package com.bondsbackend.ms.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BondPriceHistoryDto {
    private Long id;
    private Long bondId;
    private LocalDate date;
    private Double price;
    private Double couponIncome;
}