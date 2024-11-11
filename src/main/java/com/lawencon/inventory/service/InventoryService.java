package com.lawencon.inventory.service;

import com.lawencon.inventory.model.request.CreateInventoryRequest;
import com.lawencon.inventory.model.request.PagingRequest;
import com.lawencon.inventory.model.request.UpdateInventoryRequest;
import com.lawencon.inventory.model.response.InventoryResponse;
import com.lawencon.inventory.model.response.Responses;
import com.lawencon.inventory.persistence.entity.Inventory;
import java.util.List;

public interface InventoryService {
  InventoryResponse findById(Long id);
  Responses<List<InventoryResponse>> findAll(PagingRequest pagingRequest);
  void edit(UpdateInventoryRequest updateInventoryRequest);
  void deleteById(Long id);
  void add(CreateInventoryRequest createInventoryRequest);
  Inventory getById(Long id);
  Integer getStockByItemId(Long id);
  Inventory save(Inventory inventory);

  boolean existByItemId(Long id);
}
