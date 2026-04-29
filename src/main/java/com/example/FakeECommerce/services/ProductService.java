package com.example.FakeECommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FakeECommerce.dtos.ProductDTO;
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
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);

        Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .category(category)
                .imageUrl(productDTO.getImageUrl())
                .build();
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProductById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Product not found", e);
        }
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }

}
