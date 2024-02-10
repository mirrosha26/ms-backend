package com.bondsbackend.ms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioAnalysisResponse {
    private Double expectedReturn;
    private Double variance;
}