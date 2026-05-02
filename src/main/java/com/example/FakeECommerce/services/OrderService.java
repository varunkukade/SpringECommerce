package com.example.FakeECommerce.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.FakeECommerce.exception.ResourceNotFoundException;
import com.example.FakeECommerce.dtos.OrderDTO;
import com.example.FakeECommerce.dtos.OrderItemAndCountDTO;
import com.example.FakeECommerce.dtos.OrderItemDTO;
import com.example.FakeECommerce.dtos.OrderResponseDTO;
import com.example.FakeECommerce.dtos.OrderItemResponseDTO;
import com.example.FakeECommerce.mappers.order.OrderMapper;
import com.example.FakeECommerce.repositories.OrderRepository;
import com.example.FakeECommerce.schema.Order;
import com.example.FakeECommerce.schema.OrderProduct;
import com.example.FakeECommerce.schema.Product;
import com.example.FakeECommerce.utils.OrderUtils;
import com.example.FakeECommerce.utils.OrderUtils.ProductsAndCounts;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private AddressService addressService;
    private OrderProductInterface orderProductService;

    private Order createOrderInDB(OrderDTO orderDTO) {
        // 1. get the address from the addresses
        Long addressId = orderDTO.getAddressId();
        // 2. save the order in Orders table
        return orderRepository.save(OrderMapper.createOrderMapper(addressService.getAddressById(addressId)));
    }

    private Order updateOrderInDB(Long orderId, OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        if (orderDTO.getAddressId() != null) {
            order.setAddress(addressService.getAddressById(orderDTO.getAddressId()));
        }
        return orderRepository.save(order);
    }

    private OrderResponseDTO generateOrderResponse(List<Product> products, Order order,
            Map<Long, Integer> orderItemCountmap) {
        // create the product response dto builder array.
        List<OrderItemResponseDTO> productResponseDTOs = products.stream()
                .map(eachProduct -> OrderMapper.toOrderItemResponseDTO(eachProduct,
                        orderItemCountmap.getOrDefault(eachProduct.getId(), 0)))
                .collect(Collectors.toList());
        // generate create order response
        return OrderMapper.createOrderResponseDTOMapper(order.getId(), order.getAddress(), productResponseDTOs);
    }

    public OrderResponseDTO createNewOrder(OrderDTO orderDto) {
        // update order table
        Order order = this.createOrderInDB(orderDto);

        // update order_products table
        Map<Long, Integer> orderItemCountmap = OrderUtils.getOrderItemCountHashmap(orderDto.getProducts());
        List<OrderProduct> orderProductsResponse = orderProductService.createNewOrderProductsBatch(
                orderDto.getProducts(), order,
                orderItemCountmap);

        // return order response
        ProductsAndCounts productAndCounts = OrderUtils.getProductsAndCounts(orderProductsResponse);
        return this.generateOrderResponse(productAndCounts.products(), order, productAndCounts.orderItemCounts());
    }

    public OrderResponseDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        List<OrderItemDTO> newOrderItems = orderDTO.getProducts();

        // update the orders table
        Order order = this.updateOrderInDB(orderId, orderDTO);

        // update the order_products table
        orderProductService.updateOrderProductsBatch(newOrderItems, order);

        // return the order response
        List<OrderProduct> orderProductsResponse = orderProductService.findAllByOrderId(orderId);
        ProductsAndCounts productAndCounts = OrderUtils.getProductsAndCounts(orderProductsResponse);
        return this.generateOrderResponse(productAndCounts.products(), order, productAndCounts.orderItemCounts());
    }

    @Transactional
    public void deleteOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        if (order.getDeletedAt() == null) {
            orderProductService.deleteAllByOrderId(id);
            orderRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
    }

    public List<OrderResponseDTO> getAllOrders() {
        // get data from orders table.
        List<Order> orders = orderRepository.findAllWithAddress();

        // get data from order_products table
        List<Long> orderIds = orders.stream().map(Order::getId).toList();
        List<OrderProduct> lines = orderProductService.findAllByOrderIds(orderIds);

        // use both of the responses and return orders response.
        Map<Long, OrderItemAndCountDTO> orderItemCountMapByOrderId = new HashMap<>();
        for (OrderProduct line : lines) {
            Long orderId = line.getOrder().getId();
            if (!orderItemCountMapByOrderId.containsKey(orderId)) {
                orderItemCountMapByOrderId.put(orderId, OrderItemAndCountDTO.builder().orderItems(new ArrayList<>())
                        .orderItemCountMap(new HashMap<>()).build());
            }
            orderItemCountMapByOrderId.get(orderId).getOrderItems().add(line.getProduct());
            orderItemCountMapByOrderId.get(orderId).getOrderItemCountMap().put(line.getProduct().getId(),
                    line.getCount());
        }
        return orders.stream()
                .map(eachOrder -> this.generateOrderResponse(
                        orderItemCountMapByOrderId.get(eachOrder.getId()).getOrderItems(),
                        eachOrder, orderItemCountMapByOrderId.get(eachOrder.getId()).getOrderItemCountMap()))
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getOrderById(Long id) {
        // get order from orders table.
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        // get data of products from order_products table
        List<OrderProduct> orderProductsResponse = orderProductService.findAllByOrderId(id);

        ProductsAndCounts productAndCounts = OrderUtils.getProductsAndCounts(orderProductsResponse);

        // combine the data to generate and return order response
        return this.generateOrderResponse(productAndCounts.products(), order, productAndCounts.orderItemCounts());
    }
}
