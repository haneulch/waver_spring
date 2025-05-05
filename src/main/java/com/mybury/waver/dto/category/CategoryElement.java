package com.mybury.waver.dto.category;

import com.mybury.waver.domain.Category;

public record CategoryElement(
    long id,
    String name
) {
  public CategoryElement(Category category) {
    this(category.getId(), category.getName());
  }
}
