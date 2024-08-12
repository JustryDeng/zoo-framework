package com.ideaaedi.zoo.commonbase.message;

/**
 * 消息渠道
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface Channel {
    
    /**
     * 渠道名称
     *
     * @return 渠道名称
     */
    String channelName();
    
    /**
     * 渠道编码
     *
     * @return 渠道编码
     */
    String channelCode();
    
    /**
     * 消息生产渠道
     *
     * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
     * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
     * @since 1.0.0
     */
    interface Source extends Channel {
        
        /**
         * 入方向消息渠道数据id
         *
         * @return 入方向消息渠道数据id
         */
        Long sourceId();
    }
    
    /**
     * 消息消费渠道
     *
     * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
     * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
     * @since 1.0.0
     */
    interface Sink extends Channel {
        
        /**
         * 出方向消息渠道数据id
         *
         * @return 出方向消息渠道数据id
         */
        Long sinkId();
    }
}
