package com.example.marketkurly.repository;

import com.example.marketkurly.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductReposioty extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByModifiedAtDesc();
    Optional<Product> findById(Long product_id);
}
