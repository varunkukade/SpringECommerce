package com.example.FakeECommerce.mappers.product;

import com.example.FakeECommerce.schema.OrderProduct;
import com.example.FakeECommerce.schema.Product;

public interface ProductMapper {
    public static Product toProduct(OrderProduct orderProduct) {
        return Product
                .builder()
                .id(orderProduct.getProduct().getId())
                .name(orderProduct.getProduct().getName())
                .description(orderProduct.getProduct().getDescription())
                .price(orderProduct.getProduct().getPrice())
                .imageUrl(orderProduct.getProduct().getImageUrl())
                .category(orderProduct.getProduct().getCategory())
                .build();
    }
}