package com.example.FakeECommerce.dtos;

import java.util.List;
import java.util.Map;

import com.example.FakeECommerce.schema.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderItemAndCountDTO {
    private List<Product> orderItems;
    private Map<Long, Integer> orderItemCountMap;
}
