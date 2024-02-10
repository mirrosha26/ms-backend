package com.bondsbackend.ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BondCounterDto {
    private Long id;
    private Long bondId;
    private Integer quantity; // Количество облигаций
}
