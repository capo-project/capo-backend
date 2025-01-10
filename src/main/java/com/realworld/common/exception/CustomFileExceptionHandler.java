package com.realworld.common.exception;

import com.realworld.common.response.code.ExceptionResponseCode;
import lombok.Getter;

@Getter
public class CustomFileExceptionHandler extends RuntimeException {

  private final ExceptionResponseCode responseCode;

  public CustomFileExceptionHandler(ExceptionResponseCode responseCode) {
    super(responseCode.getMessage());
    this.responseCode = responseCode;
  }

}
