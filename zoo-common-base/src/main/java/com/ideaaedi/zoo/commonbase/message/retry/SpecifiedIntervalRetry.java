package com.ideaaedi.zoo.commonbase.message.retry;

import com.ideaaedi.zoo.commonbase.message.Retry;
import lombok.Data;

import java.util.List;

/**
 * 指定重试间隔
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class SpecifiedIntervalRetry implements Retry<List<Integer>> {
    
    /** 重试间隔集合（单位秒） */
    private final List<Integer> intervalSecondList;
    
    public SpecifiedIntervalRetry(List<Integer> intervalSecondList) {
        this.intervalSecondList = intervalSecondList;
    }
    
    @Override
    public Strategy strategy() {
        return Strategy.SPECIFIED_INTERVAL;
    }
    
    @Override
    public List<Integer> param() {
        return this.intervalSecondList;
    }
}
