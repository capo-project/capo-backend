package com.realworld.common.exception.custom;

import com.realworld.common.response.code.ErrorCode;
import lombok.Getter;

@Getter
public class CustomFileExceptionHandler extends RuntimeException {

  private final ErrorCode responseCode;

  public CustomFileExceptionHandler(ErrorCode responseCode) {
    super(responseCode.getMessage());
    this.responseCode = responseCode;
  }

}
