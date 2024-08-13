package com.ideaaedi.zoo.commonbase.entity.message.dto;

import com.ideaaedi.zoo.commonbase.message.Channel;
import com.ideaaedi.zoo.commonbase.message.FromTo;
import com.ideaaedi.zoo.commonbase.message.Message;
import com.ideaaedi.zoo.commonbase.message.Retry;
import lombok.Data;

/**
 * 消息申请对象
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class MsgApplyDTO {
    
    /** 消息 */
    private Message message;
    
    /** 消息交互方 */
    private FromTo<?, ?> fromTo;
    
    /** 重试策略 */
    private Retry<?> retry;
    
    /** 消费消息的渠道 */
    private Channel.Sink channelSink;
}
