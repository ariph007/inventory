package com.lawencon.inventory.controller;

import com.lawencon.inventory.model.request.CreateInventoryRequest;
import com.lawencon.inventory.model.request.PagingRequest;
import com.lawencon.inventory.model.request.UpdateInventoryRequest;
import com.lawencon.inventory.model.response.InventoryResponse;
import com.lawencon.inventory.model.response.ListInventoryResponse;
import com.lawencon.inventory.model.response.TransactionResponse;
import com.lawencon.inventory.service.InventoryService;
import jakarta.validation.Valid;
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
public class InventoryController {

  private final InventoryService inventoryService;

  @GetMapping(value = "inventories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<InventoryResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(inventoryService.findById(id));
  }

  @GetMapping(value = "inventories", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListInventoryResponse> findAll(PagingRequest pagingRequest) {
    return ResponseEntity.ok(inventoryService.findAll(pagingRequest));
  }

  @PostMapping(value = "inventories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> add(
      @RequestBody @Valid CreateInventoryRequest createInventoryRequest) {
    inventoryService.add(createInventoryRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .status(HttpStatus.OK.value())
        .message("Inventory successfully added.")
        .build());
  }

  @PutMapping(value = "inventories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> edit(
      @Valid @RequestBody UpdateInventoryRequest updateInventoryRequest) {
    inventoryService.edit(updateInventoryRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .status(HttpStatus.OK.value())
        .message("Inventory successfully updated.")
        .build());
  }

  @DeleteMapping(value = "inventories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> delete(@PathVariable Long id) {
    inventoryService.deleteById(id);
    return ResponseEntity.ok(TransactionResponse.builder()
        .status(HttpStatus.OK.value())
        .message("Inventory successfully deleted.")
        .build());
  }
}
