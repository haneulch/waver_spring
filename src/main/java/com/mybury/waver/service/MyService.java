package com.mybury.waver.service;

import com.mybury.waver.common.code.BadgeStep;
import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.common.code.SortType;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.User;
import com.mybury.waver.domain.vo.FollowCount;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.BadgeRepository;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.FollowRepository;
import com.mybury.waver.repository.UserRepository;
import com.mybury.waver.web.message.v1.my.MyResponse;
import com.mybury.waver.web.message.v1.my.MyWaveInfoResponse;
import com.mybury.waver.web.message.v1.my.OtherMyResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyService {

  private final BucketRepository bucketRepository;
  private final FollowRepository followRepository;
  private final BadgeRepository badgeRepository;
  private final UserRepository userRepository;
  private final BucketAccessPolicy bucketAccessPolicy;

  private Badge getUserWithBadgeById(Long userId) {
    return badgeRepository.findByUserIdAndSelectYn(userId, YesNo.Y)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public MyResponse my(Long userId, SortType sort) {
    Badge selected = getUserWithBadgeById(userId);
    User user = selected.getUser();

    List<FollowCount> followCounts = followRepository.findCountByUserIdOrFollowUserId(userId, userId);
    int followingCount = (int) followCounts.stream().filter(follow -> follow.getUserId() == userId).count();
    int followerCount = followCounts.size() - followingCount;

    List<Bucket> buckets = bucketRepository.findByUserIdAndDeleted(userId, YesNo.N, bucketSort(sort));
    return MyResponse.of(user, selected, followingCount, followerCount, buckets);
  }

  private Sort bucketSort(SortType sort) {
    return switch (sort == null ? SortType.CREATED_DESC : sort) {
      case CREATED -> Sort.by(Sort.Direction.ASC, "createdAt", "id");
      case CREATED_DESC -> Sort.by(Sort.Direction.DESC, "createdAt", "id");
      case UPDATED -> Sort.by(Sort.Direction.ASC, "updatedAt", "id");
      case UPDATED_DESC -> Sort.by(Sort.Direction.DESC, "updatedAt", "id");
      case LIKE_COUNT_DESC -> Sort.by(Sort.Direction.DESC, "likeCount", "id");
    };
  }

  public MyWaveInfoResponse waveInfo(long userId) {
    int totalBadgeCount = badgeRepository.countByUserId(userId);
    int totalLikeCount = 0;
    int totalBucketCount = bucketRepository.countByUserIdAndDeleted(userId, YesNo.N);

    String badgeImgUrl = badgeRepository.findByUserIdAndSelectYn(userId, YesNo.Y)
        .map(badge -> BadgeStep.getImgUrl(BadgeStep.getStep(badge.getAchieveCount()), badge.getBadgeType()))
        .orElse(null);

    return new MyWaveInfoResponse(totalBadgeCount, totalLikeCount, totalBucketCount, badgeImgUrl);
  }

  @Transactional(readOnly = true)
  public OtherMyResponse getOther(long otherUserId, long userId) {
    Badge selected = getUserWithBadgeById(otherUserId);
    User user = selected.getUser();

    // 팔로잉 수, 팔로워 수
    List<FollowCount> followCounts = followRepository.findCountByUserIdOrFollowUserId(otherUserId, otherUserId);
    int followingCount = (int) followCounts.stream().filter(follow -> follow.getUserId() == otherUserId).count();
    int followerCount = followCounts.size() - followingCount;

    // 내가 팔로우 하고 있는지 여부
    YesNo isFollowing =
        followCounts.stream().anyMatch(follow -> follow.getUserId() == userId && follow.getFollowUserId() == otherUserId) ? YesNo.Y : YesNo.N;

    List<Bucket> visibleBuckets = user.getBucketlist().stream()
        .filter(bucket -> bucket.getDeleted() == YesNo.N)
        .filter(bucket -> bucketAccessPolicy.canView(bucket, userId))
        .toList();
    return OtherMyResponse.of(user, selected, followingCount, followerCount, isFollowing, visibleBuckets);
  }
}
