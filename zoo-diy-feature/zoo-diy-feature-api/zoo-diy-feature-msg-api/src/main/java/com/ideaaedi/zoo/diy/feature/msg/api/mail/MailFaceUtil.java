package com.ideaaedi.zoo.diy.feature.msg.api.mail;

import com.ideaaedi.commonds.env.Env;
import com.ideaaedi.commonds.env.RequiredEnv;
import com.ideaaedi.commonds.env.Unit;
import com.ideaaedi.zoo.commonbase.zoo_face.Face;
import com.ideaaedi.zoo.diy.feature.msg.api.exception.MailNoSuchSenderException;
import com.ideaaedi.zoo.diy.feature.msg.api.exception.MailNotPointDefaultSenderException;
import com.ideaaedi.zoo.diy.feature.msg.api.exception.MailSendWayUnsupportException;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.core.MailSender;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.core.MailSenderManager;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.entity.MailFile;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.entity.MailSendInfo;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.entity.MailSendResult;
import com.ideaaedi.zoo.diy.feature.msg.api.properties.ZooMsgProperties;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * 邮件门面工具类
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@UtilityClass
public final class MailFaceUtil implements Face {
    
    static MailSenderManager mailSenderManager;
    
    static ZooMsgProperties zooMsgProperties;
    
    /**
     * 获取默认的邮件发送器
     * <pre>
     * 你可以通过以下配置来指定默认的邮件发送器
     * {@code
     *  zoo:
     *    msg:
     *       mail:
     *         # 指定邮件的默认发送器
     *         public static-sender-id: xxx
     * }
     * </pre>
     *
     * @return 默认的邮件发送器
     */
    @Nullable
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static MailSender getDefaultSender() {
        return mailSenderManager.find(zooMsgProperties.getMail().getDefaultSenderId());
    }
    
    /**
     * 获取指定邮件发送器的默认发件人
     *
     * @return 邮件发送器的默认发件人
     */
    @Nullable
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static String getSenderManager(@Nonnull String senderId) {
        MailSender mailSender = mailSenderManager.find(senderId);
        if (mailSender == null) {
            throw new MailNoSuchSenderException("not found mail-sender whose id is '" + senderId + "'.");
        }
        return mailSender.defaultFrom();
    }
    
    /**
     * 获取邮件发送器管理器
     *
     * @return 邮件发送器管理器
     */
    @Nonnull
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static MailSenderManager getSenderManager() {
        return mailSenderManager;
    }
    
    /**
     * 普通邮件发送
     *
     * @param to 发送对象
     * @param subject 主题
     * @param content 内容
     * @param attachments 附件
     */
    @Nonnull
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static MailSendResult sendText(@Nonnull Collection<String> to,
                                          @Nonnull String subject, @Nonnull String content,
                                          @Nullable MailFile... attachments) throws MailSendWayUnsupportException {
        String defaultSenderId = zooMsgProperties.getMail().getDefaultSenderId();
        if (StringUtils.isBlank(defaultSenderId)) {
            throw new MailNotPointDefaultSenderException("you could point default mail-sender by config 'zoo.msg.mail"
                    + ".default-sender-id=xxx'");
        }
        return sendText(defaultSenderId, to, subject, content, attachments);
    }
    
    /**
     * 普通邮件发送
     *
     * @param senderId 邮件发送器id
     * @param to 发送对象
     * @param subject 主题
     * @param content 内容
     * @param attachments 附件
     *
     * @return 发送结果
     */
    @Nonnull
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static MailSendResult sendText(@Nonnull String senderId, @Nonnull Collection<String> to,
                                          @Nonnull String subject, @Nonnull String content,
                                          @Nullable MailFile... attachments) throws MailSendWayUnsupportException {
        MailSender mailSender = mailSenderManager.find(senderId);
        if (mailSender == null) {
            throw new MailNoSuchSenderException("not found mail-sender whose id is '" + senderId + "'.");
        }
        return mailSender.sendText(to, subject, content, attachments);
    }
    
    /**
     * Html邮件发送
     *
     * @see #sendHtml(String, Collection, String, String, MailFile...)
     */
    @Nonnull
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static MailSendResult sendHtml(@Nonnull Collection<String> to,
                                          @Nonnull String subject, @Nonnull String html,
                                          @Nullable MailFile... attachments) throws MailSendWayUnsupportException {
        String defaultSenderId = zooMsgProperties.getMail().getDefaultSenderId();
        if (StringUtils.isBlank(defaultSenderId)) {
            throw new MailNotPointDefaultSenderException("you could point default mail-sender by config 'zoo.msg.mail"
                    + ".default-sender-id=xxx'");
        }
        return sendHtml(defaultSenderId, to, subject, html, attachments);
    }
    
    /**
     * Html邮件发送
     *
     * @param senderId 邮件发送器id
     * @param to 发送对象
     * @param subject 主题
     * @param html 内容
     * @param attachments 附件
     *
     * @return 发送结果
     */
    @Nonnull
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static MailSendResult sendHtml(@Nonnull String senderId, @Nonnull Collection<String> to,
                                          @Nonnull String subject, @Nonnull String html,
                                          @Nullable MailFile... attachments) throws MailSendWayUnsupportException {
        MailSender mailSender = mailSenderManager.find(senderId);
        if (mailSender == null) {
            throw new MailNoSuchSenderException("not found mail-sender whose id is '" + senderId + "'.");
        }
        return mailSender.sendHtml(to, subject, html, attachments);
    }
    
    /**
     * 发送邮件
     *
     * @see #send(String, MailSendInfo)
     */
    @Nonnull
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static MailSendResult send(@Nonnull MailSendInfo sendInfo) throws MailSendWayUnsupportException {
        String defaultSenderId = zooMsgProperties.getMail().getDefaultSenderId();
        if (StringUtils.isBlank(defaultSenderId)) {
            throw new MailNotPointDefaultSenderException("you could point default mail-sender by config 'zoo.msg.mail"
                    + ".default-sender-id=xxx'");
        }
        return send(defaultSenderId, sendInfo);
    }
    
    /**
     * 发送邮件
     *
     * @param senderId 邮件发送器id
     * @param sendInfo 邮件发送信息
     *
     * @return 发送结果
     */
    @Nonnull
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static MailSendResult send(@Nonnull String senderId, @Nonnull MailSendInfo sendInfo) throws MailSendWayUnsupportException {
        MailSender mailSender = mailSenderManager.find(senderId);
        if (mailSender == null) {
            throw new MailNoSuchSenderException("not found mail-sender whose id is '" + senderId + "'.");
        }
        return mailSender.send(sendInfo);
    }
}
