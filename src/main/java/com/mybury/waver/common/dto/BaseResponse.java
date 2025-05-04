package com.mybury.waver.common.dto;

import com.mybury.waver.common.code.ResultCode;

public record BaseResponse<T>(
    boolean success,
    String code,
    String message,
    T data
) {

  public static <T> BaseResponse<T> ok() {
    ResultCode resultCode = ResultCode.SUCCESS;
    return new BaseResponse<>(true, resultCode.getCode(), resultCode.getDescription(), null);
  }

  public static <T> BaseResponse<T> ok(T data) {
    ResultCode resultCode = ResultCode.SUCCESS;
    return new BaseResponse<>(true, resultCode.getCode(), resultCode.getDescription(), data);
  }

  public static <T> BaseResponse<T> error(ResultCode resultCode) {
    return new BaseResponse<>(false, resultCode.getCode(), resultCode.getDescription(), null);
  }

  public static <T> BaseResponse<T> error(ResultCode resultCode, T data) {
    return new BaseResponse<>(false, resultCode.getCode(), resultCode.getDescription(), data);
  }
}