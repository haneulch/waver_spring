package com.mybury.waver.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesNo {
    Y("Y"), N("N");
    private final String value;
}
