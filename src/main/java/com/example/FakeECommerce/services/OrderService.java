package com.example.FakeECommerce.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.FakeECommerce.exception.ResourceNotFoundException;
import com.example.FakeECommerce.dtos.OrderDTO;
import com.example.FakeECommerce.dtos.OrderResponseDTO;
import com.example.FakeECommerce.dtos.ProductDTO;
import com.example.FakeECommerce.mappers.order.OrderMapper;
import com.example.FakeECommerce.mappers.product.ProductMapper;
import com.example.FakeECommerce.repositories.AddressRepository;
import com.example.FakeECommerce.repositories.OrderProductRepository;
import com.example.FakeECommerce.repositories.OrderRepository;
import com.example.FakeECommerce.schema.Address;
import com.example.FakeECommerce.schema.Order;
import com.example.FakeECommerce.schema.OrderProduct;
import com.example.FakeECommerce.schema.Product;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private AddressRepository addressRepository;
    private OrderProductInterface orderProductService;
    private OrderProductRepository orderProductRepository;
    private OrderMapper orderMapper;
    private ProductMapper productMapper;

    private Order createOrderInDB(OrderDTO orderDTO) {
        // 1. get the address from the addresses
        Long addressId = orderDTO.getAddressId();
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));
        // save the order in Orders table
        return orderRepository.save(orderMapper.createOrderMapper(address));
    }

    private OrderResponseDTO generateCreateOrderResponse(List<Product> products, Order order) {
        // create the product response dto builder array.
        List<ProductDTO> productResponseDTOs = new ArrayList<>();
        for (Product product : products) {
            productResponseDTOs.add(productMapper.productDTOMapper(product));
        }
        // generate create order response
        return orderMapper.createOrderResponseDTOMapper(order.getId(), order.getAddress(), productResponseDTOs);
    }

    public OrderResponseDTO createNewOrder(OrderDTO orderDto) {
        Order order = this.createOrderInDB(orderDto);
        List<Product> products = orderProductService.createNewOrderProductsBatch(orderDto.getProducts(), order);
        return this.generateCreateOrderResponse(products, order);
    }

    public void deleteOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
    }

    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAllWithAddress();
        List<Long> orderIds = orders.stream().map(Order::getId).toList();
        List<OrderProduct> lines = orderProductRepository.findAllByOrderIdInWithProductAndCategory(orderIds);
        Map<Long, List<Product>> linesByOrderId = new HashMap<>();
        for (OrderProduct line : lines) {
            Long orderId = line.getOrder().getId();
            if (!linesByOrderId.containsKey(orderId)) {
                linesByOrderId.put(orderId, new ArrayList<>());
            }
            linesByOrderId.get(orderId).add(line.getProduct());
        }
        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();
        for (Order order : orders) {
            Long id = order.getId();
            orderResponseDTOs.add(this.generateCreateOrderResponse(linesByOrderId.get(id), order));
        }
        return orderResponseDTOs;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }
}
