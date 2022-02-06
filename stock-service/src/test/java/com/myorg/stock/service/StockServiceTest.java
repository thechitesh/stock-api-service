package com.myorg.stock.service;

import com.myorg.stock.config.StockProperties;
import com.myorg.stock.exception.StockNotFoundException;
import com.myorg.stock.mapper.LinkMapper;
import com.myorg.stock.mapper.StockMapper;
import com.myorg.stock.model.LinkResponse;
import com.myorg.stock.model.Links;
import com.myorg.stock.model.PageableContent;
import com.myorg.stock.model.Price;
import com.myorg.stock.model.Stock;
import com.myorg.stock.model.StockEntity;
import com.myorg.stock.repository.StockRepository;
import com.myorg.stock.util.ModelProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.myorg.stock.util.ModelProvider.createPrice;
import static com.myorg.stock.util.ModelProvider.createStock;
import static com.myorg.stock.util.ModelProvider.createStockEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

  @InjectMocks
  private StockService stockService;
  @Mock
  private StockMapper stockMapper;
  @Mock
  private StockRepository stockRepository;
  @Mock
  private LinkMapper linkMapper;
  @Mock
  private StockProperties stockProperties;


  Stock stock1 = createStock(1L, "Test 1", BigDecimal.ONE);
  Stock stock2 = createStock(2L, "Test 2", BigDecimal.TEN);
  StockEntity stockEntity1 = createStockEntity(1L, "Test 1", BigDecimal.ONE);
  StockEntity stockEntity2 = createStockEntity(2L, "Test 2", BigDecimal.TEN);

  @Test
  void testGetStocks() {

    List<Stock> stockList = List.of(this.stock1, stock2);
    List<StockEntity> stockEntityList = List.of(stockEntity1, stockEntity2);

    Page<StockEntity> stockEntityPage = Mockito.mock(Page.class);

    Links links = ModelProvider.createLinks("/self", "/prev", "/next");

    PageableContent expectedContent = new PageableContent();
    expectedContent.setLinks(links);
    expectedContent.setStocks(stockList);

    when(stockProperties.getPageSize()).thenReturn(3);
    when(stockRepository.findAll(any(Pageable.class))).thenReturn(stockEntityPage);
    when(stockEntityPage.getContent()).thenReturn(stockEntityList);
    when(stockEntityPage.getTotalPages()).thenReturn(4);
    when(linkMapper.buildLinks(anyInt(), anyInt(), anyInt())).thenReturn(links);
    when(stockMapper.mapToPageableContent(stockEntityList, links)).thenReturn(expectedContent);

    Optional<PageableContent> pageableContent = stockService.getStockByPage(1);

    assertTrue(pageableContent.isPresent());
    assertThat(pageableContent.get().getStocks()).isSameAs(stockList);
    assertThat(pageableContent.get().getLinks()).isNotNull()
        .hasFieldOrPropertyWithValue("self.href", "/self")
        .hasFieldOrPropertyWithValue("next.href", "/next")
        .hasFieldOrPropertyWithValue("prev.href", "/prev")
    ;

  }

  @Test
  void testAddStock() {
    LinkResponse linkResponse = new LinkResponse();
    linkResponse.setHref("/self");

    when(stockMapper.mapToStockEntity(stock1)).thenReturn(stockEntity1);
    when(stockRepository.save(any(StockEntity.class))).thenReturn(stockEntity1);
    when(linkMapper.buildAddedStockLink(anyLong())).thenReturn(linkResponse);
    LinkResponse createResourceLink = stockService.addStock(stock1);

    verify(stockRepository, times(1)).save(stockEntity1);
    assertThat(createResourceLink).isSameAs(linkResponse);
  }

  @Test
  void testFindStockById() {
    when(stockRepository.findById(anyLong())).thenReturn(Optional.of(stockEntity1));
    when(stockMapper.mapToStock(stockEntity1)).thenReturn(stock1);
    Optional<Stock> stockById = stockService.findStockById(1L);
    assertTrue(stockById.isPresent());
    assertEquals(stock1, stockById.get());
  }

  @Test
  void testUpdatePrice() {
    Long stockId = 1L;
    Price price = createPrice(BigDecimal.valueOf(10));

    when(stockRepository.findById(stockId)).thenReturn(Optional.of(stockEntity1));
    when(stockMapper.updatePriceOfStockEntity(stockEntity1, price)).thenReturn(stockEntity2);
    stockService.updatePrice(stockId, price);
    verify(stockRepository, times(1)).save(stockEntity2);
  }

  @Test
  void testUpdatePrice_NotFoundException() {
    Long stockId = 11L;
    Price price = createPrice(BigDecimal.valueOf(10));

    when(stockRepository.findById(stockId)).thenReturn(Optional.empty());
    Exception exception = assertThrows(StockNotFoundException.class, () -> stockService.updatePrice(stockId, price));
    assertTrue(exception.getMessage().contains("Stock not found"));
    verify(stockRepository, never()).save(any());
  }

  @Test
  void testDeleteStockById() {
    Long stockId = 11L;
    stockService.deleteStock(stockId);
    verify(stockRepository, times(1)).deleteById(stockId);
  }

  @Test
  void testDeleteStock_NotFoundException() {
    Long stockId = 123L;
    doThrow(new EmptyResultDataAccessException(stockId.intValue())).when(stockRepository).deleteById(stockId);
    assertThrows(StockNotFoundException.class, () -> stockService.deleteStock(stockId));
  }
}