package com.example.FakeECommerce.mappers.order;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.FakeECommerce.dtos.OrderResponseDTO;
import com.example.FakeECommerce.dtos.ProductDTO;
import com.example.FakeECommerce.schema.Address;
import com.example.FakeECommerce.schema.Order;

@Component
public class OrderMapperImpl implements OrderMapper {
    @Override
    public Order createOrderMapper(Address address) {
        return Order.builder().address(address).build();
    }

    @Override
    public OrderResponseDTO createOrderResponseDTOMapper(Long id, Address address, List<ProductDTO> products) {
        return OrderResponseDTO.builder().id(id).address(address)
                .products(products).build();
    }
}
