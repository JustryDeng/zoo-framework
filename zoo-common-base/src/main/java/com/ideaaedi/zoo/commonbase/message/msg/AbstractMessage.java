package com.ideaaedi.zoo.commonbase.message.msg;

import com.ideaaedi.zoo.commonbase.message.Message;
import lombok.Data;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;

/**
 * 消息基类
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public abstract class AbstractMessage implements Message {
    
    /** 消息内容 */
    protected String content;
    
    /** 业务端产生消息的时间 */
    protected LocalDateTime generateTime = LocalDateTime.now();
    
    /** 消息状态 */
    protected State state = State.TO_EXEC;
    
    /** 消费者配置 */
    protected ConsumeConfig consumeConfig = ConsumeConfig.builder().async(true).build();
    
    @Nonnull
    @Override
    public String content() {
        return this.content;
    }
    
    @Nonnull
    @Override
    public LocalDateTime generateTime() {
        return this.generateTime;
    }
    
    @Nonnull
    @Override
    public State state() {
        return this.state;
    }
    
    @Nonnull
    @Override
    public ConsumeConfig consumeConfig() {
        return this.consumeConfig;
    };
}
