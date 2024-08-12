package com.ideaaedi.zoo.commonbase.zoo_util;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public final class DateTimeUtil {
    
    /**
     * {@link Date}转{@link LocalDateTime}
     *
     * @param localDateTime localDateTime对象
     *
     * @return Date对象
     */
    @Nullable
    public static Date localDateTime2Date(@Nullable LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
