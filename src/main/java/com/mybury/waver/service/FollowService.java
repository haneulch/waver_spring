package com.mybury.waver.service;

import com.mybury.waver.domain.Follow;
import com.mybury.waver.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
  private final FollowRepository followRepository;

  public void follow(Long userId, Long targetUserId) {
    followRepository.insertFollow(userId, targetUserId);
  }
}
