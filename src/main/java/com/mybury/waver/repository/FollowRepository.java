package com.mybury.waver.repository;

import com.mybury.waver.domain.Follow;
import com.mybury.waver.domain.vo.FollowCount;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Long> {

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO follow (user_id, follow_user_id) VALUES (:userId, :followUserId)", nativeQuery = true)
  void insertFollow(Long userId, Long followUserId);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM Follow WHERE userId = :userId AND followUserId = :followUserId")
  void deleteFollow(long userId, long followUserId);

  Long countByUserId(Long userId);

  Long countByFollowUserId(Long followUserId);

  List<Follow> findByUserIdOrFollowUserId(Long userId, Long followUserId);

  boolean existsByUserIdAndFollowUserId(Long userId, Long followUserId);

  List<FollowCount> findCountByUserIdOrFollowUserId(long userId, long followUserId);
}
