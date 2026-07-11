package com.mybury.waver.web;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.service.AlarmService;
import com.mybury.waver.service.MyService;
import com.mybury.waver.web.message.v1.alarm.AlarmResponse;
import com.mybury.waver.web.message.v1.my.MyResponse;
import com.mybury.waver.web.message.v1.my.MyWaveInfoResponse;
import com.mybury.waver.web.message.v1.my.OtherMyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "마이페이지", description = "내 정보 요약, 타인 페이지, 알림 목록")
@RestController
@RequestMapping("waver/my")
@RequiredArgsConstructor
public class MyController {

    private final MyService myService;
    private final AlarmService alarmService;

    @Operation(summary = "마이페이지 메인",
        description = "내 프로필, 대표 배지, 팔로잉/팔로워 수를 반환합니다.")
    @GetMapping
    public MyResponse my(@Parameter(hidden = true) @UserId Long userId) {
        return myService.my(userId);
    }

    @Operation(summary = "타인 마이페이지",
        description = "다른 사용자의 프로필과 팔로잉/팔로워 수, 내가 팔로우 중인지 여부를 반환합니다.")
    @GetMapping("{otherUserId}")
    public OtherMyResponse otherMy(@PathVariable Long otherUserId, @Parameter(hidden = true) @UserId Long userId) {
        return myService.getOther(otherUserId, userId);
    }

    @Operation(summary = "알림 목록", description = "받은 푸시 알림 내역을 반환합니다.")
    @GetMapping("push")
    public AlarmResponse push(@Parameter(hidden = true) @UserId Long userId) {
        return alarmService.getList(userId);
    }

    @Operation(summary = "내 웨이브 요약",
        description = "획득 배지 수, 총 버킷 수, 대표 배지 이미지 URL을 반환합니다.")
    @GetMapping("info")
    public MyWaveInfoResponse info(@Parameter(hidden = true) @UserId Long userId) {
        return myService.waveInfo(userId);
    }
}
