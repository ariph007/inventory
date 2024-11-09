package com.lawencon.inventory.service;

import com.lawencon.inventory.model.request.CreateItemRequest;
import com.lawencon.inventory.model.request.PagingRequest;
import com.lawencon.inventory.model.request.UpdateItemRequest;
import com.lawencon.inventory.model.response.ItemResponse;
import com.lawencon.inventory.model.response.ListItemResponse;
import com.lawencon.inventory.persistence.entity.Item;

public interface ItemService {
  ItemResponse findById(Long id, Boolean showStock);
  ListItemResponse findAll(PagingRequest pagingRequest, Boolean showStock);
  void edit(UpdateItemRequest updateItemRequest);
  void deleteById(Long id);
  void add(CreateItemRequest createItemRequest);
  Item getById(Long id);
}
