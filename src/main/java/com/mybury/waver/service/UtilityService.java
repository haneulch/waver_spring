package com.mybury.waver.service;

import com.mybury.waver.common.code.CodeEnum;
import com.mybury.waver.common.code.CodeGroup;
import com.mybury.waver.config.cache.CacheName;
import com.mybury.waver.repository.ConfigRepository;
import com.mybury.waver.web.message.v1.utility.CodeResponse;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilityService {

    private final ConfigRepository configRepository;

    @Cacheable(cacheNames = CacheName.GROUP_CODE, key = "#groupCode.name()")
    public List<CodeResponse> getCodes(CodeGroup groupCode) {
        Class<? extends CodeEnum> clazz = groupCode.getClazz();
        CodeEnum[] values = clazz.getEnumConstants();

        return Arrays.stream(values)
            .map(CodeResponse::new)
            .toList();
    }
}
