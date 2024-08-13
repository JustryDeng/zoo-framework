package com.ideaaedi.zoo.diy.feature.msg.api.mail.core;


import com.ideaaedi.zoo.diy.feature.msg.api.exception.MailSenderAlreadyExistException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * email sender管理器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface MailSenderManager {
    
    /**
     * 获取指定邮件发送器
     *
     * @param senderId 邮件发送器id
     *
     * @return 邮件发送器
     */
    @Nullable
    MailSender find(@Nullable String senderId);
    
    /**
     * 按顺序获取第一个存在的邮件发送器
     *
     * @param senderIds 邮件发送器ids
     *
     * @return 第一个存在的邮件发送器
     */
    @Nullable
    MailSender findFirst(@Nullable String... senderIds);
    
    /**
     * 获取所有邮件发送器
     *
     * @return key-邮件发送器id, value-邮件发送器
     */
    @Nonnull
    Map<String, MailSender> findAll();
    
    /**
     * 判断指定邮件发送器是否存在
     *
     * @param senderId 邮件发送器id
     *
     * @return 邮件发送器是否存在
     */
    boolean exist(@Nullable String senderId);

    /**
     * 添加邮件发送器
     * <pre>
     * 注：这里添加只会往当前节点中添加；在分布式环境下，需要外部确保往所有节点都添加了
     * 注：建议项目启动时调用
     * </pre>
     *
     * @param smsSender 邮件发送器
     * @throws MailSenderAlreadyExistException 邮件发送器已存在时抛出
     */
    void add(@Nullable MailSender smsSender) throws MailSenderAlreadyExistException;
    
    /**
     * 添加邮件发送器
     * <pre>
     * 注：这里添加只会往当前节点中添加；在分布式环境下，需要外部确保往所有节点都添加了
     * 注：建议项目启动时调用
     * </pre>
     *
     * @param smsSenders 邮件发送器
     * @throws MailSenderAlreadyExistException 邮件发送器已存在时抛出
     */
    void add(@Nullable MailSender... smsSenders) throws MailSenderAlreadyExistException;
    
    /**
     * 邮件发送器移除
     * <pre>
     * 注：这里移除只会从当前节点中移除；在分布式环境下，需要外部确保所有节点都移除了
     * </pre>
     *
     * @param senderId 邮件发送器id
     */
    void remove(@Nullable String senderId);

    /**
     * 邮件发送器移除
     * <pre>
     * 注：这里移除只会从当前节点中移除；在分布式环境下，需要外部确保所有节点都移除了
     * </pre>
     *
     * @param senderIds 邮件发送器ids
     */
    void remove(@Nullable String... senderIds);
}
