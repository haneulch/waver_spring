package com.mybury.waver.web.message.v1.alarm;

import com.mybury.waver.domain.Alarm;
import java.util.List;

public record AlarmResponse(
    List<AlarmElement> alarms
) {

    public static AlarmResponse of(List<Alarm> alarmList) {
        List<AlarmElement> elements = alarmList.stream()
            .map(alarm -> new AlarmElement(alarm.getMessage()))
            .toList();
        return new AlarmResponse(elements);
    }

    record AlarmElement(String message) {

    }
}
