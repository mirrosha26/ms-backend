package com.bondsbackend.ms.controller;

import com.bondsbackend.ms.dto.BondDto;
import com.bondsbackend.ms.service.BondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bondsbackend.ms.dto.BondPriceHistoryDto;

import java.util.List;


@RestController
@RequestMapping("/api/admin/bonds")
public class AdminBondController {

    private final BondService bondService;

    @Autowired
    public AdminBondController(BondService bondService) {
        this.bondService = bondService;
    }

    @PostMapping
    public ResponseEntity<BondDto> createBond(@RequestBody BondDto bondDto) {
        BondDto createdBond = bondService.createBond(bondDto);
        return ResponseEntity.ok(createdBond);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BondDto> updateBond(@PathVariable Long id, @RequestBody BondDto bondDto) {
        BondDto updatedBond = bondService.updateBond(id, bondDto);
        return updatedBond != null ? ResponseEntity.ok(updatedBond) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBond(@PathVariable Long id) {
        bondService.deleteBond(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{bondId}/price-history")
    public ResponseEntity<List<BondPriceHistoryDto>> getBondPriceHistory(@PathVariable Long bondId) {
        List<BondPriceHistoryDto> priceHistory = bondService.getPriceHistoryForBond(bondId);
        return ResponseEntity.ok(priceHistory);
    }

    @PostMapping("/{bondId}/price-history")
    public ResponseEntity<?> addPriceHistory(@PathVariable Long bondId, @RequestBody BondPriceHistoryDto priceHistoryDto) {
        priceHistoryDto.setBondId(bondId);
        return bondService.addPriceHistory(priceHistoryDto);
    }

    // Обновление исторической записи о стоимости облигации
    @PutMapping("/price-history/{id}")
    public ResponseEntity<BondPriceHistoryDto> updatePriceHistory(@PathVariable Long id, @RequestBody BondPriceHistoryDto priceHistoryDto) {
        BondPriceHistoryDto updatedPriceHistory = bondService.updatePriceHistory(id, priceHistoryDto);
        return updatedPriceHistory != null ? ResponseEntity.ok(updatedPriceHistory) : ResponseEntity.notFound().build();
    }

    // Удаление исторической записи о стоимости облигации
    @DeleteMapping("/price-history/{id}")
    public ResponseEntity<Void> deletePriceHistory(@PathVariable Long id) {
        bondService.deletePriceHistory(id);
        return ResponseEntity.ok().build();
    }


}
