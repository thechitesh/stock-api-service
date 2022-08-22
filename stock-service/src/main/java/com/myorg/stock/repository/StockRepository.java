package com.myorg.stock.repository;

import com.myorg.stock.model.StockEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends PagingAndSortingRepository<StockEntity, Long> {

  Page<StockEntity> findAll(Pageable pageable);
}
