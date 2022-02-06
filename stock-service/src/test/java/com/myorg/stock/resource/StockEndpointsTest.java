package com.myorg.stock.resource;

import com.myorg.stock.model.LinkResponse;
import com.myorg.stock.model.Links;
import com.myorg.stock.model.PageableContent;
import com.myorg.stock.model.Price;
import com.myorg.stock.model.Stock;
import com.myorg.stock.service.StockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.myorg.stock.util.ModelProvider.createLinks;
import static com.myorg.stock.util.ModelProvider.createPrice;
import static com.myorg.stock.util.ModelProvider.createStock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class StockEndpointsTest {

  @Mock
  private StockService stockService;
  @InjectMocks
  private StockEndpoints stockEndpoints;

  Stock stock1 = createStock(1L, "Test 1", BigDecimal.ONE);
  Stock stock2 = createStock(2L, "Test 2", BigDecimal.TEN);
  List<Stock> stocks = List.of(stock1, stock2);

  @Test
  void testGetAllStocks() {
    Links links = createLinks("/self", null, "/next");
    PageableContent pageableContent = createPageableContent(stocks, links);

    when(stockService.getStockByPage(3)).thenReturn(Optional.of(pageableContent));

    ResponseEntity<PageableContent> responseEntity = stockEndpoints.getAllStock(3);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(stocks, responseEntity.getBody().getStocks());
    assertEquals(links, responseEntity.getBody().getLinks());
  }

  @Test
  void testAllStocks_NoContent() {
    when(stockService.getStockByPage(333)).thenReturn(Optional.empty());
    ResponseEntity<PageableContent> responseEntity = stockEndpoints.getAllStock(333);
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
  }

  @Test
  void testAddStock() {
    LinkResponse lResponse = new LinkResponse();
    lResponse.setHref("/stock/1");
    when(stockService.addStock(stock1)).thenReturn(lResponse);
    ResponseEntity<LinkResponse> responseEntity = stockEndpoints.addStock(stock1);
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(lResponse, responseEntity.getBody());
  }

  @Test
  void testGetStocksById() {
    when(stockService.findStockById(1L)).thenReturn(Optional.of(stock1));
    ResponseEntity<Stock> responseEntity = stockEndpoints.getStockById(1L);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(stock1, responseEntity.getBody());
  }

  @Test
  void testGetStocksById_NoContent() {
    when(stockService.findStockById(111L)).thenReturn(Optional.empty());
    ResponseEntity<Stock> responseEntity = stockEndpoints.getStockById(111L);
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
  }

  @Test
  void testUpdateStockPrice() {
    Price price = createPrice(BigDecimal.valueOf(10.10d));
    when(stockService.updatePrice(2L, price)).thenReturn(stock2);
    ResponseEntity<Stock> responseEntity = stockEndpoints.updateStockPrice(2L, price);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }


  @Test
  void testDeleteStock() {
    stockEndpoints.deleteStockById(2L);
    verify(stockService, times(1)).deleteStock(2L);
  }

  private PageableContent createPageableContent(List<Stock> stocks, Links links) {
    PageableContent pageableContent = new PageableContent();
    pageableContent.setStocks(stocks);
    pageableContent.setLinks(links);
    return pageableContent;
  }
}