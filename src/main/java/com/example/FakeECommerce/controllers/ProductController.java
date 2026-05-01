package com.example.FakeECommerce.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.example.FakeECommerce.dtos.ProductDTO;
import com.example.FakeECommerce.schema.Product;
import com.example.FakeECommerce.services.ProductService;
import com.example.FakeECommerce.utils.ApiResponse;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity
                .ok(ApiResponse.<List<Product>>success(products, "Products fetched successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.createProduct(productDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<Product>success(product, "Product created successfully"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateAddressById(@PathVariable Long id,
            @RequestBody ProductDTO productDTO) {
        Product product = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(ApiResponse.<Product>success(product, "Product updated successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity
                .ok(ApiResponse.<Product>success(product, "Product fetched successfully"));
    }

    @DeleteExchange("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity
                .ok(ApiResponse.<Void>success(null, "Product deleted successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Product>>> getProductByCategory(
            @RequestParam("categoryName") String categoryName) {
        List<Product> products = productService.getProductsByCategory(categoryName);
        return ResponseEntity
                .ok(ApiResponse.<List<Product>>success(products, "Products fetched successfully"));
    }
}