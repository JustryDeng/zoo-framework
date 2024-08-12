package com.ideaaedi.zoo.diy.feature.msg.email.impl.jakarta;

import com.ideaaedi.zoo.diy.feature.msg.api.ZooMsgApiAutoConfiguration;
import com.ideaaedi.zoo.diy.feature.msg.email.impl.jakarta.core.JakartaMailRegistry;
import com.ideaaedi.zoo.diy.feature.msg.email.impl.jakarta.properties.JakartaMailProperties;
import com.ideaaedi.zoo.diy.feature.msg.email.impl.jakarta.properties.ZooEmailJakartaDIYGuideProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * jakarta email 自动配置
 */
@AutoConfigureAfter(ZooMsgApiAutoConfiguration.class)
@AutoConfigureBefore(MailSenderAutoConfiguration.class)
@EnableConfigurationProperties({ZooEmailJakartaDIYGuideProperties.class, JakartaMailProperties.class})
public class ZooEmailJakartaAutoConfiguration {
    
    @Bean
    public JakartaMailRegistry jakartaMailRegistry() {
        return new JakartaMailRegistry();
    }
}