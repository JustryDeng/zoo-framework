package com.ideaaedi.zoo.diy.feature.msg.email.impl.jakarta.core;

import com.ideaaedi.zoo.diy.feature.msg.email.impl.jakarta.properties.JakartaMailProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class JakartaMailRegistry implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {
    
    private JakartaMailProperties jakartaMailProperties;
    
    @Override
    public void postProcessBeanDefinitionRegistry(@Nonnull BeanDefinitionRegistry registry) throws BeansException {
        if (jakartaMailProperties == null) {
            return;
        }
        List<JakartaMailProperties.JakartaMailSenderProperties> mailSenders = jakartaMailProperties.getSenders();
        if (CollectionUtils.isEmpty(mailSenders)) {
            return;
        }
        for (JakartaMailProperties.JakartaMailSenderProperties mailSenderProperties : mailSenders) {
            String id = mailSenderProperties.getId();
            if (StringUtils.isBlank(id)) {
                throw new IllegalArgumentException("'jakarta.mail.senders.id' cannot be blank. please check your config.");
            }
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(JakartaMailSender.class);
            builder.addConstructorArgValue(mailSenderProperties);
            registry.registerBeanDefinition("jakartaMailSender_" + id,
                    builder.getBeanDefinition());
        }
    }
    
    @Override
    public void postProcessBeanFactory(@Nonnull ConfigurableListableBeanFactory beanFactory) throws BeansException {
    
    }
    
    @Override
    public void setEnvironment(@Nonnull Environment environment) {
        try {
            jakartaMailProperties = Binder.get(environment).bind(JakartaMailProperties.PREFIX, JakartaMailProperties.class).get();
        } catch (NoSuchElementException ignore) {
            // ignore. may be no relative config
        }
    }
}
