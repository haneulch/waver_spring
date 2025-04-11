package com.mybury.waver.exception;

import com.mybury.waver.common.code.ResultCode;
import lombok.Getter;

@Getter
public class WaverException extends RuntimeException {

    private final ResultCode resultCode;

    public WaverException(ResultCode errorCode) {
        super(errorCode.getDescription());
        this.resultCode = errorCode;
    }
}
