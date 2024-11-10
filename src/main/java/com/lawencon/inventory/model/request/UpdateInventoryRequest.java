package com.lawencon.inventory.model.request;

import com.lawencon.inventory.persistence.entity.Inventory;
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
public class UpdateInventoryRequest {
  @NotNull(message = "id is required.")
  private Long id;

  @NotNull(message = "item id can't be empty")
  private Long itemId;

  @NotNull(message = "quantity can't be empty")
  private Integer quantity;

  @NotBlank(message = "item id can't be blank")
  private Inventory.Type type;

  @NotNull(message = "version is required.")
  private Long version;
}
