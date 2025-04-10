package com.mybury.waver.exception;

import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.common.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(WaverException.class)
    public ResponseEntity<BaseResponse> handleWaverException(WaverException e) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new BaseResponse(ResultCode.get(e.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new BaseResponse(ResultCode.get(e.getMessage()), e.getCause()));
    }
}

