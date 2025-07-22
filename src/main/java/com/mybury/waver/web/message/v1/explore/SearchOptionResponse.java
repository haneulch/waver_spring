package com.mybury.waver.web.message.v1.explore;

import com.mybury.waver.common.code.FixedKeyword;
import java.util.Arrays;
import java.util.List;

public record SearchOptionResponse(
    List<String> recentSearch,
    List<String> popularSearch,
    List<KeywordElement> recommendKeywords
) {

    public record KeywordElement(
        String code,
        String name
    ) {

        public static List<KeywordElement> getAllKeywords() {
            return Arrays.stream(FixedKeyword.values()).map(
                value -> new KeywordElement(value.getCode(), value.getName())
            ).toList();
        }
    }
}
