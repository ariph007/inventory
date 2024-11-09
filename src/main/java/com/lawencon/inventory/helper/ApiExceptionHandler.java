package com.lawencon.inventory.helper;

import com.lawencon.inventory.model.response.ApiErrorResponse;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ApiExceptionHandler {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    ApiErrorResponse apiException = ApiErrorResponse.builder().message(ex.getBody().getDetail()).errors(errors)
        .status(HttpStatus.BAD_REQUEST.value()).time(ZonedDateTime.now()).build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiException);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Object> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(
        ApiErrorResponse.builder().message(e.getMessage()).status(HttpStatus.BAD_REQUEST.value()).time(ZonedDateTime.now()).build(),
        HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Throwable.class)
  public ResponseEntity<Object> throwable(Throwable e) {
    return new ResponseEntity<>(
        ApiErrorResponse.builder().message(e.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).time(ZonedDateTime.now()).build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<Object> throwable(NoResourceFoundException e) {
    return new ResponseEntity<>(
        ApiErrorResponse.builder().message("No route for this request").status(HttpStatus.INTERNAL_SERVER_ERROR.value()).time(ZonedDateTime.now()).build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(CustomResponseException.class)
  public ResponseEntity<Object> handleCustomServiceException(CustomResponseException ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("status", ex.getStatus().value());
    errorResponse.put("time", LocalDateTime.now());
    return ResponseEntity.status(ex.getStatus()).body(errorResponse);
  }

}
