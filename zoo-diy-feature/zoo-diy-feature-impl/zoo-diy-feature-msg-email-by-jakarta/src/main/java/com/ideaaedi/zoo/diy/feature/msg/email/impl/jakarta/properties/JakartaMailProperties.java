package com.ideaaedi.zoo.diy.feature.msg.email.impl.jakarta.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Validated
@ConfigurationProperties(prefix = JakartaMailProperties.PREFIX)
public class JakartaMailProperties {
    
    public static final String PREFIX = "jakarta.mail";
    
    @Valid
    @NestedConfigurationProperty
    private List<JakartaMailSenderProperties> senders;
    
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class JakartaMailSenderProperties extends MailProperties {
        
        /**
         * 邮件发送器id
         */
        @NotBlank(message = "mail sender id cannot be blank.")
        private String id;
        
        /**
         * 默认发件人邮箱
         */
        private String defaultFrom;
    }
}
