package com.mybury.waver.service;

import com.mybury.waver.domain.Alarm;
import com.mybury.waver.repository.AlarmRepository;
import com.mybury.waver.web.message.v1.alarm.AlarmResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public void create(Alarm alarm) {
        if (alarm == null || alarm.getUserId() == null) {
            log.warn("Alarm object is null or empty");
            return;
        }
        alarmRepository.save(alarm);
    }

    public AlarmResponse getList(long userId) {
        List<Alarm> alarms = alarmRepository.findByUserId(userId);
        return AlarmResponse.of(alarms);
    }
}
