package com.ideaaedi.zoo.diy.artifact.websocket.distribute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分布式websocket消息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributedWebsocketMsgDTO implements Serializable {
    
    /**
     * 消息发送方
     */
    private String sender;
    
    /**
     * 标识session的唯一key
     */
    private String sessionUuid;
    
    /**
     * 待发送的消息内容
     */
    private String msg;
}
