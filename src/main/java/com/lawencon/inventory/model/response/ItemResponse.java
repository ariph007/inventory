package com.lawencon.inventory.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
  private Long id;
  private String name;
  private Double price;
  private Integer stock;
  private Long version;
}
