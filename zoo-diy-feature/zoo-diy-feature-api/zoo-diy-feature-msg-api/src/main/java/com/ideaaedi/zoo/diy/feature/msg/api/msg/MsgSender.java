package com.ideaaedi.zoo.diy.feature.msg.api.msg;

import java.util.List;

/**
 * 消息发送者
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface MsgSender {
    
    /**
     * 唯一id
     */
    String id();
    
    /**
     * 供应商
     */
    String supplier();
    
    /**
     * 标签
     */
    List<String> tags();
}
