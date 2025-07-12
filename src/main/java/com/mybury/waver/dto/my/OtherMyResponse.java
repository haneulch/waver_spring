package com.mybury.waver.dto.my;

import java.util.List;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.User;
import com.mybury.waver.dto.bucket.BucketElement;

import io.swagger.v3.oas.annotations.media.Schema;

record OtherBucketInfo(
    List<BucketElement> bucketlist,
    int totalCount,
    int completedCount,
    int progressCount
) {
  public static OtherBucketInfo of(List<Bucket> buckets) {
    List<BucketElement> bucketlist = buckets.stream().map(BucketElement::new).toList();
    int totalCount = bucketlist.size();
    int completedCount = (int) bucketlist.stream().filter(bucket -> bucket.status() == BucketStatus.COMPLETE).count();
    return new OtherBucketInfo(bucketlist, totalCount, completedCount, totalCount - completedCount);
  }
}


public record OtherMyResponse(
  long id,
  @Schema(description = "프로필 이미지")
  String imgUrl,
  @Schema(description = "이름")
  String name,
  @Schema(description = "소개")
  String bio,
  @Schema(description = "뱃지 제목")
  String badgeTitle,
  @Schema(description = "팔로잉 수")
  int followingCount,
  @Schema(description = "팔로워 수")
  int followerCount,
  @Schema(description = "내가 팔로우 하고 있는지 여부")
  YesNo isFollowing,
  @Schema(description = "버킷리스트")
  OtherBucketInfo bucketInfo
){
  public OtherMyResponse(User user, Badge selected, int followingCount, int followerCount, YesNo isFollowing) {
    this(user.getId(), user.getImgUrl(), user.getName(), user.getBio(), selected.getBadgeType().getTitle(), followingCount, followerCount, isFollowing, OtherBucketInfo.of(user.getBucketlist()));
  }
}