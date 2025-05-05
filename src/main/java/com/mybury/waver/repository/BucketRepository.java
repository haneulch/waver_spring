package com.mybury.waver.repository;

import com.mybury.waver.domain.Bucket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BucketRepository extends JpaRepository<Bucket, Long>, BucketRepositoryCustom {
  List<Bucket> findByUserId(Long userId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Bucket SET likeCount = likeCount + :count WHERE id = :id")
  void updateLike(long id, int count);
}
