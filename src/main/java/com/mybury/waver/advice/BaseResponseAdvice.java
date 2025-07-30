package com.mybury.waver.advice;

import com.mybury.waver.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Hidden
@RestControllerAdvice
public class BaseResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !BaseResponse.class.isAssignableFrom(returnType.getParameterType())
            && !ResponseEntity.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
        Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
        ServerHttpResponse response) {

        String path = request.getURI().getPath();
        // waver로 시작하는 요청에만 응답을 감싼다
        if (path.startsWith("/waver")) {
            return BaseResponse.ok(body);
        }

        if (body instanceof BaseResponse) {
            return body;
        }

        return body;
    }
}
