package com.mybury.waver.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtils {

    public static String now() {
        return now("yyyyMMddHHmmssSSS");
    }

    public static String now(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }
}