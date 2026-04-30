package com.example.FakeECommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FakeECommerce.dtos.CategoryDTO;
import com.example.FakeECommerce.exception.ResourceNotFoundException;
import com.example.FakeECommerce.repositories.CategoryRepository;
import com.example.FakeECommerce.schema.Category;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder().name(categoryDTO.getName()).build();
        return categoryRepository.save(category);
    }

    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

}
