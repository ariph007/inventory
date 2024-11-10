package com.lawencon.inventory.controller;

import com.lawencon.inventory.model.request.CreateItemRequest;
import com.lawencon.inventory.model.request.PagingRequest;
import com.lawencon.inventory.model.request.UpdateItemRequest;
import com.lawencon.inventory.model.response.ItemResponse;
import com.lawencon.inventory.model.response.ListItemResponse;
import com.lawencon.inventory.model.response.TransactionResponse;
import com.lawencon.inventory.service.ItemService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping(value = "items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ItemResponse> findById(@PathVariable Long id,
      @RequestParam(required = false) Boolean showStock) {
    return ResponseEntity.ok(itemService.findById(id, showStock));
  }

  @GetMapping(value = "items", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListItemResponse> findAll(PagingRequest pagingRequest,
      @RequestParam(required = false) Boolean showStock) {
    return ResponseEntity.ok(itemService.findAll(pagingRequest, showStock));
  }

  @PostMapping(value = "items", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> add(
      @RequestBody @Valid CreateItemRequest createItemRequest) {
    itemService.add(createItemRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .status(HttpStatus.OK.value())
        .message("Item successfully added.")
        .build());
  }

  @PutMapping(value = "items", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> edit(
      @Valid @RequestBody UpdateItemRequest updateItemRequest) {
    itemService.edit(updateItemRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .status(HttpStatus.OK.value())
        .message("Item successfully updated.")
        .build());
  }

  @DeleteMapping(value = "items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> delete(@PathVariable Long id) {
    itemService.deleteById(id);
    return ResponseEntity.ok(TransactionResponse.builder()
        .status(HttpStatus.OK.value())
        .message("Item successfully deleted.")
        .build());
  }


}
