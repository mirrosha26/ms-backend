package com.bondsbackend.ms.repository;

import com.bondsbackend.ms.entity.BondPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BondPriceHistoryRepository extends JpaRepository<BondPriceHistory, Long> {
    List<BondPriceHistory> findByBondId(Long bondId);
    List<BondPriceHistory> findByDateBetween(LocalDate startDate, LocalDate endDate);
    Optional<BondPriceHistory> findByBondIdAndDate(Long bondId, LocalDate date);
    // Найти последнюю запись цены до или на указанную дату для определенной облигации
    Optional<BondPriceHistory> findTopByBondIdAndDateLessThanEqualOrderByDateDesc(Long bondId, LocalDate date);

    // Найти первую запись цены после указанной даты для определенной облигации
    Optional<BondPriceHistory> findTopByBondIdAndDateGreaterThanOrderByDateAsc(Long bondId, LocalDate date);
}