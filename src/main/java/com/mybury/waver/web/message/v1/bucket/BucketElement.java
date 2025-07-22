package com.mybury.waver.web.message.v1.bucket;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.common.code.ContentType;
import com.mybury.waver.common.code.ExposureStatus;
import com.mybury.waver.domain.Bucket;

public record BucketElement(
    long id,
    ContentType bucketType,
    String title,
    ExposureStatus exposureStatus,
    BucketStatus status,
    Integer dDay,
    int userCount,
    int goalCount
) {

    public BucketElement(Bucket bucket) {
        this(bucket.getId(), bucket.getType(), bucket.getTitle(), bucket.getExposureStatus(), bucket.getStatus(),
            bucket.getDDay(), bucket.getUserCount(), bucket.getGoalCount());
    }
}
