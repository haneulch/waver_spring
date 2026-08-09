package com.mybury.waver.web.message.v1.alarm;

import com.mybury.waver.common.code.PushType;
import com.mybury.waver.domain.Alarm;
import com.mybury.waver.util.FileImageUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record AlarmResponse(
    @Schema(description = "알림 목록")
    List<AlarmElement> alarms
) {

    public static AlarmResponse of(List<Alarm> alarmList) {
        List<AlarmElement> elements = alarmList.stream()
            .map(alarm -> new AlarmElement(alarm.getType(), alarm.getMessage(),
                FileImageUtils.imagePath(alarm.getImgUrl()),
                alarm.getBucketId()))
            .toList();
        return new AlarmResponse(elements);
    }

    record AlarmElement(
        @Schema(description = "알림 타입", example = "FOLLOW")
        PushType type,
        @Schema(description = "알림 메시지", example = "민지님이 회원님을 팔로우하기 시작했습니다.")
        String message,
        @Schema(description = "FOLLOW 타입일 때 팔로워 프로필 이미지 URL. 그 외 타입은 null",
            example = "https://cdn.test.com/profile/minji.png")
        String imgUrl,
        @Schema(description = "버킷 관련 알림(LIKE/COMMENT/TOGETHER/D_DAY)일 때 대상 버킷 ID. 그 외 타입은 null",
            example = "1024")
        Long bucketId
    ) {

    }
}
