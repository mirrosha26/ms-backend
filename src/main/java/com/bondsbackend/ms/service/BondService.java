package com.bondsbackend.ms.service;

import com.bondsbackend.ms.dto.BondDto;
import com.bondsbackend.ms.dto.BondPriceHistoryDto;
import com.bondsbackend.ms.entity.Bond;
import com.bondsbackend.ms.entity.BondPriceHistory;
import com.bondsbackend.ms.mapper.BondMapper;
import com.bondsbackend.ms.mapper.BondPriceHistoryMapper;
import com.bondsbackend.ms.repository.BondPriceHistoryRepository;
import com.bondsbackend.ms.repository.BondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
@Service
public class BondService {
    private final BondRepository bondRepository;
    private final BondPriceHistoryRepository bondPriceHistoryRepository;
    private final BondMapper bondMapper;
    private final BondPriceHistoryMapper bondPriceHistoryMapper;

    @Autowired
    public BondService(BondRepository bondRepository,
                       BondPriceHistoryRepository bondPriceHistoryRepository,
                       BondMapper bondMapper,
                       BondPriceHistoryMapper bondPriceHistoryMapper) {
        this.bondRepository = bondRepository;
        this.bondPriceHistoryRepository = bondPriceHistoryRepository;
        this.bondMapper = bondMapper;
        this.bondPriceHistoryMapper = bondPriceHistoryMapper;
    }

    public List<BondDto> getAllBonds() {
        return bondRepository.findAll().stream()
                .map(bondMapper::toDto)
                .collect(Collectors.toList());
    }

    public BondDto getBondById(Long id) {
        return bondRepository.findById(id)
                .map(bondMapper::toDto)
                .orElse(null);
    }

    public BondDto createBond(BondDto bondDto) {
        Bond bond = bondMapper.toEntity(bondDto);
        bond = bondRepository.save(bond);
        return bondMapper.toDto(bond);
    }

    public BondDto updateBond(Long id, BondDto bondDto) {
        return bondRepository.findById(id)
                .map(bond -> {
                    bond.setTicker(bondDto.getTicker());
                    bond.setName(bondDto.getName());
                    bond.setNominalPrice(bondDto.getNominalPrice());
                    bond.setTermInYears(bondDto.getTermInYears());
                    bond.setCouponRate(bondDto.getCouponRate());
                    bond.setCouponAnnouncementDate(bondDto.getCouponAnnouncementDate());
                    return bondMapper.toDto(bondRepository.save(bond));
                }).orElse(null);
    }

    public void deleteBond(Long id) {
        bondRepository.deleteById(id);
    }

    public List<BondPriceHistoryDto> getPriceHistoryForBond(Long bondId) {
        return bondPriceHistoryRepository.findByBondId(bondId).stream()
                .map(bondPriceHistoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> addPriceHistory(BondPriceHistoryDto priceHistoryDto) {
        Optional<Bond> bondOpt = bondRepository.findById(priceHistoryDto.getBondId());
        if (bondOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Bond not found");
        }

        BondPriceHistory priceHistory = bondPriceHistoryMapper.toEntity(priceHistoryDto);
        bondPriceHistoryRepository.save(priceHistory);
        return ResponseEntity.ok(bondPriceHistoryMapper.toDto(priceHistory));
    }

    public BondPriceHistoryDto updatePriceHistory(Long id, BondPriceHistoryDto priceHistoryDto) {
        Optional<BondPriceHistory> existingPriceHistory = bondPriceHistoryRepository.findById(id);
        return existingPriceHistory.map(priceHistory -> {
            priceHistory.setDate(priceHistoryDto.getDate());
            priceHistory.setPrice(priceHistoryDto.getPrice());
            BondPriceHistory updatedPriceHistory = bondPriceHistoryRepository.save(priceHistory);
            return bondPriceHistoryMapper.toDto(updatedPriceHistory);
        }).orElse(null);
    }

    public void deletePriceHistory(Long id) {
        bondPriceHistoryRepository.deleteById(id);
    }

}
