package com.example.FakeECommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.FakeECommerce.exception.ResourceNotFoundException;
import com.example.FakeECommerce.mappers.order.OrderMapper;
import com.example.FakeECommerce.dtos.OrderProductDTO;
import com.example.FakeECommerce.dtos.OrderItemDTO;
import com.example.FakeECommerce.repositories.OrderProductRepository;
import com.example.FakeECommerce.schema.Order;
import com.example.FakeECommerce.schema.OrderProduct;
import com.example.FakeECommerce.schema.Product;
import com.example.FakeECommerce.utils.OPEnum;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderProductService implements OrderProductInterface {
    private OrderProductRepository orderProductRepository;
    private ProductService productService;
    private OrderMapper orderMapper;

    @Override
    public OrderProduct createNewOrderProduct(OrderProductDTO orderProductDTO, Order order) {
        return orderProductRepository
                .save(orderMapper.toOrderProduct(orderProductDTO.getProductId(), orderProductDTO.getCount(), order));
    }

    @Override
    public List<OrderProduct> createNewOrderProductsBatch(List<OrderItemDTO> productsRequest, Order order,
            Map<Long, Integer> productCountMap) {
        // get all product info
        var requestedIds = productCountMap.keySet();
        List<Product> products = productService.findAllProductsById(new ArrayList<>(requestedIds));
        Set<Long> foundIds = products.stream().map(Product::getId).collect(Collectors.toSet());
        List<Long> missingIds = requestedIds.stream().filter(id -> !foundIds.contains(id)).toList();
        if (!missingIds.isEmpty()) {
            throw new ResourceNotFoundException("Products not found with ids: " + missingIds);
        }

        // create the builder array
        List<OrderProduct> orderProducts = products.stream().map(eachProduct -> OrderProduct.builder()
                .order(order)
                .product(eachProduct)
                .count(productCountMap.getOrDefault(eachProduct.getId(), 0))
                .build()).collect(Collectors.toList());

        // save all order product into OrderProduct table and return response
        return orderProductRepository.saveAll(orderProducts);
    }

    @Override
    public void updateOrderProductsBatch(List<OrderItemDTO> newOrderItems, Order order) {
        List<OrderProduct> toSave = new ArrayList<>();
        List<OrderProduct> toRemove = new ArrayList<>();
        for (OrderItemDTO newOrderItem : newOrderItems) {
            Long productId = newOrderItem.getId();
            OPEnum op = newOrderItem.getOp();
            if (op == OPEnum.DELETE) {
                toRemove.add(orderMapper.toOrderProduct(productId, newOrderItem.getCount(), order));
            } else {
                Optional<OrderProduct> existingLine = orderProductRepository.findByOrder_IdAndProduct_Id(order.getId(),
                        productId);
                if (existingLine.isEmpty()) {
                    Integer count = newOrderItem.getCount() != null ? newOrderItem.getCount() : 1;
                    toSave.add(orderMapper.toOrderProduct(productId, count, order));
                } else {
                    OrderProduct orderProduct = existingLine.get();
                    if (newOrderItem.getCount() != null) {
                        orderProduct.setCount(newOrderItem.getCount());
                    }
                    toSave.add(orderProduct);
                }
            }
        }
        orderProductRepository.saveAll(toSave);
        orderProductRepository.deleteAll(toRemove);
    }

    @Override
    public List<OrderProduct> findAllByOrderId(Long orderId) {
        return orderProductRepository.findAllByOrder_Id(orderId);
    }

    @Override
    public List<OrderProduct> findAllByOrderIds(List<Long> orderIds) {
        return orderProductRepository.findAllByOrderIdInWithProductAndCategory(orderIds);
    }

    @Override
    public void deleteAllByOrderId(Long orderId) {
        orderProductRepository.deleteAllByOrder_Id(orderId);
    }
}
