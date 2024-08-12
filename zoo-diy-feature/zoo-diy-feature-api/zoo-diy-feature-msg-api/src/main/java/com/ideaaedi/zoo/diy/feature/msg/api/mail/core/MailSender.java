package com.ideaaedi.zoo.diy.feature.msg.api.mail.core;

import com.ideaaedi.zoo.diy.feature.msg.api.exception.MailSendWayUnsupportException;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.entity.MailFile;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.entity.MailSendInfo;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.entity.MailSendResult;
import com.ideaaedi.zoo.diy.feature.msg.api.msg.MsgSender;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;

/**
 * 邮件发送器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface MailSender extends MsgSender {
    
    /**
     * 默认的发件人邮箱
     */
    @Nullable
    String defaultFrom();
    
    /**
     * 普通邮件发送
     *
     * @param to 发送对象
     * @param subject 主题
     * @param content 内容
     * @param attachments 附件
     *
     * @return 发送结果
     */
    @Nonnull
    default MailSendResult sendText(@Nonnull Collection<String> to,
                                    @Nonnull String subject, @Nonnull String content,
                                    @Nullable MailFile... attachments) throws MailSendWayUnsupportException {
        return send(MailSendInfo.builder()
                .to(to)
                .subject(subject)
                .content(content)
                .isHtml(false)
                .attachments(attachments == null ? null : Arrays.stream(attachments).toList())
                .build());
    }
    
    /**
     * Html邮件发送
     *
     * @param to 发送对象
     * @param subject 主题
     * @param html 内容
     * @param attachments 附件
     *
     * @return 发送结果
     */
    @Nonnull
    default MailSendResult sendHtml(@Nonnull Collection<String> to,
                                    @Nonnull String subject, @Nonnull String html,
                                    @Nullable MailFile... attachments) throws MailSendWayUnsupportException {
        return send(MailSendInfo.builder()
                .to(to)
                .subject(subject)
                .content(html)
                .isHtml(true)
                .attachments(attachments == null ? null : Arrays.stream(attachments).toList())
                .build());
    }
    
    /**
     * 发送邮件
     *
     * @param sendInfo 邮件发送信息
     *
     * @return 发送结果
     */
    @Nonnull
    default MailSendResult send(@Nonnull MailSendInfo sendInfo) throws MailSendWayUnsupportException {
        throw new MailSendWayUnsupportException(this.getClass().getName() + " not support send(MailSendInfo)");
    }
}
