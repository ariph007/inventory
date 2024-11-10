package com.lawencon.inventory.model.response;

import com.lawencon.inventory.persistence.entity.Inventory.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
  private Long id;
  private String orderNo;
  private Long itemId;
  private String itemName;
  private Integer quantity;
  private Double price;
  private Long version;
}
