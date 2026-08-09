package com.mybury.waver.web.message.v1.bucket;

import java.util.Arrays;
import java.util.List;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.common.code.ContentType;
import com.mybury.waver.common.code.ExposureStatus;
import com.mybury.waver.common.code.FixedKeyword;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.BucketMember;

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
        this(bucket, null);
    }

    // member가 있으면(함께하기) 진행도/상태를 조회자 본인의 참여자 row 기준으로 표시
    public BucketElement(Bucket bucket, BucketMember member) {

        this(
            bucket.getId(),
            bucket.getType(),
            bucket.getTitle(),
            bucket.getImgUrl(),
            bucket.getExposureStatus(),
            member != null ? member.getStatus() : bucket.getStatus(),
            bucket.getDDay(),
            member != null ? member.getUserCount() : bucket.getUserCount(),
            bucket.getGoalCount(),
            bucket.getLikeCount(),
            bucket.getKeywords() == null || bucket.getKeywords().isBlank()
                ? List.of()
                : Arrays.stream(bucket.getKeywords().split(","))
                        .map(item -> new KeywordElement(FixedKeyword.get(item)))
                        .toList());
    }
}
