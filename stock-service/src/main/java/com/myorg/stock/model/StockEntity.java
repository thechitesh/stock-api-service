package com.myorg.stock.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "STOCK")
public class StockEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @PrimaryKeyJoinColumn
    private Long id;

    @NonNull
    @Column(name = "NAME")
    private String name;

    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "CURRENCY")
    @Enumerated(STRING)
    private Price.CurrencyEnum currency;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "LAST_UPDATE_AT")
    private LocalDateTime lastUpdate;
}