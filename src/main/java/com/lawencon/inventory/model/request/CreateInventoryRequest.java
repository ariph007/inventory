package com.lawencon.inventory.model.request;

import com.lawencon.inventory.converter.ToUpperCase;
import com.lawencon.inventory.persistence.entity.Inventory.Type;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateInventoryRequest {

  @NotNull(message = "Item id can't be empty")
  private Long itemId;

  @NotNull(message = "quantity can't be empty")
  private Integer quantity;

  @NotNull(message = "type can't be empty")
  @ToUpperCase
  private String type;
}
