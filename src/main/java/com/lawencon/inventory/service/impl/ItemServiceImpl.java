package com.lawencon.inventory.service.impl;

import com.lawencon.inventory.helper.CustomResponseException;
import com.lawencon.inventory.model.request.CreateItemRequest;
import com.lawencon.inventory.model.request.PagingRequest;
import com.lawencon.inventory.model.request.UpdateItemRequest;
import com.lawencon.inventory.model.response.ItemResponse;
import com.lawencon.inventory.model.response.Responses;
import com.lawencon.inventory.model.response.PageResponse;
import com.lawencon.inventory.persistence.entity.Item;
import com.lawencon.inventory.persistence.repository.ItemRepository;
import com.lawencon.inventory.service.InventoryService;
import com.lawencon.inventory.service.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
  private final ItemRepository itemRepository;

  @Setter(onMethod_ = @Autowired, onParam_ = @Lazy)
  private InventoryService inventoryService;


  @Override
  public ItemResponse findById(Long id, Boolean showStock) {
    return mappingToResponse(getById(id), showStock);
  }

  @Override
  public Responses<List<ItemResponse>> findAll(PagingRequest pagingRequest, Boolean showStock) {
    Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());

    Page<Item> items = itemRepository.findAll(pageable);
    List<Item> listOfItem = items.getContent();
    List<ItemResponse> content = listOfItem.stream().map(item-> mappingToResponse(item, showStock)).toList();
    Responses<List<ItemResponse>> response = new Responses<>();
    response.setData(content);
    response.setPageResponse(PageResponse.builder().pageNo(items.getNumber()).pageSize(
        items.getSize()).totalElements(items.getTotalElements()).totalPages(items.getTotalPages()).last(items.isLast()).build());
    return response;
  }

  @Override
  public void edit(UpdateItemRequest updateItemRequest) {
    Item item =  getById(updateItemRequest.getId());
    BeanUtils.copyProperties(updateItemRequest, item);
    itemRepository.saveAndFlush(item);
  }

  @Override
  public void deleteById(Long id) {
    boolean isItemUsed = inventoryService.existByItemId(id);
    if(isItemUsed){
      throw new CustomResponseException(HttpStatus.BAD_REQUEST, "can't delete item that used in transaction");
    }
    Item item = getById(id);
    itemRepository.delete(item);
  }

  @Override
  public void add(CreateItemRequest createItemRequest) {
    Item item = new Item();
    BeanUtils.copyProperties(createItemRequest, item);
    itemRepository.saveAndFlush(item);
  }

  @Override
  public Item getById(Long id) {
    return itemRepository.findById(id).orElseThrow(
        () -> new CustomResponseException(HttpStatus.BAD_REQUEST, "Item not found")
    );
  }

  @Override
  public boolean existById(Long id) {
    return itemRepository.existsById(id);
  }

  private ItemResponse mappingToResponse(Item item, Boolean showStock){
    ItemResponse itemResponse = new ItemResponse();
    BeanUtils.copyProperties(item, itemResponse);
    if(showStock != null && showStock){
      Integer stock = inventoryService.getStockByItemId(item.getId());
      itemResponse.setStock(stock);
    }
    return itemResponse;
  }

}
