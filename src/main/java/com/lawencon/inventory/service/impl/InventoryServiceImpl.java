package com.lawencon.inventory.service.impl;

import com.lawencon.inventory.helper.CustomResponseException;
import com.lawencon.inventory.model.request.CreateInventoryRequest;
import com.lawencon.inventory.model.request.PagingRequest;
import com.lawencon.inventory.model.request.UpdateInventoryRequest;
import com.lawencon.inventory.model.response.InventoryResponse;
import com.lawencon.inventory.model.response.Responses;
import com.lawencon.inventory.model.response.PageResponse;
import com.lawencon.inventory.persistence.entity.Inventory;
import com.lawencon.inventory.persistence.entity.Inventory.Type;
import com.lawencon.inventory.persistence.entity.Item;
import com.lawencon.inventory.persistence.repository.InventoryRepository;
import com.lawencon.inventory.service.InventoryService;
import com.lawencon.inventory.service.ItemService;
import com.lawencon.inventory.service.StockBalanceService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {
  private final InventoryRepository inventoryRepository;
  private final ItemService itemService;
  private final StockBalanceService stockBalanceService;

  @Override
  public InventoryResponse findById(Long id) {
    return mappingToResponse(getById(id));
  }

  @Override
  public Responses<List<InventoryResponse>> findAll(PagingRequest pagingRequest) {
    Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());

    Page<Inventory> inventories = inventoryRepository.findAll(pageable);
    List<Inventory> listOfItem = inventories.getContent();
    List<InventoryResponse> content = listOfItem.stream().map(this::mappingToResponse).toList();
    Responses<List<InventoryResponse>> response = new Responses<>();
    response.setData(content);
    response.setPageResponse(PageResponse.builder().pageNo(inventories.getNumber()).pageSize(
        inventories.getSize()).totalElements(inventories.getTotalElements()).totalPages(inventories.getTotalPages()).last(inventories.isLast()).build());
    return response;
  }

  @Override
  public void edit(UpdateInventoryRequest updateInventoryRequest) {
    Inventory inventory =  getById(updateInventoryRequest.getId());
    validateItemExists(updateInventoryRequest.getItemId());
    Type type = validateType(updateInventoryRequest.getType());
    inventory.setType(type);
    BeanUtils.copyProperties(updateInventoryRequest, inventory);
    inventoryRepository.saveAndFlush(inventory);
  }

  private void validateItemExists(Long itemId) {
    boolean isItemExist = itemService.existById(itemId);
    if(!isItemExist){
      throw new CustomResponseException(HttpStatus.BAD_REQUEST, "Item not found");
    }
  }

  @Override
  public void deleteById(Long id) {
    inventoryRepository.delete(getById(id));
  }

  @Override
  public void add(CreateInventoryRequest createInventoryRequest) {
    Item item = validateItemExist(createInventoryRequest.getItemId());
    Type type = validateType(createInventoryRequest.getType());
    Inventory inventory = new Inventory();
    BeanUtils.copyProperties(createInventoryRequest, inventory);
    inventory.setItem(item);
    inventory.setType(type);
    Inventory savedInventory = inventoryRepository.saveAndFlush(inventory);
    stockBalanceService.updateStock(savedInventory.getItem().getId(), savedInventory.getQuantity(), savedInventory.getType());
  }


  @Override
  public Inventory getById(Long id) {
    return inventoryRepository.findById(id).orElseThrow(
        () -> new CustomResponseException(HttpStatus.BAD_REQUEST, "inventory not found"));
  }

  @Override
  public Integer getStockByItemId(Long id) {
    return inventoryRepository.getStockByItemId(id);
  }

  @Override
  public Inventory save(Inventory inventory) {
    return inventoryRepository.saveAndFlush(inventory);
  }

  private InventoryResponse mappingToResponse(Inventory inventory) {
    InventoryResponse response = new InventoryResponse();
    BeanUtils.copyProperties(inventory, response);
    response.setItemId(inventory.getItem().getId());
    response.setItemName(inventory.getItem().getName());
    return response;
  }

  @Override
  public boolean existByItemId(Long id) {
    return inventoryRepository.existsByItemId(id);
  }

  private Item validateItemExist(Long id){
     return itemService.getById(id);
  }

  private Type validateType(String typeRequest){
    Type type = Type.valueOf(typeRequest);
    if(type.name() != "T" && type.name() != "W"){
      throw  new CustomResponseException(HttpStatus.BAD_REQUEST,"Wrong type");
    }
    return type;
  }
}
