package com.example.marketkurly.repository;

import com.example.marketkurly.model.Cart;
import com.example.marketkurly.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    ArrayList<Product> findAllByProductIds();
}