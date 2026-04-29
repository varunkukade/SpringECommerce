package com.example.FakeECommerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    @JsonProperty("lat")
    private Double lattitude;

    @JsonProperty("long")
    private Double longitude;

    @JsonProperty("desc")
    private String description;
}
