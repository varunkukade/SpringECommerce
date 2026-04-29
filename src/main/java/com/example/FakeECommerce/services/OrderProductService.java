package com.example.FakeECommerce.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FakeECommerce.dtos.OrderProductDTO;
import com.example.FakeECommerce.dtos.OrderProductProductDTO;
import com.example.FakeECommerce.repositories.OrderProductRepository;
import com.example.FakeECommerce.repositories.ProductRepository;
import com.example.FakeECommerce.schema.Order;
import com.example.FakeECommerce.schema.OrderProduct;
import com.example.FakeECommerce.schema.Product;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderProductService implements OrderProductInterface {
    private OrderProductRepository orderProductRepository;
    private ProductRepository productRepository;

    @Override
    public OrderProduct createNewOrderProduct(OrderProductDTO orderProductDTO, Order order) {
        // 1. get product from id
        Product product = productRepository.findById(orderProductDTO.getProductId()).orElse(null);
        // 2. create new order product
        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .count(orderProductDTO.getCount())
                .build();
        // 3. save new order product into the db
        return orderProductRepository.save(orderProduct);
    }

    @Override
    public List<Product> createNewOrderProductsBatch(List<OrderProductProductDTO> productsRequest, Order order) {
        // create hashmap for product count
        HashMap<Long, Integer> productCountMap = new HashMap<>();
        for (OrderProductProductDTO product : productsRequest) {
            productCountMap.put(product.getId(), product.getCount());
        }

        // get all product info
        List<Product> products = productRepository.findAllById(productCountMap.keySet());

        // create the builder array
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (Product product : products) {
            OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .count(productCountMap.getOrDefault(product.getId(), 0))
                    .build();
            orderProducts.add(orderProduct);
        }

        // save all order product into OrderProduct table
        orderProductRepository.saveAll(orderProducts);
        return products;
    }
}
