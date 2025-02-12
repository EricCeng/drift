package org.drift.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author jiakui_zeng
 * @date 2025/2/9 15:25
 */
public class DateUtil {
    public static String format(Instant instant) {
        // 获取当前时间
        Instant now = Instant.now();
        LocalDate nowDate = LocalDate.now(ZoneId.systemDefault());
        LocalDate instantDate = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        LocalDateTime instantDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        // 计算当前年份
        int currentYear = nowDate.getYear();
        int instantYear = instantDate.getYear();

        if (instantYear < currentYear) {
            // 如果早于当前年，则转为 yyyy-mm-dd 格式
            return instantDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            // 计算三天前的时间
            Instant threeDaysAgo = now.minus(3, ChronoUnit.DAYS);
            if (instant.isBefore(threeDaysAgo)) {
                // 如果是当前年且早于三天前，则转为 mm-dd 格式
                return instantDate.format(DateTimeFormatter.ofPattern("MM-dd"));
            } else {
                if (instantDate.isEqual(nowDate.minusDays(1))) {
                    // 如果是昨天，则转为 昨天 几点 格式
                    return instantDateTime.format(DateTimeFormatter.ofPattern("昨天 HH:mm"));
                } else {
                    // 计算具体时间差
                    Duration duration = Duration.between(instant, now);
                    long seconds = duration.getSeconds();
                    long absSeconds = Math.abs(seconds);

                    if (absSeconds < 60) {
                        return "刚刚";
                    } else if (absSeconds < 3600) {
                        return absSeconds / 60 + "分钟前";
                    } else if (absSeconds < 86400) {
                        return absSeconds / 3600 + "小时前";
                    } else {
                        return ChronoUnit.DAYS.between(instant, now) + "天前";
                    }
                }
            }
        }
    }
}
