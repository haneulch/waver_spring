package com.mybury.waver.service;

import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyService {

    private final BucketRepository bucketRepository;
    private final FollowRepository followRepository;

    public void my(Long userId) {
        bucketRepository.findByUser_Id(userId);

        long followerCount = followRepository.countByUser_Id(userId);
        long followingCount = followRepository.countByFollowUser_Id(userId);
    }

}
