package com.example.FakeECommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FakeECommerce.dtos.ProductDTO;
import com.example.FakeECommerce.exception.ResourceNotFoundException;
import com.example.FakeECommerce.repositories.CategoryRepository;
import com.example.FakeECommerce.repositories.ProductRepository;
import com.example.FakeECommerce.schema.Category;
import com.example.FakeECommerce.schema.Product;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAllWithCategory();
    }

    public Product createProduct(ProductDTO productDTO) {
        Long categoryId = productDTO.getCategoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .category(category)
                .imageUrl(productDTO.getImageUrl())
                .build();
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, ProductDTO productDTO) {
        Product product = productRepository.findById(
                productId).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Product not found with id: " + productId));
        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        if (productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
        }
        if (productDTO.getImageUrl() != null) {
            product.setImageUrl(productDTO.getImageUrl());
        }
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO
                    .getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDTO
                            .getCategoryId()));
            product.setCategory(category);
        }
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }

    public List<Product> findAllProductsById(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }
}
