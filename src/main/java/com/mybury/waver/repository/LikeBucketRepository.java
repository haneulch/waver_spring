package com.mybury.waver.repository;

import com.mybury.waver.domain.LikeBucket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Set;

public interface LikeBucketRepository extends JpaRepository<LikeBucket, Long> {
  boolean existsByUserIdAndBucketId(long userId, long bucketId);

  void deleteByUserIdAndBucketId(long userId, long bucketId);
  
  List<LikeBucket> findByUserIdAndBucketIdIn(long userId, Set<Long> bucketIds);
}
