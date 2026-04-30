package com.example.FakeECommerce.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FakeECommerce.dtos.OrderDTO;
import com.example.FakeECommerce.dtos.OrderResponseDTO;
import com.example.FakeECommerce.schema.Order;
import com.example.FakeECommerce.services.OrderService;
import com.example.FakeECommerce.utils.ApiResponse;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity
                .ok(ApiResponse.<List<OrderResponseDTO>>success(orders, "Orders fetched successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDTO>> createNewOrder(@RequestBody OrderDTO orderDto) {
        OrderResponseDTO order = orderService.createNewOrder(orderDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<OrderResponseDTO>success(order, "Order created successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity
                .ok(ApiResponse.<Void>success(null, "Order deleted successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity
                .ok(ApiResponse.<Order>success(order, "Order fetched successfully"));
    }
}
