package com.mybury.waver.repository;

import com.mybury.waver.domain.Follow;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
  @Modifying
  @Transactional
  @Query(value = "INSERT INTO follow (user_id, follow_user_id) VALUES (:userId, :followUserId)", nativeQuery = true)
  void insertFollow(Long userId, Long followUserId);

  Long countByUser_Id(Long userId);

  Long countByFollowUser_Id(Long followUserId);

  List<Follow> findByUserIdOrFollowUserId(Long userId, Long followUserId);
}
