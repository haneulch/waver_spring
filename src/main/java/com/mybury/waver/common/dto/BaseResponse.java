package com.mybury.waver.common.dto;

import com.mybury.waver.common.code.ResultCode;
import lombok.Getter;

@Getter
public class BaseResponse {

    private String code;
    private String message;

    public BaseResponse(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getDescription();
    }

    public BaseResponse(ResultCode resultCode, Throwable cause) {
        this.code = resultCode.getCode();
        this.message = cause != null ? cause.getMessage() : null;
    }
}