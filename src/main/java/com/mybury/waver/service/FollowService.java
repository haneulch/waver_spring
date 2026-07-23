package com.mybury.waver.service;

import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Follow;
import com.mybury.waver.event.message.AlarmMessageEvent;
import com.mybury.waver.repository.FollowRepository;
import com.mybury.waver.repository.UserRepository;
import com.mybury.waver.web.message.v1.follow.FollowElement;
import com.mybury.waver.web.message.v1.follow.GetFollowersResponse;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

  private final FollowRepository followRepository;
  private final UserRepository userRepository;
  private final ApplicationEventPublisher publisher;

  @Transactional
  public void follow(Long userId, Long targetUserId) {
    if (!userRepository.existsByIdAndDeleteYn(targetUserId, YesNo.N)) {
      return;
    }

    if (followRepository.existsByUserIdAndFollowUserId(userId, targetUserId)) {
      return;
    }

    followRepository.insertFollow(userId, targetUserId);

    // targetUserId(팔로우 당한 사람)에게 userId(팔로워)가 팔로우했다는 알림 발행
    publisher.publishEvent(AlarmMessageEvent.follow(targetUserId, userId));
  }

  public GetFollowersResponse getFollowList(Long userId) {
    List<Follow> follows = new ArrayList<>();
    List<Follow> followers = new ArrayList<>();
    Set<Long> mutualIds = new HashSet<>();
    followInfo(userId, follows, followers, mutualIds);

    List<FollowElement> followElements = follows.stream()
        .map(f -> FollowElement.follow(f, mutualIds.contains(f.getFollowUserId())))
        .toList();

    List<FollowElement> followerElements = followers.stream()
        .map(f -> FollowElement.follower(f, mutualIds.contains(f.getUserId())))
        .toList();

    return new GetFollowersResponse(followElements, followerElements);
  }

  private void followInfo(long userId, List<Follow> follows, List<Follow> followers, Set<Long> mutualIds) {
    List<Follow> all = followRepository.findByUserIdOrFollowUserId(userId, userId);

    for (Follow follow : all) {
      if (Objects.equals(follow.getUserId(), userId) && follow.getFollowUser().getDeleteYn() == YesNo.N) {
        follows.add(follow);
      }
      if (Objects.equals(follow.getFollowUserId(), userId) && follow.getUser().getDeleteYn() == YesNo.N) {
        followers.add(follow);
      }
    }

    Set<Long> followIds = follows.stream().map(Follow::getFollowUserId).collect(Collectors.toSet());
    Set<Long> followerIds = followers.stream().map(Follow::getUserId).collect(Collectors.toSet());

    mutualIds.addAll(followIds);
    mutualIds.retainAll(followerIds);
  }

  public void unfollow(long userId, @NotNull long followUserId) {
    followRepository.deleteFollow(userId, followUserId);
  }

  public List<Follow> getMutual(Long userId, String name) {
    List<Follow> follows = new ArrayList<>();
    List<Follow> followers = new ArrayList<>();
    Set<Long> mutualIds = new HashSet<>();
    followInfo(userId, follows, followers, mutualIds);

    return follows.stream()
        .filter(item -> mutualIds.contains(item.getFollowUserId()))
        .filter(item -> name == null || item.getFollowUser().getName().contains(name))
        .toList();
  }
}
