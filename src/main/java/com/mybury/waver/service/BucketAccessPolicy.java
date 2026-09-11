package com.mybury.waver.service;

import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.FollowRepository;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 버킷 열람 권한 정책.
 * 노출 설정(exposureStatus)은 버킷 생성/수정 API 밖에서는 신뢰할 수 있는 유일한 기준이므로,
 * 버킷 내용을 내려주거나 버킷에 쓰는 모든 경로가 여기를 거쳐야 한다.
 */
@Component
@RequiredArgsConstructor
public class BucketAccessPolicy {

  private final FollowRepository followRepository;

  /**
   * 소유자와 함께하기 참여자는 노출 설정과 무관하게 열람할 수 있고,
   * 그 외에는 PUBLIC은 전체, FOLLOWER는 맞팔로우, PRIVATE는 불가다.
   * (FOLLOWER 기준은 피드 조회(findFeed)와 동일하게 맞팔로우로 판단한다)
   */
  public boolean canView(Bucket bucket, long userId) {
    if (isOwner(bucket, userId) || isParticipant(bucket, userId)) {
      return true;
    }
    return switch (bucket.getExposureStatus()) {
      case PUBLIC -> true;
      case FOLLOWER -> isMutualFollow(userId, bucket.getUserId());
      case PRIVATE -> false;
    };
  }

  public void checkViewable(Bucket bucket, long userId) {
    if (!canView(bucket, userId)) {
      throw new WaverException(ResultCode.FORBIDDEN);
    }
  }

  public boolean isOwner(Bucket bucket, long userId) {
    return bucket.getUserId() != null && bucket.getUserId() == userId;
  }

  /** 함께하기 참여자(소유자 제외). friendUserIds가 참여자의 단일 기준이다. */
  public boolean isParticipant(Bucket bucket, long userId) {
    if (!bucket.isTogether()) {
      return false;
    }
    String target = String.valueOf(userId);
    return Arrays.stream(bucket.getFriendUserIds().split(","))
        .map(String::trim)
        .anyMatch(target::equals);
  }

  private boolean isMutualFollow(long userId, Long ownerId) {
    if (ownerId == null) {
      return false;
    }
    return followRepository.existsByUserIdAndFollowUserId(userId, ownerId)
        && followRepository.existsByUserIdAndFollowUserId(ownerId, userId);
  }
}
