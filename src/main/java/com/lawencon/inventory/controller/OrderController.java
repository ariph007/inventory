package com.lawencon.inventory.controller;

import com.lawencon.inventory.model.request.CreateInventoryRequest;
import com.lawencon.inventory.model.request.CreateOrderRequest;
import com.lawencon.inventory.model.request.PagingRequest;
import com.lawencon.inventory.model.request.UpdateInventoryRequest;
import com.lawencon.inventory.model.request.UpdateOrderRequest;
import com.lawencon.inventory.model.response.InventoryResponse;
import com.lawencon.inventory.model.response.OrderResponse;
import com.lawencon.inventory.model.response.Responses;
import com.lawencon.inventory.model.response.TransactionResponse;
import com.lawencon.inventory.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping(value = "orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(orderService.findById(id));
  }

  @GetMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Responses<List<OrderResponse>>> findAll(PagingRequest pagingRequest) {
    return ResponseEntity.ok(orderService.findAll(pagingRequest));
  }

  @PostMapping(value = "orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> add(
      @RequestBody @Valid CreateOrderRequest createOrderRequest) {
    orderService.add(createOrderRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .status(HttpStatus.OK.value())
        .message("Order successfully added.")
        .build());
  }

  @PutMapping(value = "orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> edit(
      @Valid @RequestBody UpdateOrderRequest updateOrderRequest) {
    orderService.edit(updateOrderRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .status(HttpStatus.OK.value())
        .message("Order successfully updated.")
        .build());
  }

  @DeleteMapping(value = "orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> delete(@PathVariable Long id) {
    orderService.deleteById(id);
    return ResponseEntity.ok(TransactionResponse.builder()
        .status(HttpStatus.OK.value())
        .message("Order successfully deleted.")
        .build());
  }
}
