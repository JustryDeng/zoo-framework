package com.ideaaedi.zoo.commonbase.message;

import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;
import lombok.Getter;

/**
 * 重试
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface Retry<T> {
    
    /**
     * 重试策略
     *
     * @return 重试策略
     */
    default Strategy strategy() {
        return Strategy.NON;
    }
    
    /**
     * 重试参数值
     *
     * @return 重试参数值
     */
    T param();
    
    /**
     * 重试策略
     *
     * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
     * @since 1.0.0
     */
    @Getter
    enum Strategy implements EnumDescriptor {
        
        NON("不重试"),
        MAX_TIMES("重试最大次数限制"),
        LATEST_TIME("重试最晚时间限制"),
        SPECIFIED_INTERVAL("指定重试间隔"),
        SPECIFIED_TIME("指定重试时间点"),
        ;
        
        /** 描述 */
        private final String desc;
    
        Strategy(String desc) {
            this.desc = desc;
        }
    
        @Override
        public String obtainDescription() {
            return this.desc;
        }
    }
}
