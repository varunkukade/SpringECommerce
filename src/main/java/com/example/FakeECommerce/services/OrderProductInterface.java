package com.example.FakeECommerce.services;

import java.util.List;
import java.util.Map;

import com.example.FakeECommerce.dtos.OrderProductDTO;
import com.example.FakeECommerce.dtos.OrderItemDTO;
import com.example.FakeECommerce.schema.Order;
import com.example.FakeECommerce.schema.OrderProduct;

public interface OrderProductInterface {
    public OrderProduct createNewOrderProduct(OrderProductDTO orderProductDTO, Order order);

    public List<OrderProduct> createNewOrderProductsBatch(List<OrderItemDTO> productsRequest, Order order,
            Map<Long, Integer> productCountMap);

    public void updateOrderProductsBatch(List<OrderItemDTO> newOrderItems, Order order);

    public List<OrderProduct> findAllByOrderId(Long orderId);

    public List<OrderProduct> findAllByOrderIds(List<Long> orderIds);
}
