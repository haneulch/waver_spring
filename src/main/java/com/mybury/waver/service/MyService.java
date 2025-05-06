package com.mybury.waver.service;

import com.mybury.waver.common.code.BadgeStep;
import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.User;
import com.mybury.waver.domain.vo.FollowCount;
import com.mybury.waver.dto.my.MyResponse;
import com.mybury.waver.dto.my.MyWaveInfoResponse;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.BadgeRepository;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.FollowRepository;
import com.mybury.waver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyService {

  private final BucketRepository bucketRepository;
  private final FollowRepository followRepository;
  private final BadgeRepository badgeRepository;
  private final UserRepository userRepository;

  public MyResponse my(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new WaverException(ResultCode.BAD_REQUEST));
    Badge selected = badgeRepository.findByUserIdAndSelectYn(userId, YesNo.Y).orElseThrow(() -> new WaverException(ResultCode.BAD_REQUEST));

    List<FollowCount> followCounts = followRepository.findCountByUserIdOrFollowUserId(userId, userId);
    int followingCount = (int) followCounts.stream().filter(follow -> follow.getUserId() == userId).count();
    int followerCount = followCounts.size() - followingCount;

    return new MyResponse(user, selected, followingCount, followerCount);
  }

  public MyWaveInfoResponse waveInfo(long userId) {
    int totalBadgeCount = badgeRepository.countByUserId(userId);
    int totalLikeCount = 0;
    int totalBucketCount = bucketRepository.countByUserIdAndDeleted(userId, YesNo.N);

    Badge badge = badgeRepository.findByUserIdAndSelectYn(userId, YesNo.Y).orElse(null);
    String badgeImgUrl = badge == null ? null : BadgeStep.getImgUrl(BadgeStep.getStep(badge.getAchieveCount()), badge.getBadgeType());

    return new MyWaveInfoResponse(totalBadgeCount, totalLikeCount, totalBucketCount, badgeImgUrl);
  }
}
