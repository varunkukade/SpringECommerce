package com.example.FakeECommerce.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.FakeECommerce.dtos.OrderItemDTO;
import com.example.FakeECommerce.mappers.product.ProductMapper;
import com.example.FakeECommerce.schema.OrderProduct;
import com.example.FakeECommerce.schema.Product;

public final class OrderUtils {
    public record ProductsAndCounts(List<Product> products, Map<Long, Integer> orderItemCounts) {
    }

    public static Map<Long, Integer> getOrderItemCountHashmap(List<OrderItemDTO> orderItems) {
        // create hashmap for product count
        Map<Long, Integer> orderItemCountMap = new HashMap<>();
        for (OrderItemDTO orderItem : orderItems) {
            orderItemCountMap.put(orderItem.getId(), orderItem.getCount());
        }
        return orderItemCountMap;
    }

    public static OrderUtils.ProductsAndCounts getProductsAndCounts(List<OrderProduct> orderItems) {
        List<Product> products = new ArrayList<>();
        Map<Long, Integer> orderItemCounts = new HashMap<>();
        for (OrderProduct line : orderItems) {
            products.add(ProductMapper.toProduct(line));
            orderItemCounts.put(line.getProduct().getId(), line.getCount());
        }
        return new ProductsAndCounts(products, orderItemCounts);
    }
}
