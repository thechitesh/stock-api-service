//package com.myorg.stock.model;
//
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.NonNull;
//import lombok.ToString;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.MapsId;
//import javax.persistence.OneToOne;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Builder(toBuilder = true)
//@Data
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@Entity
//@Table(name = "PRICE")
//public class PriceEntity {
//
//    @Id
////    @NonNull
//    @Column(name = "ID")
////    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "p_seq")
////    @SequenceGenerator(name = "p_seq", sequenceName = "PRICE_SEQ", allocationSize = 0)
//    @GeneratedValue(strategy=GenerationType.AUTO)
//    private Long id;
//
//    @NonNull
//    @Column(name = "AMOUNT")
//    private BigDecimal amount;
//
//    @NonNull
//    @Column(name = "CURRENCY")
//    private String currency;
//
////    @NonNull
//    @Column(name = "CREATED_AT")
//    private LocalDateTime createdAt;
//
//    @Column(name = "LAST_UPDATE_AT")
//    private LocalDateTime lastUpdateAt;
//
////    @Column(name = "STOCK_ID")
////    private Long stockId;
//
//    @ToString.Exclude
////    @NonNull
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "stock_id")
//    private StockEntity stock;
//
//   /* @OneToOne(fetch = FetchType.LAZY, cascade= CascadeType.ALL,
//            mappedBy = "price")
//    private StockEntity stockEntity;*/
//
////    @OneToOne
////    @MapsId
////    @JoinColumn(name = "stock_id")
////    @OneToOne(targetEntity=StockEntity.class)
////    private StockEntity stock;
//
//
//}
