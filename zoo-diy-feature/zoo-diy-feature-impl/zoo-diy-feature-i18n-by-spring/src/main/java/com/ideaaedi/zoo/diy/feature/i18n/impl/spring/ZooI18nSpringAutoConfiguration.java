package com.ideaaedi.zoo.diy.feature.i18n.impl.spring;

import com.ideaaedi.zoo.diy.feature.i18n.impl.spring.core.SpringI18Translator;
import com.ideaaedi.zoo.diy.feature.i18n.impl.spring.core.ZooI18nSpringLocaleResolver;
import com.ideaaedi.zoo.diy.feature.i18n.impl.spring.properties.ZooI18nSpringDIYGuideProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * i18n 自动配置
 */
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties({ZooI18nSpringDIYGuideProperties.class})
public class ZooI18nSpringAutoConfiguration {
    
    @Bean(name = DispatcherServlet.LOCALE_RESOLVER_BEAN_NAME)
    public ZooI18nSpringLocaleResolver zooI18nSpringLocaleResolver(){
        return new ZooI18nSpringLocaleResolver();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public SpringI18Translator springI18Translator(MessageSource messageSource){
        return new SpringI18Translator(messageSource);
    }

}