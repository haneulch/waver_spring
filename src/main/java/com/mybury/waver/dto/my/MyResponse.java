package com.mybury.waver.dto.my;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.common.code.ContentType;
import com.mybury.waver.common.code.ExposureStatus;
import java.util.List;

record Bucket(
    int id,
    ContentType bucketType,
    String title,
    ExposureStatus exposureStatus,
    BucketStatus status,
    int dDay,
    int userCount,
    int goalCount
) {

}

record BucketInfo(
    List<Bucket> bucketlist,
    int totalCount,
    int completedCount,
    int progressCount
) {

}

public record MyResponse(
    String imgUrl,
    String name,
    String bio,
    String badgeTitle,
    int followingCount,
    int followerCount,
    BucketInfo bucketInfo
) {

}
