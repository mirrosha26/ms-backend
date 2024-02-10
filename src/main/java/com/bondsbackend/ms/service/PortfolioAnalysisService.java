package com.bondsbackend.ms.service;

import com.bondsbackend.ms.dto.PortfolioAnalysisRequest;
import com.bondsbackend.ms.dto.PortfolioAnalysisResponse;

public interface PortfolioAnalysisService {
    PortfolioAnalysisResponse analyzePortfolio(PortfolioAnalysisRequest request, String username);
}
