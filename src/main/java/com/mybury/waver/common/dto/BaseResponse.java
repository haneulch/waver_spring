package com.mybury.waver.common.dto;

import com.mybury.waver.common.code.ResultCode;

public record BaseResponse<T>(
    String code,
    String message,
    T data
) {

    public static <T> BaseResponse<T> ok() {
        ResultCode resultCode = ResultCode.SUCCESS;
        return new BaseResponse<>(resultCode.getCode(), resultCode.getDescription(), null);
    }

    public static <T> BaseResponse<T> ok(T data) {
        ResultCode resultCode = ResultCode.SUCCESS;
        return new BaseResponse<>(resultCode.getCode(), resultCode.getDescription(), data);
    }

    public static <T> BaseResponse<T> error(ResultCode resultCode) {
        return new BaseResponse<>(resultCode.getCode(), resultCode.getDescription(), null);
    }

    public static <T> BaseResponse<T> error(ResultCode resultCode, T data) {
        return new BaseResponse<>(resultCode.getCode(), resultCode.getDescription(), data);
    }
}