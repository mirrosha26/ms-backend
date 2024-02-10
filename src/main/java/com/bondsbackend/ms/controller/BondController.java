package com.bondsbackend.ms.controller;

import com.bondsbackend.ms.dto.BondDto;
import com.bondsbackend.ms.service.BondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bonds")
public class BondController {

    private final BondService bondService;

    @Autowired
    public BondController(BondService bondService) {
        this.bondService = bondService;
    }


    @GetMapping
    public ResponseEntity<List<BondDto>> getAllBonds() {
        List<BondDto> bonds = bondService.getAllBonds();
        return ResponseEntity.ok(bonds);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BondDto> getBondById(@PathVariable Long id) {
        BondDto bondDto = bondService.getBondById(id);
        return bondDto != null ? ResponseEntity.ok(bondDto) : ResponseEntity.notFound().build();
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
}