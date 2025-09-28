package com.mybury.waver.repository;

import com.mybury.waver.domain.Bucket;
import com.mybury.waver.web.message.v1.bucket.BucketRequest;
import java.util.List;

public interface BucketRepositoryCustom {

    List<Bucket> findBucket(Long userId, BucketRequest request);

    List<Bucket> findFeed();

    List<Bucket> search(String text);

    void commit();
}
