package com.mybury.waver.exception;

import com.mybury.waver.common.code.ResultCode;

public class WaverException extends RuntimeException {

    public WaverException(ResultCode errorCode) {
        super(errorCode.getCode());
    }
}
