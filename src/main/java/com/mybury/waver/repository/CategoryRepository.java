package com.mybury.waver.repository;

import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Category;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.util.StringUtils;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  default List<Category> findCategories(long userId, String query) {
    if (StringUtils.hasText(query)) {
      return findByUserIdAndNameContainsAndDeleted(userId, query, YesNo.N);
    }
    return findByUserIdAndDeleted(userId, YesNo.N);
  }

  List<Category> findByUserIdAndDeleted(long userId, YesNo deleted);

  List<Category> findByUserIdAndNameContainsAndDeleted(long userId, String query, YesNo deleted);

  @Query(value = "SELECT id FROM Category WHERE userId = :userId AND defaultYn = :defaultYn")
  Long findIdByUserIdAndDefaultYn(long userId, YesNo defaultYn);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Category SET name = :name WHERE id = :id AND userId = :userId")
  void updateName(long id, long userId, String name);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Category SET deleted = 'Y' WHERE id = :id AND userId = :userId")
  void delete(long id, long userId);
}
