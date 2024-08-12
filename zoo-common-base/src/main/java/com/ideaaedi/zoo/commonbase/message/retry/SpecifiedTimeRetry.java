package com.ideaaedi.zoo.commonbase.message.retry;

import com.ideaaedi.zoo.commonbase.message.Retry;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 指定重试时间点
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class SpecifiedTimeRetry implements Retry<List<LocalDateTime>> {
    
    /** 重试时间点集合 */
    private final List<LocalDateTime> timeList;
    
    public SpecifiedTimeRetry(List<LocalDateTime> timeList) {
        this.timeList = timeList;
    }
    
    @Override
    public Strategy strategy() {
        return Strategy.SPECIFIED_TIME;
    }
    
    @Override
    public List<LocalDateTime> param() {
        return this.timeList;
    }
}
