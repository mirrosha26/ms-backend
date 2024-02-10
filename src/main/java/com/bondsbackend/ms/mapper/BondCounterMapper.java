package com.bondsbackend.ms.mapper;

import com.bondsbackend.ms.dto.BondCounterDto;
import com.bondsbackend.ms.entity.BondCounter;
import com.bondsbackend.ms.repository.BondRepository;
import com.bondsbackend.ms.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BondCounterMapper {

    private final BondRepository bondRepository;
    private final PortfolioRepository portfolioRepository;

    @Autowired
    public BondCounterMapper(BondRepository bondRepository, PortfolioRepository portfolioRepository) {
        this.bondRepository = bondRepository;
        this.portfolioRepository = portfolioRepository;
    }

    public BondCounterDto toDto(BondCounter bondCounter) {
        BondCounterDto dto = new BondCounterDto();
        dto.setId(bondCounter.getId());
        dto.setBondId(bondCounter.getBond().getId());
        dto.setQuantity(bondCounter.getQuantity());
        return dto;
    }

    public BondCounter toEntity(BondCounterDto dto) {
        BondCounter bondCounter = new BondCounter();
        bondCounter.setId(dto.getId());
        bondCounter.setBond(bondRepository.findById(dto.getBondId()).orElse(null));
        bondCounter.setQuantity(dto.getQuantity());
        return bondCounter;
    }
}
