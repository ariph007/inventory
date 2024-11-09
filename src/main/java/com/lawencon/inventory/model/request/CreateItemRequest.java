package com.lawencon.inventory.model.request;

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
public class CreateItemRequest {

  @NotBlank(message = "Name can't be blank")
  @NotNull(message = "Name can't be empty")
  private String name;

  @NotNull(message = "Price can't be empty")
  private Double price;
}
