package com.ideaaedi.zoo.commonbase.message.retry;

import com.ideaaedi.zoo.commonbase.message.Retry;
import lombok.Data;

/**
 * 重试最大次数限制
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class MaxTimesRetry implements Retry<Integer> {
    
    private final int maxRetryTimes;
    
    public MaxTimesRetry(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }
    
    @Override
    public Strategy strategy() {
        return Strategy.MAX_TIMES;
    }
    
    @Override
    public Integer param() {
        return this.maxRetryTimes;
    }
}
