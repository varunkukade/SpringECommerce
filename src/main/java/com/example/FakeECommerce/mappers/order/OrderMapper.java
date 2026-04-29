package com.example.FakeECommerce.mappers.order;

import java.util.List;

import com.example.FakeECommerce.dtos.OrderResponseDTO;
import com.example.FakeECommerce.dtos.ProductDTO;
import com.example.FakeECommerce.schema.Address;
import com.example.FakeECommerce.schema.Order;

public interface OrderMapper {
    public Order createOrderMapper(Address address);

    public OrderResponseDTO createOrderResponseDTOMapper(Long id, Address address, List<ProductDTO> products);
}
