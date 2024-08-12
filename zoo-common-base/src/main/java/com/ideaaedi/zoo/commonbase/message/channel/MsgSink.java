package com.ideaaedi.zoo.commonbase.message.channel;

import com.ideaaedi.zoo.commonbase.message.Channel;
import lombok.Data;

/**
 * 消息消费渠道
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class MsgSink implements Channel.Sink {
    
    /** 消息渠道id */
    private Long id;
    
    /** 渠道名称 */
    private String channelName;
    
    /** 渠道编码 */
    private String channelCode;
    
    @Override
    public String channelName() {
        return this.channelName;
    }
    
    @Override
    public String channelCode() {
        return this.channelCode;
    }
    
    @Override
    public Long sinkId() {
        return id;
    }
}
