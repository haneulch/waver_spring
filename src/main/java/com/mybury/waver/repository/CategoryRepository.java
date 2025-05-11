package com.mybury.waver.repository;

import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.util.StringUtils;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  default List<Category> findCategories(long userId, String query) {
    if (StringUtils.hasText(query)) {
      return findByUserIdAndNameContainsAndDeleted(userId, query, YesNo.N);
    }
    return findByUserIdAndDeleted(userId, YesNo.N);
  }

  List<Category> findByUserIdAndDeleted(long userId, YesNo deleted);

  List<Category> findByUserIdAndNameContainsAndDeleted(long userId, String query, YesNo deleted);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Category SET name = :name WHERE id = :id AND userId = :userId")
  void updateName(long id, long userId, String name);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Category SET deleted = 'Y' WHERE id = :id AND userId = :userId")
  void delete(long id, long userId);
}
