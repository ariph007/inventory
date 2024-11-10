package com.lawencon.inventory.model.request;

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

public class UpdateItemRequest {
  @NotNull(message = "id is required.")
  private Long id;

  @NotNull(message = "name can't be empty")
  private String name;

  @NotNull(message = "price can't be empty")
  private Double price;

  @NotNull(message = "version is required.")
  private Long version;
}
