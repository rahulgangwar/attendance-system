package com.example.util;

import java.time.LocalDateTime;

public class DateUtil {

    private DateUtil() {}

    public static boolean isToday(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().isEqual(LocalDateTime.now().toLocalDate());
    }
}
