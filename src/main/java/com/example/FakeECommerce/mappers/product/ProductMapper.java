package com.example.FakeECommerce.mappers.product;

import com.example.FakeECommerce.dtos.ProductResponseDTO;
import com.example.FakeECommerce.schema.Product;

public interface ProductMapper {
    public ProductResponseDTO productDTOMapper(Product product);
}