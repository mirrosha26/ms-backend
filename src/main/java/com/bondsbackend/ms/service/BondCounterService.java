package com.bondsbackend.ms.service;

import com.bondsbackend.ms.dto.BondCounterDto;
import com.bondsbackend.ms.entity.BondCounter;
import com.bondsbackend.ms.mapper.BondCounterMapper;
import com.bondsbackend.ms.repository.BondCounterRepository;
import com.bondsbackend.ms.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BondCounterService {
    private final BondCounterRepository bondCounterRepository;
    private final BondCounterMapper bondCounterMapper;
    private final PortfolioRepository portfolioRepository;

    @Autowired
    public BondCounterService(BondCounterRepository bondCounterRepository,
                              BondCounterMapper bondCounterMapper,
                              PortfolioRepository portfolioRepository) {
        this.bondCounterRepository = bondCounterRepository;
        this.bondCounterMapper = bondCounterMapper;
        this.portfolioRepository = portfolioRepository;
    }

    public List<BondCounterDto> getAllBondCounters(String username) {
        return bondCounterRepository.findAll().stream()
                .map(bondCounterMapper::toDto)
                .collect(Collectors.toList());
    }

    public BondCounterDto createBondCounter(BondCounterDto bondCounterDto, String username) {
        BondCounter bondCounter = bondCounterMapper.toEntity(bondCounterDto);
        bondCounter = bondCounterRepository.save(bondCounter);
        return bondCounterMapper.toDto(bondCounter);
    }

    public BondCounterDto updateBondCounter(Long id, BondCounterDto bondCounterDto, String username) {
        return bondCounterRepository.findById(id)
                .map(bondCounter -> {
                    bondCounter.setQuantity(bondCounterDto.getQuantity());
                    return bondCounterMapper.toDto(bondCounterRepository.save(bondCounter));
                }).orElse(null);
    }

    public void deleteBondCounter(Long id, String username) {
        bondCounterRepository.deleteById(id);
    }
}
