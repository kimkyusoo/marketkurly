package com.example.marketkurly.repository;


import com.example.marketkurly.model.RefreshToken;
import com.example.marketkurly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByUser(User user);
}
