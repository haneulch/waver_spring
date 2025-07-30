package com.mybury.waver.advice;

import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.common.dto.BaseResponse;
import com.mybury.waver.exception.WaverException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class BaseControllerAdvice {

    @ExceptionHandler(WaverException.class)
    public ResponseEntity<BaseResponse<Void>> handleWaverException(WaverException e) {
        return ResponseEntity.ok(BaseResponse.error(e.getResultCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<String>> handleValidation(MethodArgumentNotValidException e) {
        if (e.getBindingResult().hasErrors()) {
            FieldError error = e.getBindingResult().getFieldErrors().get(0); // 첫 번째 에러만 응답
            String message = String.format("[%s] %s", error.getField(), error.getDefaultMessage());
            return ResponseEntity.ok(BaseResponse.error(ResultCode.VALIDATION_FAILED, message));
        }
        return ResponseEntity.ok(BaseResponse.error(ResultCode.VALIDATION_FAILED, e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<String>> handleMissingBody(HttpMessageNotReadableException e) {
        String message = e.getMessage();
        if (message.contains(":")) {
            return ResponseEntity.ok(BaseResponse.error(ResultCode.VALIDATION_FAILED, message.split(":")[0]));
        }
        return ResponseEntity.ok(BaseResponse.error(ResultCode.VALIDATION_FAILED, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleException(Exception e) {
        return ResponseEntity.ok(BaseResponse.error(ResultCode.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}

