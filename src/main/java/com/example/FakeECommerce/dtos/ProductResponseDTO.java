package com.example.FakeECommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class ProductResponseDTO extends ProductDTO {
    private Long id;
}
