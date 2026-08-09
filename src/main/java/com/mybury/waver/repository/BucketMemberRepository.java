package com.mybury.waver.repository;

import com.mybury.waver.domain.BucketMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketMemberRepository extends JpaRepository<BucketMember, Long> {

  List<BucketMember> findByBucketId(Long bucketId);

  Optional<BucketMember> findByBucketIdAndUserId(Long bucketId, Long userId);

  List<BucketMember> findByUserIdAndBucketIdIn(Long userId, List<Long> bucketIds);
}
