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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

  private final StockRepository stockRepository;
  private final StockMapper stockMapper;
  private final LinkMapper linkMapper;
  private final StockProperties stockProperties;

  public Optional<PageableContent> getStockByPage(int page) {
    int currentPageIndex;
    if (page < 1) {
      throw new IllegalArgumentException("Request to page number "+ page + " is not allowed");
    } else {
      currentPageIndex = page - 1;
    }
    log.trace("page {} with  size {}", currentPageIndex, stockProperties.getPageSize());

    Pageable stocksPage = PageRequest.of(currentPageIndex, stockProperties.getPageSize());
    Page<StockEntity> pagedStocks = stockRepository.findAll(stocksPage);

    if(pagedStocks.getContent() != null && !pagedStocks.getContent().isEmpty()) {
      Links links = linkMapper.buildLinks(pagedStocks.getTotalPages(), page, currentPageIndex);
      PageableContent pageableContent = stockMapper.mapToPageableContent(pagedStocks.getContent(), links);
      return Optional.of(pageableContent);
    } else {
      return Optional.empty();
    }
  }

  public LinkResponse addStock(Stock stock) {
    StockEntity stockEntity = stockMapper.mapToStockEntity(stock);
    StockEntity savedEntity = stockRepository.save(stockEntity);
    return linkMapper.buildAddedStockLink(savedEntity.getId());
  }

  public Optional<Stock> findStockById(Long stockId) {
    Optional<StockEntity> stockEntity = stockRepository.findById(stockId);
    return stockEntity.map(entity -> Optional.of(stockMapper.mapToStock(entity)))
        .orElse(Optional.empty());
  }

  public Stock updatePrice(Long stockId, Price updatedPrice) {
    Optional<StockEntity> stockById = stockRepository.findById(stockId);
    if (stockById.isEmpty()) {
      log.trace("Stock with id {} is not found", stockById);
      throw new StockNotFoundException("Stock not found");
    }
    StockEntity stockWithUpdatedPrice = stockMapper.updatePriceOfStockEntity(stockById.get(), updatedPrice);
    StockEntity save = stockRepository.save(stockWithUpdatedPrice);
    return stockMapper.mapToStock(save);
  }

  public void deleteStock(Long stockId) {
    log.trace("About to delete the stock with id {} ", stockId);
    try {
      stockRepository.deleteById(stockId);
    } catch (EmptyResultDataAccessException exp) {
      throw new StockNotFoundException("Stock with id "+stockId + ", is not found");
    }

  }
}
