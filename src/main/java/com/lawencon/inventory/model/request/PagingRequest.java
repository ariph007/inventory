package com.lawencon.inventory.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingRequest {
  private Integer page;

  private Integer pageSize;

  public Integer getPage() {
    return page - 1;
  }
}
