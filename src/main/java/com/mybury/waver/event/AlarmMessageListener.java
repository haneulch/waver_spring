package com.mybury.waver.event;

import com.mybury.waver.event.message.AlarmMessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 알람 메시지 리스너
 */
@Component
@RequiredArgsConstructor
public class AlarmMessageListener {

    @EventListener
    public void handle(AlarmMessageEvent event) {
        switch (event.type()) {
            case NOTICE -> System.out.println(event.message()); // 메세지 그대로 저장
            case FEED_COMMENT -> System.out.println(event.message()); // OOO님의 회원님 버킷리스트에 댓글을 달았습니다.
            case FEED_LIKE -> System.out.println(event.message()); // OOO님이 회원님의 버킷리스트를 좋아합니다.
            case D_DAY_7 -> System.out.println(event.message()); // 디데이가 7일 남은 버킷리스트가 있습니다.\n:버킷리스트제목
        }
    }

}
