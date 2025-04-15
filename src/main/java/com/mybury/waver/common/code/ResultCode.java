package com.mybury.waver.common.code;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS("2000", "SUCCESS"),
    BAD_REQUEST("4000", "BAD_REQUEST"),
    VALIDATION_FAILED("4001", "VALIDATION_FAILED"),
    UNAUTHORIZED("4010", "UNAUTHORIZED"),
    NOT_FOUND("4040", "NOT_FOUND"),
    INTERNAL_SERVER_ERROR("5000", "INTERNAL_SERVER_ERROR"),
    TOKEN_EXPIRED("5001", "TOKEN_EXPIRED"),
    INVALID_TOKEN("5002", "INVALID_TOKEN"),

    CATEGORY_CANNOT_DUPLICATE("6000", "CATEGORY_CANNOT_DUPLICATE"),

    UNKNOWN("9999", "UNKNOWN"),
    ;

    private static final Map<String, ResultCode> resultCodes = Arrays.stream(values())
        .collect(Collectors.toMap(ResultCode::getCode, Function.identity()));

    public static ResultCode get(String code) {
        return resultCodes.getOrDefault(code, UNKNOWN);
    }

    private final String code;
    private final String description;
}
