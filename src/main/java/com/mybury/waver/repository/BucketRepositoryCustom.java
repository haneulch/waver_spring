package com.mybury.waver.repository;

import com.mybury.waver.domain.Bucket;
import com.mybury.waver.dto.bucket.BucketRequest;

import java.util.List;

public interface BucketRepositoryCustom {
  List<Bucket> findBucket(long userId, BucketRequest request);

  List<Bucket> findFeed();

  List<Bucket> search(String text);
}
