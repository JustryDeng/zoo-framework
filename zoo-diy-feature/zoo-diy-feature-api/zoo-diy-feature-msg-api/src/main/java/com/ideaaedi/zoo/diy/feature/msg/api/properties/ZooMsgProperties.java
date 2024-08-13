package com.ideaaedi.zoo.diy.feature.msg.api.properties;

import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Validated
@ConfigurationProperties(prefix = "zoo.msg")
public class ZooMsgProperties {
    
    @Valid
    @NestedConfigurationProperty
    private SmsProperties sms = new SmsProperties();
    
    @Valid
    @NestedConfigurationProperty
    private MailProperties mail = new MailProperties();
    
    @Data
    public static class SmsProperties {
        
        /**
         * 默认使用的短信发送者id
         */
        private String defaultSenderId;
    }
    
    @Data
    public static class MailProperties {
        
        /**
         * 默认使用的邮件发送者id
         */
        private String defaultSenderId;
    }
}
