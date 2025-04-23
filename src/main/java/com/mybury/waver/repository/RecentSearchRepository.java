package com.mybury.waver.repository;

import com.mybury.waver.domain.RecentSearch;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long> {
  
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM RecentSearch WHERE user.id = :userId AND query = :query")
  void deleteRecentSearchByUserIdAndQuery(long userId, String query);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM RecentSearch WHERE user.id = :userId")
  void deleteRecentSearchByUserId(long userId);
}
