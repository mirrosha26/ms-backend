package com.bondsbackend.ms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bonds")
public class Bond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticker", nullable = false, unique = true)
    private String ticker;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nominal_price", nullable = false)
    private Double nominalPrice;

    @Column(name = "term_in_years", nullable = false)
    private Integer termInYears; // Срок облигации в годах

    @Column(name = "coupon_rate", nullable = false)
    private Double couponRate; // Купонная ставка

    @Column(name = "coupon_announcement_date", nullable = false)
    private LocalDate couponAnnouncementDate; // Дата объявления купона
}
