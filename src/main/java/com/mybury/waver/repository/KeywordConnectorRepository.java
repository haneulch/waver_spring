package com.mybury.waver.repository;

import com.mybury.waver.domain.KeywordConnector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordConnectorRepository extends JpaRepository<KeywordConnector, Long> {
  List<KeywordConnector> findByUserIdAndBucketId(long userId, long bucketId);
}
