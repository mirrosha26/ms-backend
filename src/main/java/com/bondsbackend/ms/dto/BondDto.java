package com.bondsbackend.ms.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BondDto {
    private Long id;
    private String ticker;
    private String name;
    private Double nominalPrice;
    private Integer termInYears; // Срок облигации в годах
    private Double couponRate; // Купонная ставка
    private LocalDate couponAnnouncementDate; // Дата объявления купона
}


