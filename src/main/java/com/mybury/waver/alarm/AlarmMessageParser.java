package com.mybury.waver.alarm;

import com.mybury.waver.alarm.code.AlarmMessageType;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmMessageParser {

    private final MessageSource messageSource;

    public String parse(AlarmMessageType type, Locale locale, Object... args) {
        return messageSource.getMessage(type.getCode(), args, locale);
    }
}
