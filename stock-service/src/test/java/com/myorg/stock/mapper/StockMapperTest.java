package com.myorg.stock.mapper;

import com.myorg.stock.model.Links;
import com.myorg.stock.model.PageableContent;
import com.myorg.stock.model.Price;
import com.myorg.stock.model.Stock;
import com.myorg.stock.model.StockEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.myorg.stock.util.ModelProvider.createLinks;
import static com.myorg.stock.util.ModelProvider.createPrice;
import static com.myorg.stock.util.ModelProvider.createStock;
import static com.myorg.stock.util.ModelProvider.createStockEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StockMapperTest {

  @InjectMocks
  private StockMapper stockMapper;

  StockEntity stockEntity1 = createStockEntity(1L, "Test 1", BigDecimal.ONE);
  StockEntity stockEntity2 = createStockEntity(2L, "Test 2", BigDecimal.TEN);
  Stock stock1 = createStock(1L, "Test 1", BigDecimal.ONE);


  @Test
  void testMapToStockEntity() {
    StockEntity mappedStockEntity = stockMapper.mapToStockEntity(stock1);
    assertThat(mappedStockEntity)
        .hasFieldOrPropertyWithValue("name", "Test 1")
        .hasFieldOrPropertyWithValue("price", BigDecimal.ONE)
        .hasFieldOrPropertyWithValue("currency", Price.CurrencyEnum.EUR)
        ;
  }

  @Test
  void testMapToStock() {
    Stock mappedStock = stockMapper.mapToStock(stockEntity1);
    assertEquals(stock1, mappedStock);
  }

  @Test
  void testUpdatePriceOfStockEntity() {
    Price price = createPrice(BigDecimal.valueOf(12));
    StockEntity mappedStockEntity = stockMapper.updatePriceOfStockEntity(stockEntity1, price);
    assertEquals(price.getAmount(), mappedStockEntity.getPrice());
  }

  @Test
  void testMapToPageableContent() {
    Links links = createLinks("dummy-selfLink", null, "dummy-nextLink");
    PageableContent pageableContent = stockMapper.mapToPageableContent(List.of(stockEntity1, stockEntity2), links);
    assertThat(pageableContent).isNotNull();
    assertThat(pageableContent.getStocks()).isNotNull().isNotEmpty().hasSize(2);
    assertThat(pageableContent.getLinks())
        .hasFieldOrPropertyWithValue("self.href", "dummy-selfLink")
        .hasFieldOrPropertyWithValue("next.href", "dummy-nextLink")
        .hasFieldOrPropertyWithValue("prev.href", null)
    ;
  }
}