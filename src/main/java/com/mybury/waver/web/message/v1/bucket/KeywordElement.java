package com.mybury.waver.web.message.v1.bucket;

import com.mybury.waver.common.code.FixedKeyword;

public record KeywordElement(
    String code,
    String name
) {

    public KeywordElement(FixedKeyword keyword) {
        this(keyword.getCode(), keyword.getName());
    }
}
