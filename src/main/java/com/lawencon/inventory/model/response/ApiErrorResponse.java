package com.lawencon.inventory.model.response;

import java.time.ZonedDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
public class ApiErrorResponse {
  private final String message;
  private final Map<String, String> errors;
  private final int status;
  private final ZonedDateTime time;
}
