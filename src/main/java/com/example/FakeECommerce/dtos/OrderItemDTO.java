package com.example.FakeECommerce.dtos;

import com.example.FakeECommerce.utils.OPEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Integer count;
    private OPEnum op;
}
