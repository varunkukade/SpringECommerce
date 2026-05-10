package com.example.FakeECommerce.mappers.order;

import org.springframework.stereotype.Component;

import com.example.FakeECommerce.exception.ResourceNotFoundException;
import com.example.FakeECommerce.repositories.ProductRepository;
import com.example.FakeECommerce.schema.Order;
import com.example.FakeECommerce.schema.OrderProduct;
import com.example.FakeECommerce.schema.Product;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class OrderMapperImpl implements OrderMapper {

    private ProductRepository productRepository;

    @Override
    public OrderProduct toOrderProduct(Long productId, Integer productCount, Order order) {
        Product product = productRepository.findByIdWithCategory(
                productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        return OrderProduct.builder()
                .order(order)
                .product(product)
                .count(productCount)
                .build();
    }
}
