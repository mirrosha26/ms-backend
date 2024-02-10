package com.bondsbackend.ms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioAnalysisRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}
