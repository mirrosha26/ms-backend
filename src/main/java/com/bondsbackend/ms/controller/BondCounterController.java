package com.bondsbackend.ms.controller;

import com.bondsbackend.ms.dto.BondCounterDto;
import com.bondsbackend.ms.service.BondCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bondcounters")
public class BondCounterController {

    private final BondCounterService bondCounterService;

    @Autowired
    public BondCounterController(BondCounterService bondCounterService) {
        this.bondCounterService = bondCounterService;
    }

    @GetMapping
    public ResponseEntity<List<BondCounterDto>> getAllBondCounters(Authentication authentication) {
        String username = authentication.getName();
        List<BondCounterDto> bondCounters = bondCounterService.getAllBondCounters(username);
        return ResponseEntity.ok(bondCounters);
    }

    @PostMapping
    public ResponseEntity<BondCounterDto> createBondCounter(@RequestBody BondCounterDto bondCounterDto, Authentication authentication) {
        String username = authentication.getName();
        BondCounterDto createdBondCounter = bondCounterService.createBondCounter(bondCounterDto, username);
        return ResponseEntity.ok(createdBondCounter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BondCounterDto> updateBondCounter(@PathVariable Long id, @RequestBody BondCounterDto bondCounterDto, Authentication authentication) {
        String username = authentication.getName();
        BondCounterDto updatedBondCounter = bondCounterService.updateBondCounter(id, bondCounterDto, username);
        return updatedBondCounter != null ? ResponseEntity.ok(updatedBondCounter) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBondCounter(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        bondCounterService.deleteBondCounter(id, username);
        return ResponseEntity.ok().build();
    }
}
