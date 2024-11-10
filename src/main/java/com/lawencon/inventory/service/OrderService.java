package com.lawencon.inventory.service;

import com.lawencon.inventory.model.request.CreateOrderRequest;
import com.lawencon.inventory.model.request.PagingRequest;
import com.lawencon.inventory.model.request.UpdateOrderRequest;
import com.lawencon.inventory.model.response.OrderResponse;
import com.lawencon.inventory.model.response.Responses;
import com.lawencon.inventory.persistence.entity.Order;
import java.util.List;

public interface OrderService {
  OrderResponse findById(Long id);
  Responses<List<OrderResponse>> findAll(PagingRequest pagingRequest);
  void edit(UpdateOrderRequest updateOrderRequest);
  void deleteById(Long id);
  void add(CreateOrderRequest createOrderRequest);
  Order getById(Long id);
}
