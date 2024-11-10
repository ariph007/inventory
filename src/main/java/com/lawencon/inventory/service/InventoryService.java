package com.lawencon.inventory.service;

import com.lawencon.inventory.model.request.CreateInventoryRequest;
import com.lawencon.inventory.model.request.PagingRequest;
import com.lawencon.inventory.model.request.UpdateInventoryRequest;
import com.lawencon.inventory.model.response.InventoryResponse;
import com.lawencon.inventory.model.response.ListInventoryResponse;
import com.lawencon.inventory.persistence.entity.Inventory;

public interface InventoryService {
  InventoryResponse findById(Long id);
  ListInventoryResponse findAll(PagingRequest pagingRequest);
  void edit(UpdateInventoryRequest updateInventoryRequest);
  void deleteById(Long id);
  void add(CreateInventoryRequest createInventoryRequest);
  Inventory getById(Long id);
  boolean existByItemId(Long id);
  Integer getStockByItemId(Long id);
}
