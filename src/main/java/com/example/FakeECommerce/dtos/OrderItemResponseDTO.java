package com.example.FakeECommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class OrderItemResponseDTO extends ProductDTO {
    private Long id;
    private Integer count;
}
