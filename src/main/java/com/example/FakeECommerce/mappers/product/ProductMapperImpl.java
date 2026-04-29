package com.example.FakeECommerce.mappers.product;

import org.springframework.stereotype.Component;

import com.example.FakeECommerce.dtos.ProductResponseDTO;
import com.example.FakeECommerce.schema.Product;

@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponseDTO productDTOMapper(Product product) {
        return ProductResponseDTO
                .builder()
                .id(product.getId())
                .categoryId(product.getCategory().getId())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
