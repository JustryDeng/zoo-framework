package com.ideaaedi.zoo.diy.feature.msg.api;

import com.ideaaedi.zoo.diy.feature.msg.api.mail.MailFaceUtilInitializer;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.core.DefaultMailSenderManager;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.core.MailSender;
import com.ideaaedi.zoo.diy.feature.msg.api.mail.core.MailSenderManager;
import com.ideaaedi.zoo.diy.feature.msg.api.properties.ZooMsgApiDIYGuideProperties;
import com.ideaaedi.zoo.diy.feature.msg.api.properties.ZooMsgProperties;
import com.ideaaedi.zoo.diy.feature.msg.api.sms.SmsFaceUtilInitializer;
import com.ideaaedi.zoo.diy.feature.msg.api.sms.core.DefaultSmsSenderManager;
import com.ideaaedi.zoo.diy.feature.msg.api.sms.core.SmsSender;
import com.ideaaedi.zoo.diy.feature.msg.api.sms.core.SmsSenderManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 消息 自动配置
 */
@EnableConfigurationProperties({ZooMsgProperties.class, ZooMsgApiDIYGuideProperties.class})
public class ZooMsgApiAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(SmsSenderManager.class)
    public SmsSenderManager smsSenderManager(List<SmsSender> senderList) {
        return new DefaultSmsSenderManager(senderList);
    }
    
    @Bean
    @ConditionalOnMissingBean(MailSenderManager.class)
    public MailSenderManager mailSenderManager(List<MailSender> senderList) {
        return new DefaultMailSenderManager(senderList);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public SmsFaceUtilInitializer smsFaceUtilInitializer(SmsSenderManager smsSenderManager, ZooMsgProperties zooMsgProperties) {
        return new SmsFaceUtilInitializer(smsSenderManager, zooMsgProperties);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public MailFaceUtilInitializer mailFaceUtilInitializer(MailSenderManager mailSenderManager, ZooMsgProperties zooMsgProperties) {
        return new MailFaceUtilInitializer(mailSenderManager, zooMsgProperties);
    }
}