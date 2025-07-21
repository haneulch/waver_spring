package com.mybury.waver.service;

import com.mybury.waver.domain.Alarm;
import com.mybury.waver.repository.AlarmRepository;
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
}
