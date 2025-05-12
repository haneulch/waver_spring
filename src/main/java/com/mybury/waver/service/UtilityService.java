package com.mybury.waver.service;

import com.mybury.waver.common.code.CodeEnum;
import com.mybury.waver.common.code.CodeGroup;
import com.mybury.waver.config.cache.CacheName;
import com.mybury.waver.dto.utility.CodeResponse;
import com.mybury.waver.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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
