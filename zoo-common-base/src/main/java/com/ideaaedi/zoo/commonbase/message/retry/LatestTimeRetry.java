package com.ideaaedi.zoo.commonbase.message.retry;

import com.ideaaedi.zoo.commonbase.message.Retry;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 重试最晚时间限制
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class LatestTimeRetry implements Retry<LocalDateTime> {
    
    private final LocalDateTime latestRetryTime;
    
    public LatestTimeRetry(LocalDateTime latestRetryTime) {
        this.latestRetryTime = latestRetryTime;
    }
    
    @Override
    public Strategy strategy() {
        return Strategy.LATEST_TIME;
    }
    
    @Override
    public LocalDateTime param() {
        return this.latestRetryTime;
    }
}
