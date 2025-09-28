package com.mybury.waver.web.message.v1.bucket;

import java.util.Arrays;
import java.util.List;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.common.code.ContentType;
import com.mybury.waver.common.code.ExposureStatus;
import com.mybury.waver.common.code.FixedKeyword;
import com.mybury.waver.domain.Bucket;

public record BucketElement(
    long id,
    ContentType bucketType,
    String title,
    String imgUrl,
    ExposureStatus exposureStatus,
    BucketStatus status,
    Integer dDay,
    int userCount,
    int goalCount,
    int likeCount,
    List<KeywordElement> keyword
) {

    public BucketElement(Bucket bucket) {

        this(
            bucket.getId(), 
            bucket.getType(), 
            bucket.getTitle(), 
            bucket.getImgUrl(),
            bucket.getExposureStatus(), 
            bucket.getStatus(),
            bucket.getDDay(), 
            bucket.getUserCount(), 
            bucket.getGoalCount(), 
            bucket.getLikeCount(), 
            bucket.getKeywords() == null || bucket.getKeywords().isBlank()
                ? List.of()
                : Arrays.stream(bucket.getKeywords().split(","))
                        .map(item -> new KeywordElement(FixedKeyword.get(item)))
                        .toList());
    }
}
