package com.mybury.waver.web.message.v1.utility;

import com.mybury.waver.common.code.CodeEnum;

public record CodeResponse(
    String code,
    String content
) {

    public CodeResponse(CodeEnum codeEnum) {
        this(codeEnum.name(), codeEnum.getContent());
    }
}
