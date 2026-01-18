package com.mybury.waver.service;

import com.mybury.waver.domain.Category;
import com.mybury.waver.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public List<Category> getCategory(long userId, String query) {

    // 카테고리 조회
    List<Category> categories = categoryRepository.findCategories(userId, query);

    // 카테고리가 없는 경우 기본 카테고리 추가
    if (categories.isEmpty()) {
      categoryRepository.save(Category.createDefaultCategoryFor(userId));
      categories = categoryRepository.findCategories(userId, query);
    }

    return categories;
  }

  public void postCategory(long userId, String name) {
    categoryRepository.save(Category.builder().userId(userId).name(name).build());
  }

  @Transactional
  public void patchCategory(long id, long userId, String name) {
    categoryRepository.updateName(id, userId, name);
  }

  public void deleteCategory(long id, long userId) {
    categoryRepository.delete(id, userId);
  }

  @Transactional
  public void patchCategorySeq(long userId, List<Integer> categoryIds) {
    List<Category> categories = categoryRepository.findCategories(userId, "");

    Map<Long, Category> categoryMap = categories.stream()
        .collect(Collectors.toMap(Category::getId, Function.identity()));

    for (int i = 0; i < categoryIds.size(); i++) {
      long id = categoryIds.get(i);
      Category category = categoryMap.get(id);
      if (category != null) {
        category.setSeq(i + 1);
      }
    }

    categoryRepository.saveAll(categories);
  }
}
