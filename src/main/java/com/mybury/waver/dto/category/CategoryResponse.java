package com.mybury.waver.dto.category;

import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Category;

public record CategoryResponse(
    Long id,
    String name,
    YesNo defaultYn,
    int bucketCount
) {
  public CategoryResponse(Category category) {
    this(category.getId(), category.getName(), category.getDefaultYn(), category.getBuckets().size());
  }
}
