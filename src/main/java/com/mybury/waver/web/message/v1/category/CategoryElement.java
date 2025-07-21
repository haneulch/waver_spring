package com.mybury.waver.web.message.v1.category;

import com.mybury.waver.domain.Category;

public record CategoryElement(
    long id,
    String name
) {

    public CategoryElement(Category category) {
        this(category.getId(), category.getName());
    }
}
