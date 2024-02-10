package com.bondsbackend.ms.repository;

import com.bondsbackend.ms.entity.Portfolio;
import com.bondsbackend.ms.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByProfile(Profile profile);
}
