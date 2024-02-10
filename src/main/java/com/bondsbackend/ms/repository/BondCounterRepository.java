package com.bondsbackend.ms.repository;

import com.bondsbackend.ms.entity.BondCounter;
import com.bondsbackend.ms.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

public interface BondCounterRepository extends JpaRepository<BondCounter, Long> {
    List<BondCounter> findAllByPortfolio(Portfolio portfolio);
    Optional<BondCounter> findByIdAndPortfolio(Long id, Portfolio portfolio);
}