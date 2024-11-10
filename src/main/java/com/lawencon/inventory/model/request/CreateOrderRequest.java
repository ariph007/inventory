package com.lawencon.inventory.model.request;

import com.lawencon.inventory.converter.ToUpperCase;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

  @NotNull(message = "Item id can't be empty")
  private Long itemId;

  @NotNull(message = "Quantity can't be empty")
  private Integer quantity;

  @NotNull(message = "Price can't be empty")
  private Double price;
}
