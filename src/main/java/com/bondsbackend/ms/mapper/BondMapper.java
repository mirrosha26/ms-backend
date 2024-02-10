package com.bondsbackend.ms.mapper;

import com.bondsbackend.ms.dto.BondDto;
import com.bondsbackend.ms.entity.Bond;
import org.springframework.stereotype.Component;

@Component
public class BondMapper {

    public BondDto toDto(Bond bond) {
        BondDto bondDto = new BondDto();
        bondDto.setId(bond.getId());
        bondDto.setTicker(bond.getTicker());
        bondDto.setName(bond.getName());
        bondDto.setNominalPrice(bond.getNominalPrice());
        bondDto.setTermInYears(bond.getTermInYears());
        bondDto.setCouponRate(bond.getCouponRate());
        bondDto.setCouponAnnouncementDate(bond.getCouponAnnouncementDate());
        return bondDto;
    }

    public Bond toEntity(BondDto bondDto) {
        Bond bond = new Bond();
        bond.setId(bondDto.getId());
        bond.setTicker(bondDto.getTicker());
        bond.setName(bondDto.getName());
        bond.setNominalPrice(bondDto.getNominalPrice());
        bond.setTermInYears(bondDto.getTermInYears());
        bond.setCouponRate(bondDto.getCouponRate());
        bond.setCouponAnnouncementDate(bondDto.getCouponAnnouncementDate());
        return bond;
    }
}
