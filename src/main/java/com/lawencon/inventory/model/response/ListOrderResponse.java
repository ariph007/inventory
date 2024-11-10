package com.lawencon.inventory.model.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListOrderResponse<T> {
  private T data;
  private PageResponse pageResponse;
}
