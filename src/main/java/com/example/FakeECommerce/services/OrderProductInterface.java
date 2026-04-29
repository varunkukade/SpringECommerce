package com.example.FakeECommerce.services;

import java.util.List;

import com.example.FakeECommerce.dtos.OrderProductDTO;
import com.example.FakeECommerce.dtos.OrderProductProductDTO;
import com.example.FakeECommerce.schema.Order;
import com.example.FakeECommerce.schema.OrderProduct;
import com.example.FakeECommerce.schema.Product;

public interface OrderProductInterface {
    public OrderProduct createNewOrderProduct(OrderProductDTO orderProductDTO, Order order);

    public List<Product> createNewOrderProductsBatch(List<OrderProductProductDTO> productsRequest, Order order);
}
