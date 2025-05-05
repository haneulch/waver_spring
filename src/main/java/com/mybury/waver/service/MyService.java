package com.mybury.waver.service;

import com.mybury.waver.domain.Bucket;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyService {

  private final BucketRepository bucketRepository;
  private final FollowRepository followRepository;

  public void my(Long userId) {
    List<Bucket> buckets = bucketRepository.findByUserId(userId);

    long followerCount = followRepository.countByUserId(userId);
    long followingCount = followRepository.countByFollowUserId(userId);
  }

}
