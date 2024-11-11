package com.lawencon.inventory.service.impl;

import com.lawencon.inventory.helper.CustomResponseException;
import com.lawencon.inventory.model.request.CreateOrderRequest;
import com.lawencon.inventory.model.request.PagingRequest;
import com.lawencon.inventory.model.request.UpdateOrderRequest;
import com.lawencon.inventory.model.response.OrderResponse;
import com.lawencon.inventory.model.response.PageResponse;
import com.lawencon.inventory.model.response.Responses;
import com.lawencon.inventory.persistence.entity.Inventory;
import com.lawencon.inventory.persistence.entity.Inventory.Type;
import com.lawencon.inventory.persistence.entity.Item;
import com.lawencon.inventory.persistence.entity.Order;
import com.lawencon.inventory.persistence.entity.StockBalance;
import com.lawencon.inventory.persistence.repository.OrderRepository;
import com.lawencon.inventory.service.InventoryService;
import com.lawencon.inventory.service.ItemService;
import com.lawencon.inventory.service.OrderService;
import com.lawencon.inventory.service.StockBalanceService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final ItemService itemService;
  private final InventoryService inventoryService;
  private final StockBalanceService stockBalanceService;

  @Override
  public OrderResponse findById(Long id) {
    return mappingToResponse(getById(id));
  }

  @Override
  public Responses<List<OrderResponse>> findAll(PagingRequest pagingRequest) {
    Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());

    Page<Order> orders = orderRepository.findAll(pageable);
    List<Order> listOfOrder = orders.getContent();
    List<OrderResponse> content = listOfOrder.stream().map(this::mappingToResponse).toList();
    Responses<List<OrderResponse>> response = new Responses<>();
    response.setData(content);
    response.setPageResponse(PageResponse.builder().pageNo(orders.getNumber()).pageSize(
        orders.getSize()).totalElements(orders.getTotalElements()).totalPages(orders.getTotalPages()).last(orders.isLast()).build());
    return response;
  }

  @Transactional
  @Override
  public void edit(UpdateOrderRequest updateOrderRequest) {
      Optional<StockBalance> stockBalance = stockBalanceService.getStockByItemId(updateOrderRequest.getItemId());
      if(stockBalance.isEmpty()){
          throw new CustomResponseException(HttpStatus.BAD_REQUEST, "Stock balance not found for the requested item.");
      }
      Integer currentBalance = stockBalance.get().getCurrentBalance();
      Order order = getById(updateOrderRequest.getId());
      Integer differenceQuantity = order.getQuantity();
      BeanUtils.copyProperties(updateOrderRequest, order);
      orderRepository.saveAndFlush(order);
      Integer newBalance = (currentBalance + differenceQuantity) - updateOrderRequest.getQuantity();
      stockBalance.get().setCurrentBalance(newBalance);
      Integer currentWithdraw = stockBalance.get().getWithdraw();

      Integer newWithdraw =  currentWithdraw + differenceQuantity;
      stockBalance.get().setWithdraw(newWithdraw);
      stockBalanceService.saveStock(stockBalance.get());
  }

  @Override
  public void deleteById(Long id) {
    orderRepository.delete(getById(id));
  }

  @Transactional
  @Override
  public void add(CreateOrderRequest createOrderRequest) {
    validateItemExist(createOrderRequest.getItemId());
    //!check stock balance, if stock < order request quantity throw exception
    Optional<StockBalance> stockBalance  = stockBalanceService.getStockByItemId(createOrderRequest.getItemId());
    if(stockBalance.isEmpty()){
      throw new CustomResponseException(HttpStatus.BAD_REQUEST, "Stock balance not found for the requested item.");
    }
    Integer stock = stockBalance.get().getCurrentBalance();
    if(stock < createOrderRequest.getQuantity()){
      throw new CustomResponseException(HttpStatus.BAD_REQUEST, "Insufficient stock available for the requested item.");
    }
    Order order = new Order();
    BeanUtils.copyProperties(createOrderRequest, order);
    Item item = itemService.getById(createOrderRequest.getItemId());
    order.setItem(item);
    Order orderSuccess = orderRepository.saveAndFlush(order);
    Inventory inventory = new Inventory();
    inventory.setType(Type.W);
    inventory.setItem(orderSuccess.getItem());
    inventory.setQuantity(orderSuccess.getQuantity());
    Inventory savedInventory = inventoryService.save(inventory);
    stockBalanceService.updateStock(savedInventory.getItem().getId(),savedInventory.getQuantity(), savedInventory.getType());
  }

  @Override
  public Order getById(Long id) {
    return orderRepository.findById(id).orElseThrow(
        () -> new CustomResponseException(HttpStatus.BAD_REQUEST, "Order not found")
    );
  }


  private OrderResponse mappingToResponse(Order order) {
    OrderResponse orderResponse = new OrderResponse();
    BeanUtils.copyProperties(order, orderResponse);
    String prefix = "O";
    orderResponse.setOrderNo(prefix.concat(String.valueOf(order.getId())));
    return orderResponse;
  }

  private void validateItemExist(Long itemId){
    boolean isItemExist = itemService.existById(itemId);
    if(!isItemExist){
      throw new CustomResponseException(HttpStatus.BAD_REQUEST, "Item not found");
    }
  }

}
