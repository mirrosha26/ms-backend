package com.bondsbackend.ms.dto;
import lombok.Data;
@Data
public class PortfolioStatisticsDto {
    private Double expectedReturn; // Математическое ожидание
    private Double variance;       // Дисперсия
}
