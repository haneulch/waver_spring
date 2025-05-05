package com.mybury.waver.dto.feed;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record KeywordRequest(
    @Size(min = 1)
    @NotNull
    List<Integer> keywordIds
) {
}
