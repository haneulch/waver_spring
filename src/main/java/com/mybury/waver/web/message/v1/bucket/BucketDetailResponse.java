package com.mybury.waver.web.message.v1.bucket;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.common.code.ExposureStatus;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.BucketMember;
import com.mybury.waver.domain.User;
import com.mybury.waver.util.FileImageUtils;
import com.mybury.waver.web.message.v1.category.CategoryElement;
import com.mybury.waver.web.message.v1.comment.CommentElement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

// 함께하는 참여자(본인 제외)의 진행 상태. completedDt는 달성 완료(COMPLETE)일 때만 내려간다
// 참여자 row가 없는 레거시 데이터는 버킷 공유값으로 fallback
record FriendStatusElement(
        long id,
        String name,
        String imgUrl,
        int userCount,
        BucketStatus status,
        LocalDateTime completedDt) {

    public static FriendStatusElement of(User friend, Bucket bucket, BucketMember member) {
        int userCount = member != null ? member.getUserCount() : bucket.getUserCount();
        BucketStatus status = member != null ? member.getStatus() : bucket.getStatus();
        LocalDateTime completedDate = member != null ? member.getCompletedDate() : bucket.getCompletedDate();
        return new FriendStatusElement(
                friend.getId(),
                friend.getName(),
                FileImageUtils.imagePath(friend.getImgUrl()),
                userCount,
                status,
                status == BucketStatus.COMPLETE ? completedDate : null);
    }
}

public record BucketDetailResponse(
        long id,
        long userId,
        String title,
        String memo,
        ExposureStatus exposureStatus,
        BucketStatus status,
        YesNo pin,
        YesNo complete,
        YesNo scrapYn,
        YesNo isMine,
        YesNo isLike,
        CategoryElement category,
        int goalCount,
        int userCount,
        LocalDateTime completedDt,
        LocalDate targetDate,
        List<KeywordElement> keywords,
        List<FriendStatusElement> friendStatusList,
        List<String> images,
        List<CommentElement> comment) {

    public static BucketDetailResponse of(Bucket bucket, long userId,
            List<KeywordElement> keywordList, List<User> friendUserList, boolean isLike,
            List<Long> reportedCommentIds, Map<Long, BucketMember> memberByUserId) {
        CategoryElement category = new CategoryElement(bucket.getCategory());
        List<FriendStatusElement> friendStatusList = friendUserList.stream()
                .map(friend -> FriendStatusElement.of(friend, bucket, memberByUserId.get(friend.getId())))
                .toList();
        List<String> images = bucket.getImgUrl() != null
                ? Arrays.stream(bucket.getImgUrl().split(",")).map(FileImageUtils::imagePath).toList()
                : List.of();
        // 미노출 조건: 차단(신고 누적) / 작성자 숨김 / 조회 사용자가 신고한 댓글
        List<CommentElement> comment = bucket.getComments().stream()
                .filter(item -> item.getIsBlocked() != YesNo.Y)
                .filter(item -> item.getIsHide() != YesNo.Y)
                .filter(item -> reportedCommentIds == null || !reportedCommentIds.contains(item.getId()))
                .map(item -> new CommentElement(item, userId)).toList().reversed();

        // 함께하기면 상단 진행도/상태는 조회자 본인의 참여자 row 기준
        BucketMember viewer = memberByUserId.get(userId);
        BucketStatus status = viewer != null ? viewer.getStatus() : bucket.getStatus();
        int userCount = viewer != null ? viewer.getUserCount() : bucket.getUserCount();
        LocalDateTime completedDate = viewer != null ? viewer.getCompletedDate() : bucket.getCompletedDate();

        return new BucketDetailResponse(
                bucket.getId(),
                bucket.getUserId(),
                bucket.getTitle(),
                bucket.getMemo(),
                bucket.getExposureStatus(),
                status,
                bucket.getPin(),
                completedDate != null ? YesNo.Y : YesNo.N,
                bucket.getScrapYn(),
                bucket.getUserId().equals(userId) ? YesNo.Y : YesNo.N,
                isLike ? YesNo.Y : YesNo.N,
                category,
                bucket.getGoalCount(),
                userCount,
                completedDate,
                bucket.getTargetDate(),
                keywordList,
                friendStatusList,
                images,
                comment);
    }
}
