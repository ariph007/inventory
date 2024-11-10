package com.lawencon.inventory.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

public class UpperCaseConverter extends StdConverter<String, String> {

  @Override
  public String convert(String value) {
    if(value == null) {
      return null;
    }
    return value.toUpperCase();
  }

}
