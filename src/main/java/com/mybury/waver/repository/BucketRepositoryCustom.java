package com.mybury.waver.repository;

import com.mybury.waver.domain.Bucket;
import com.mybury.waver.web.message.v1.bucket.BucketRequest;

import java.util.List;

public interface BucketRepositoryCustom {

  List<Bucket> findBucket(Long userId, BucketRequest request);

  List<Bucket> findBucketExcludingIds(Long userId, BucketRequest request, List<Long> excludedBucketIds);

  List<Bucket> findFeed(List<String> keywords, Long myUserId, Long nextKey, List<Long> excludedBucketIds);

  List<Bucket> search(String text, List<Long> excludedBucketIds);

  void commit();
}
