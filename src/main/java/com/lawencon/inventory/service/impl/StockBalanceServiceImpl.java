package com.lawencon.inventory.service.impl;

import com.lawencon.inventory.helper.CustomResponseException;
import com.lawencon.inventory.persistence.entity.Inventory.Type;
import com.lawencon.inventory.persistence.entity.Item;
import com.lawencon.inventory.persistence.entity.StockBalance;
import com.lawencon.inventory.persistence.repository.StockBalanceRepository;
import com.lawencon.inventory.service.ItemService;
import com.lawencon.inventory.service.StockBalanceService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StockBalanceServiceImpl implements StockBalanceService {

  private final StockBalanceRepository stockBalanceRepository;
  private final ItemService itemService;

  @Override
  public Optional<StockBalance> getStockByItemId(Long id) {
    return stockBalanceRepository.findByItemId(id);
  }
  
  /**
   * Updates the stock balance for a specific item based on the given quantity and type.
   * If the stock balance already exists for the item, the quantity will be added or subtracted
   * based on the type (top-up or withdraw). If the stock balance does not exist, a new one will be created.
   *
   * @param itemId The unique identifier of the item.
   * @param quantity The quantity to be added or subtracted from the stock balance.
   * @param type The type of stock operation (top-up or withdraw).
   * @throws CustomResponseException If the type is invalid.
   */
  @Transactional
  @Override
  public void updateStock(Long itemId, Integer quantity, Type type) {
    Optional<StockBalance> stockBalance = stockBalanceRepository.findByItemId(itemId);
    StockBalance stock;
    if (stockBalance.isPresent()) {
      stock = stockBalance.get();
      Integer currentWithdraw = stock.getWithdraw();
      if (type == Type.T) {
        Integer updatedTopUp = stock.getCurrentBalance() + quantity;
        stock.setTopup(updatedTopUp);
        stock.setCurrentBalance(stock.getCurrentBalance() + quantity);
      } else if (type == Type.W) {
        Integer updatedWithdraw = currentWithdraw - quantity;
        stock.setWithdraw(updatedWithdraw);
        stock.setCurrentBalance(stock.getCurrentBalance() - quantity);
      } else {
        throw new CustomResponseException(HttpStatus.BAD_REQUEST, "Invalid type");
      }
    } else {
      stock = new StockBalance();
      Item item = itemService.getById(itemId);
      stock.setItem(item);
      Integer stockBegin = 0;
      if (type == Type.T) {
        stock.setTopup(quantity);
        stock.setWithdraw(0);
        stock.setCurrentBalance(stockBegin + quantity);
      } else {
        stock.setWithdraw(quantity);
        stock.setTopup(0);
        stock.setCurrentBalance(stockBegin - quantity);
      }
    }
    stockBalanceRepository.save(stock);
  }

  @Override
  public void saveStock(StockBalance stockBalance) {
    stockBalanceRepository.saveAndFlush(stockBalance);
  }
}
