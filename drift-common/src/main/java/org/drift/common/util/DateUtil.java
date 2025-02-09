package org.drift.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * @author jiakui_zeng
 * @date 2025/2/9 15:25
 */
public class DateUtil {
    public static String format(Instant instant) {
        // 获取当前时间
        Instant now = Instant.now();
        // 计算三天前的时间
        Instant threeDaysAgo = now.minus(3, ChronoUnit.DAYS);
        if (instant.isBefore(threeDaysAgo)) {
            // 如果早于三天前，则转为 yyyy-mm-dd 格式
            return LocalDate.ofInstant(instant, ZoneId.systemDefault()).toString();
        } else {
            // 计算具体是几天前
            long daysAgo = ChronoUnit.DAYS.between(instant, now);
            return daysAgo + "天前";
        }
    }
}
