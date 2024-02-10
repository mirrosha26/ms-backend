package com.bondsbackend.ms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bond_price_history")
public class BondPriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bond_id", nullable = false)
    private Bond bond;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "coupon_income", nullable = false)
    private Double couponIncome;
}
