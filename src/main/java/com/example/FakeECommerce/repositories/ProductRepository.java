package com.example.FakeECommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.FakeECommerce.schema.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // here spring data jpa will automatcally create the class implementation for
    // this interface
    // inside that class, it will implement all required menthods that needs to be
    // implemented by JpaRepository interface

    List<Product> findByCategoryIgnoreCase(String category);

    @Query("SELECT p FROM Product p JOIN FETCH p.category")
    List<Product> findAllWithCategory();
}