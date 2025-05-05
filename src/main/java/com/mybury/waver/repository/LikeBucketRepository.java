package com.mybury.waver.repository;

import com.mybury.waver.domain.LikeBucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeBucketRepository extends JpaRepository<LikeBucket, Long> {
  boolean existsByUserIdAndBucketId(long userId, long bucketId);

  void deleteByUserIdAndBucketId(long userId, long bucketId);
}
