package com.realworld.common.exception;

import com.realworld.common.response.code.ExceptionResponseCode;
import lombok.Getter;

@Getter
public class CustomImageExceptionHandler extends RuntimeException {

  private final ExceptionResponseCode responseCode;

  public CustomImageExceptionHandler(ExceptionResponseCode responseCode) {
    super(responseCode.getMessage());
    this.responseCode = responseCode;
    }

}
