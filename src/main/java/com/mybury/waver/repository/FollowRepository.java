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
  @Query(value = """
      INSERT INTO follow (user_id, follow_user_id)
      SELECT :userId, :followUserId
      FROM DUAL
      WHERE NOT EXISTS (
        SELECT 1
        FROM follow
        WHERE user_id = :userId
          AND follow_user_id = :followUserId
      )
      """, nativeQuery = true)
  void insertFollow(Long userId, Long followUserId);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM Follow WHERE userId = :userId AND followUserId = :followUserId")
  void deleteFollow(long userId, long followUserId);

  @Query("SELECT COUNT(f) FROM Follow f WHERE f.userId = :userId AND f.followUser.deleteYn = 'N'")
  Long countByUserId(Long userId);

  @Query("SELECT COUNT(f) FROM Follow f WHERE f.followUserId = :followUserId AND f.user.deleteYn = 'N'")
  Long countByFollowUserId(Long followUserId);

  List<Follow> findByUserIdOrFollowUserId(Long userId, Long followUserId);

  boolean existsByUserIdAndFollowUserId(Long userId, Long followUserId);

  @Query("SELECT f.userId AS userId, f.followUserId AS followUserId FROM Follow f " +
         "WHERE (f.userId = :userId AND f.followUser.deleteYn = 'N') " +
         "   OR (f.followUserId = :followUserId AND f.user.deleteYn = 'N')")
  List<FollowCount> findCountByUserIdOrFollowUserId(long userId, long followUserId);
}
