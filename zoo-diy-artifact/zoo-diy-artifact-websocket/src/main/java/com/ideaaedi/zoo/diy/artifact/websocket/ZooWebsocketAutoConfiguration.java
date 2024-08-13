package com.ideaaedi.zoo.diy.artifact.websocket;

import com.ideaaedi.commonds.constants.BeanNameConstant;
import com.ideaaedi.zoo.commonbase.zoo_injector.ExtAutowiredInjector;
import com.ideaaedi.zoo.diy.artifact.websocket.distribute.DistributeWebsocketMsgConfig;
import com.ideaaedi.zoo.diy.artifact.websocket.properties.ZooWebsocketDIYGuideProperties;
import com.ideaaedi.zoo.diy.artifact.websocket.properties.ZooWebsocketProperties;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * websocket 自动配置
 */
@Slf4j
@ImportAutoConfiguration(DistributeWebsocketMsgConfig.class)
@EnableConfigurationProperties({ZooWebsocketProperties.class, ZooWebsocketDIYGuideProperties.class})
public class ZooWebsocketAutoConfiguration {
    
    /**
     * {@link ServerEndpoint}端点暴露器
     */
    @Bean
    @ConditionalOnMissingBean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    
    /**
     * autowired注入增强
     */
    @Bean(name = BeanNameConstant.BEAN_NAME_4_ExtAutowiredInjectorProcessor)
    @ConditionalOnMissingBean(name = BeanNameConstant.BEAN_NAME_4_ExtAutowiredInjectorProcessor)
    public ExtAutowiredInjector.ExtAutowiredInjectorProcessor extAutowiredInjectorProcessor(AutowireCapableBeanFactory autowireCapableBeanFactory,
                                                                                            ApplicationContext applicationContext) {
        return new ExtAutowiredInjector.ExtAutowiredInjectorProcessor(autowireCapableBeanFactory,
                applicationContext);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ServletServerContainerFactoryBean servletServerContainerFactoryBean(ZooWebsocketProperties zooWebsocketProperties) {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(zooWebsocketProperties.getMaxTextMessageBufferSize());
        container.setMaxBinaryMessageBufferSize(zooWebsocketProperties.getMaxBinaryMessageBufferSize());
        container.setMaxSessionIdleTimeout(zooWebsocketProperties.getMaxSessionIdleTimeout());
        return container;
    }
}