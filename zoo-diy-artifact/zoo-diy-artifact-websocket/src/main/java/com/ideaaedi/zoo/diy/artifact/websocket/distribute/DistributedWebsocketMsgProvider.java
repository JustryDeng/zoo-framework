package com.ideaaedi.zoo.diy.artifact.websocket.distribute;

/**
 * 分布式websocket消息提供者
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface DistributedWebsocketMsgProvider {
    
    /**
     * 用于广播待发送websocket消息的redis主题
     */
    String REDIS_TOPIC_WEBSOCKET_MSG_TO_SEND_NOTIFY = "REDIS_TOPIC_WEBSOCKET_MSG_TO_SEND_NOTIFY";
    
    /**
     * 提供消息
     *
     * @param distributedWebsocketMsg 消息
     */
    void provide(DistributedWebsocketMsgDTO distributedWebsocketMsg);
}
