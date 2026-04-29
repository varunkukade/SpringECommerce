package com.example.FakeECommerce.schema;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "addresses")
public class Address extends BaseEntity {

    private Double lattitude;
    private Double longitude;
    private String description;
}
