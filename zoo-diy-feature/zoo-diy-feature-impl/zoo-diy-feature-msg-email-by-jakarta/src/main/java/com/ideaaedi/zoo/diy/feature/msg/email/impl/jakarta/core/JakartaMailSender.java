package com.ideaaedi.zoo.diy.feature.msg.email.impl.jakarta.core;

import com.ideaaedi.zoo.diy.feature.msg.api.exception.MailNotFromException;
import com.ideaaedi.zoo.diy.feature.msg.api.exception.MailSendWayUnsupportException;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.core.MailSender;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.entity.MailFile;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.entity.MailSendInfo;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.entity.MailSendResult;
import com.ideaaedi.zoo.diy.feature.msg.email.impl.jakarta.properties.JakartaMailProperties;
import jakarta.mail.internet.MimeUtility;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * jakarta 邮箱实现
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class JakartaMailSender implements MailSender {
    
    private final JavaMailSenderImpl proxy;
    
    private final JakartaMailProperties.JakartaMailSenderProperties jakartaMailSenderProperties;
    
    public JakartaMailSender(JakartaMailProperties.JakartaMailSenderProperties jakartaMailSenderProperties) {
        this.proxy = buildMailSender(jakartaMailSenderProperties);
        this.jakartaMailSenderProperties = jakartaMailSenderProperties;
    }
    
    @Nonnull
    @Override
    @SneakyThrows
    public MailSendResult send(@Nonnull MailSendInfo sendInfo) throws MailSendWayUnsupportException {
        String from = sendInfo.getFrom();
        if (StringUtils.isBlank(from)) {
            String defaultFrom = defaultFrom();
            if (StringUtils.isBlank(defaultFrom)) {
                throw new MailNotFromException("default from is not found.");
            }
            sendInfo.setFrom(defaultFrom);
            from = defaultFrom;
        }
        Collection<String> to = sendInfo.getTo();
        String subject = sendInfo.getSubject();
        String content = sendInfo.getContent();
        boolean isHtml = sendInfo.isHtml();
        Collection<String> cc = sendInfo.getCc();
        Collection<String> bcc = sendInfo.getBcc();
        Collection<MailFile> inlines = sendInfo.getInlines();
        Collection<MailFile> attachments = sendInfo.getAttachments();
        //true表示支持复杂类型
        MimeMessageHelper messageHelper = new MimeMessageHelper(proxy.createMimeMessage(), true);
        messageHelper.setFrom(from);
        messageHelper.setSubject(subject);
        messageHelper.setText(content, isHtml);
        messageHelper.setTo(to.toArray(new String[0]));
        if (!CollectionUtils.isEmpty(cc)) {
            messageHelper.setCc(cc.toArray(new String[0]));
        }
        if (!CollectionUtils.isEmpty(bcc)) {
            messageHelper.setBcc(bcc.toArray(new String[0]));
        }
        if (!CollectionUtils.isEmpty(inlines)) {
            for (MailFile inline : inlines) {
                String uid = inline.getUid();
                File fileDirect = inline.getFileDirect();
                byte[] fileBytes = inline.getFileBytes();
                String contentType = inline.getContentType();
                if (StringUtils.isBlank(uid)) {
                    throw new IllegalArgumentException("'uid' cannot be blank if you use inline file.");
                }
                String encodeUid = MimeUtility.encodeWord(uid);
                if (!StringUtils.equals(encodeUid, uid)) {
                    log.info("encode uid from '{}' to '{}'.", uid, encodeUid);
                }
                if (fileDirect != null) {
                    messageHelper.addInline(encodeUid, fileDirect);
                } else {
                    if (StringUtils.isBlank(contentType)) {
                        throw new IllegalArgumentException("'contentType' cannot be blank if you use inline file by bytes.");
                    }
                    messageHelper.addInline(encodeUid, new ByteArrayResource(fileBytes), contentType);
                }
            }
        }
        if (!CollectionUtils.isEmpty(attachments)) {
            for (MailFile attachment : attachments) {
                String filename = attachment.getFilename();
                File fileDirect = attachment.getFileDirect();
                byte[] fileBytes = attachment.getFileBytes();
                String contentType = attachment.getContentType();
                if (StringUtils.isBlank(filename) && fileDirect != null) {
                    filename = fileDirect.getName();
                }
                if (StringUtils.isBlank(filename)) {
                    throw new IllegalArgumentException("'filename' cannot be blank if you use attachment file.");
                }
                String encodeFilename = MimeUtility.encodeWord(filename);
                if (!StringUtils.equals(encodeFilename, filename)) {
                    log.info("encode filename from '{}' to '{}'.", filename, encodeFilename);
                }
                if (fileDirect != null) {
                    messageHelper.addAttachment(encodeFilename, fileDirect);
                } else {
                    if (StringUtils.isBlank(contentType)) {
                        throw new IllegalArgumentException("file 'contentType' cannot be blank if you use attachment file by bytes.");
                    }
                    messageHelper.addAttachment(encodeFilename, new ByteArrayResource(fileBytes), contentType);
                }
            }
        }
        messageHelper.setSentDate(new Date());
        proxy.send(messageHelper.getMimeMessage());
        MailSendResult mailSendResult = new MailSendResult();
        mailSendResult.setSenderId(id());
        mailSendResult.setSuccess(true);
        return mailSendResult;
    }
    
    @Nullable
    @Override
    public String defaultFrom() {
        return jakartaMailSenderProperties.getDefaultFrom();
    }
    
    @Override
    public String id() {
        return jakartaMailSenderProperties.getId();
    }
    
    @Override
    public String supplier() {
        return "jakarta";
    }
    
    @Override
    public List<String> tags() {
        return Collections.emptyList();
    }
    
    /**
     * copy from {@link org.springframework.boot.autoconfigure.mail.MailSenderPropertiesConfiguration#mailSender(MailProperties)}
     */
    private static JavaMailSenderImpl buildMailSender(MailProperties properties) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        applyProperties(properties, sender);
        return sender;
    }
    
    /**
     * copy from {@link org.springframework.boot.autoconfigure.mail.MailSenderPropertiesConfiguration#applyProperties(MailProperties, JavaMailSenderImpl)}
     */
    private static void applyProperties(MailProperties properties, JavaMailSenderImpl sender) {
        sender.setHost(properties.getHost());
        if (properties.getPort() != null) {
            sender.setPort(properties.getPort());
        }
        sender.setUsername(properties.getUsername());
        sender.setPassword(properties.getPassword());
        sender.setProtocol(properties.getProtocol());
        if (properties.getDefaultEncoding() != null) {
            sender.setDefaultEncoding(properties.getDefaultEncoding().name());
        }
        if (!properties.getProperties().isEmpty()) {
            sender.setJavaMailProperties(asProperties(properties.getProperties()));
        }
    }
    
    /**
     * copy from {@link org.springframework.boot.autoconfigure.mail.MailSenderPropertiesConfiguration#asProperties(Map <String, String>)}
     */
    private static Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }
}
