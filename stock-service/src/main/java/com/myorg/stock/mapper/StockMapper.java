package com.myorg.stock.mapper;

import com.myorg.stock.model.Links;
import com.myorg.stock.model.PageableContent;
import com.myorg.stock.model.Price;
import com.myorg.stock.model.Stock;
import com.myorg.stock.model.StockEntity;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockMapper {

  public PageableContent mapToPageableContent(List<StockEntity> stockEntities, Links links) {
    List<Stock> stocks = mapToStockList(stockEntities);
    PageableContent pageableContent = new PageableContent();
    pageableContent.setStocks(stocks);
    pageableContent.setLinks(links);
    return pageableContent;
  }

  private List<Stock> mapToStockList(List<StockEntity> stockEntities) {
    return stockEntities.stream()
        .map(this::mapToStock)
        .collect(Collectors.toList());
  }

  public StockEntity mapToStockEntity(Stock stock) {
    StockEntity stockEntity = StockEntity.builder()
        .name(stock.getName())
        .createdAt(LocalDateTime.now())
        .symbol(stock.getSymbol())
        .build();
    val price = stock.getCurrentPrice();
    return stockEntity.toBuilder()
        .price(price.getAmount())
        .currency(price.getCurrency())
        .build();
  }

  public Stock mapToStock(StockEntity stockEntity) {
    Price price = new Price();
    price.setAmount(stockEntity.getPrice());
    price.setCurrency(stockEntity.getCurrency());

    Stock stock = new Stock();
    stock.setId(stockEntity.getId());
    stock.currentPrice(price);
    stock.setName(stockEntity.getName());
    stock.setSymbol(stockEntity.getSymbol());
    if (stockEntity.getLastUpdate() != null) {
      stock.setLastUpdate(stockEntity.getLastUpdate().toString());
    }
    return stock;
  }

  public StockEntity updatePriceOfStockEntity(StockEntity stockEntity, Price price) {
    return stockEntity.toBuilder()
        .price(price.getAmount())
        .currency(price.getCurrency())
        .lastUpdate(LocalDateTime.now())
        .build();
  }
}