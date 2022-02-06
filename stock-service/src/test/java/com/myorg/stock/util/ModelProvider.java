package com.myorg.stock.util;

import com.myorg.stock.model.LinkResponse;
import com.myorg.stock.model.Links;
import com.myorg.stock.model.Price;
import com.myorg.stock.model.Stock;
import com.myorg.stock.model.StockEntity;
import lombok.val;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ModelProvider {

  public static Stock createStock(Long id, String stockName, BigDecimal amount) {
    val price = createPrice(amount);
    Stock stock = new Stock();
    stock.setId(id);
    stock.setName(stockName);
    stock.setCurrentPrice(price);
    return stock;
  }

  public static Price createPrice(BigDecimal amount) {
    Price price = new Price();
    price.setAmount(amount);
    price.setCurrency(Price.CurrencyEnum.EUR);
    return price;
  }

  public static StockEntity createStockEntity(Long stockId, String stockName, BigDecimal amount) {
    return StockEntity.builder()
        .id(stockId)
        .name(stockName)
        .price(amount)
        .currency(Price.CurrencyEnum.EUR)
        .createdAt(LocalDateTime.now())
        .build();
  }


  public static Links createLinks(String selfLink, String prevLink, String nextLink) {
    Links links = new Links();
    links.setSelf(creatLinkResponse(selfLink));
    links.setPrev(creatLinkResponse(prevLink));
    links.setNext(creatLinkResponse(nextLink));
    return links;
  }

  private static LinkResponse creatLinkResponse(String href) {
    LinkResponse linkResponse = new LinkResponse();
    linkResponse.setHref(href);
    return linkResponse;
  }
}
