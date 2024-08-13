package com.ideaaedi.zoo.diy.feature.msg.api.sms.core;

import com.ideaaedi.zoo.diy.feature.msg.api.exception.SmsSenderAlreadyExistException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * sms sender管理器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface SmsSenderManager {
    
    /**
     * 获取指定短信发送器
     *
     * @param senderId 短信发送器id
     *
     * @return 短信发送器
     */
    @Nullable
    SmsSender find(@Nullable String senderId);
    
    /**
     * 按顺序获取第一个存在的短信发送器
     *
     * @param senderIds 短信发送器ids
     *
     * @return 第一个存在的短信发送器
     */
    @Nullable
    SmsSender findFirst(@Nullable String... senderIds);
    
    /**
     * 获取所有短信发送器
     *
     * @return key-短信发送器id, value-短信发送器
     */
    @Nonnull
    Map<String, SmsSender> findAll();
    
    /**
     * 判断指定短信发送器是否存在
     *
     * @param senderId 短信发送器id
     *
     * @return 短信发送器是否存在
     */
    boolean exist(@Nullable String senderId);

    /**
     * 添加短信发送器
     * <pre>
     * 注：这里添加只会往当前节点中添加；在分布式环境下，需要外部确保往所有节点都添加了
     * 注：建议项目启动时调用
     * </pre>
     *
     * @param smsSender 短信发送器
     * @throws SmsSenderAlreadyExistException 短信发送器已存在时抛出
     */
    void add(@Nullable SmsSender smsSender) throws SmsSenderAlreadyExistException;
    
    /**
     * 添加短信发送器
     * <pre>
     * 注：这里添加只会往当前节点中添加；在分布式环境下，需要外部确保往所有节点都添加了
     * 注：建议项目启动时调用
     * </pre>
     *
     * @param smsSenders 短信发送器
     * @throws SmsSenderAlreadyExistException 短信发送器已存在时抛出
     */
    void add(@Nullable SmsSender... smsSenders) throws SmsSenderAlreadyExistException;
    
    /**
     * 短信发送器移除
     * <pre>
     * 注：这里移除只会从当前节点中移除；在分布式环境下，需要外部确保所有节点都移除了
     * </pre>
     *
     * @param senderId 短信发送器id
     */
    void remove(@Nullable String senderId);

    /**
     * 短信发送器移除
     * <pre>
     * 注：这里移除只会从当前节点中移除；在分布式环境下，需要外部确保所有节点都移除了
     * </pre>
     *
     * @param senderIds 短信发送器ids
     */
    void remove(@Nullable String... senderIds);
}
