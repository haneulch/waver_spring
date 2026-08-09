package com.mybury.waver.web.message.v1.bucket;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.BucketMember;
import java.util.List;
import java.util.Map;

public record BucketResponse(
    List<BucketElement> bucketlist,
    int totalCount,
    int completedCount,
    int progressCount
) {

    // memberByBucketId: 함께하기 버킷의 조회자 본인 참여자 row - 진행도/상태를 개인 기준으로 표시
    public static BucketResponse of(List<Bucket> bucketList, Map<Long, BucketMember> memberByBucketId) {
        List<BucketElement> bucketlist = bucketList.stream()
            .map(bucket -> new BucketElement(bucket, memberByBucketId.get(bucket.getId())))
            .toList();
        int totalCount = bucketlist.size();
        int completedCount = (int) bucketlist.stream().filter(bucket -> bucket.status() == BucketStatus.COMPLETE)
            .count();

        return new BucketResponse(bucketlist, totalCount, completedCount, totalCount - completedCount);
    }
}
