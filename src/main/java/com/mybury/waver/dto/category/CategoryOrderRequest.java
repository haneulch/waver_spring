package com.mybury.waver.dto.category;

import java.util.List;

public record CategoryOrderRequest(
    List<Integer> categoryIds
) {
}
