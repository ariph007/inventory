package com.lawencon.inventory.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Responses<T> {
  private T data;
  private PageResponse pageResponse;
}
