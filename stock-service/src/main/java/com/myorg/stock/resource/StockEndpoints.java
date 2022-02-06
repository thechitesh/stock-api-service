package com.myorg.stock.resource;

import com.myorg.stock.api.StocksApi;
import com.myorg.stock.model.LinkResponse;
import com.myorg.stock.model.PageableContent;
import com.myorg.stock.model.Price;
import com.myorg.stock.model.Stock;
import com.myorg.stock.service.StockService;
import com.myorg.stock.validators.StockValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StockEndpoints implements StocksApi {

  private final StockService stockService;

  @Override
  public ResponseEntity<PageableContent> getAllStock(Integer page) {
    Optional<PageableContent> stockByPage = stockService.getStockByPage(page);
    return stockByPage.map(ResponseEntity::ok)
        .orElse(ResponseEntity.noContent().build());
  }

  @Override
  public ResponseEntity<Stock> getStockById(Long stockId) {
    Optional<Stock> stockById = stockService.findStockById(stockId);
    return stockById.map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
  }

  @Override
  public ResponseEntity<LinkResponse> addStock(@Valid Stock stock) {
    StockValidator.validateStock(stock);
    LinkResponse linkResponse = stockService.addStock(stock);
    return  new ResponseEntity<>(linkResponse, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<Stock> updateStockPrice(Long stockId, Price price) {
    StockValidator.validatePrice(price);
    Stock stock = stockService.updatePrice(stockId, price);
    return ResponseEntity.ok(stock);
  }

  @Override
  public ResponseEntity<Void> deleteStockById(Long stockId) {
    stockService.deleteStock(stockId);
    return ResponseEntity.accepted().build();
  }
}
