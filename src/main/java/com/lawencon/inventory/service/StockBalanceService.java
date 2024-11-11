package com.lawencon.inventory.service;

import com.lawencon.inventory.persistence.entity.Inventory.Type;
import com.lawencon.inventory.persistence.entity.StockBalance;
import java.util.Optional;

public interface StockBalanceService {
  Optional<StockBalance> getStockByItemId(Long id);
  void updateStock(Long itemId, Integer quantity, Type type);
}