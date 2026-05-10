package com.example.FakeECommerce.mappers.order;

import java.util.List;
import com.example.FakeECommerce.dtos.OrderResponseDTO;
import com.example.FakeECommerce.dtos.OrderItemResponseDTO;
import com.example.FakeECommerce.schema.Address;
import com.example.FakeECommerce.schema.Order;
import com.example.FakeECommerce.schema.OrderProduct;
import com.example.FakeECommerce.schema.Product;

public interface OrderMapper {

    public static Order createOrderMapper(Address address) {
        return Order.builder().address(address).build();
    }

    public static OrderItemResponseDTO toOrderItemResponseDTO(Product product, Integer itemCount) {
        return OrderItemResponseDTO
                .builder()
                .id(product.getId())
                .categoryId(product.getCategory().getId())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .name(product.getName())
                .price(product.getPrice())
                .count(itemCount)
                .build();
    }

    public static OrderResponseDTO createOrderResponseDTOMapper(Long id, Address address,
            List<OrderItemResponseDTO> products) {
        return OrderResponseDTO.builder().id(id).address(address)
                .products(products).build();
    }

    public OrderProduct toOrderProduct(Long id, Integer count, Order order);
}
