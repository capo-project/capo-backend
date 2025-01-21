package com.realworld.common.exception.custom;

import com.realworld.common.response.code.ErrorCode;
import lombok.Getter;

@Getter
public class CustomImageExceptionHandler extends RuntimeException {

  private final ErrorCode responseCode;

  public CustomImageExceptionHandler(ErrorCode responseCode) {
    super(responseCode.getMessage());
    this.responseCode = responseCode;
    }

}
