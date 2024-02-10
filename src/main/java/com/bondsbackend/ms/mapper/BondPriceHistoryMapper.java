package com.bondsbackend.ms.mapper;

import com.bondsbackend.ms.dto.BondPriceHistoryDto;
import com.bondsbackend.ms.entity.Bond;
import com.bondsbackend.ms.entity.BondPriceHistory;
import org.springframework.stereotype.Component;
@Component
public class BondPriceHistoryMapper {

    // Преобразование сущности в DTO
    public BondPriceHistoryDto toDto(BondPriceHistory entity) {
        BondPriceHistoryDto dto = new BondPriceHistoryDto();
        dto.setId(entity.getId());
        dto.setBondId(entity.getBond().getId()); // Только ID облигации
        dto.setDate(entity.getDate());
        dto.setPrice(entity.getPrice());
        dto.setCouponIncome(entity.getCouponIncome()); // Добавляем купонный доход
        return dto;
    }

    // Преобразование DTO в сущность
    public BondPriceHistory toEntity(BondPriceHistoryDto dto) {
        BondPriceHistory entity = new BondPriceHistory();
        entity.setId(dto.getId());
        Bond bond = new Bond();
        bond.setId(dto.getBondId());
        entity.setBond(bond);
        entity.setDate(dto.getDate());
        entity.setPrice(dto.getPrice());
        entity.setCouponIncome(dto.getCouponIncome()); // Устанавливаем купонный доход
        return entity;
    }
}

