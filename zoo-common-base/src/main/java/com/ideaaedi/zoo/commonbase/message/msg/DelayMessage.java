package com.ideaaedi.zoo.commonbase.message.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 延迟消息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DelayMessage extends AbstractMessage {
    
    /**
     * 延迟执行的时间点
     */
    private LocalDateTime delayTo;
    
    /**
     * fast create
     */
    public static DelayMessage create(LocalDateTime delayTo) {
        DelayMessage delayMessage = new DelayMessage();
        delayMessage.setDelayTo(delayTo);
        return delayMessage;
    }
    
    /**
     * fast create
     *
     * @param duration 延迟时长
     * @param chronUnit 延迟时长单位
     *
     * @return DelayMessage对象
     */
    public static DelayMessage create(long duration, ChronoUnit chronUnit) {
        DelayMessage delayMessage = new DelayMessage();
        delayMessage.setDelayTo(LocalDateTime.now().plus(duration, chronUnit));
        return delayMessage;
    }
}
