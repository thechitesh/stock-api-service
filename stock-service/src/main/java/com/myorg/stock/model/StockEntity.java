package com.myorg.stock.model;


import lombok.*;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static javax.persistence.EnumType.STRING;

@Getter
@Builder(toBuilder = true)
//@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "STOCK")
public class StockEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @PrimaryKeyJoinColumn
    private Long id;

    @NonNull
    @Column(name = "NAME")
    private String name;

    @Column(name = "SYMBOL")
    private String symbol;

//    @OneToOne
//    @JoinColumn(name = "amount_id", referencedColumnName = "id")


//    @OneToOne(mappedBy = "stock", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "stock")
//    private PriceEntity price;
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
