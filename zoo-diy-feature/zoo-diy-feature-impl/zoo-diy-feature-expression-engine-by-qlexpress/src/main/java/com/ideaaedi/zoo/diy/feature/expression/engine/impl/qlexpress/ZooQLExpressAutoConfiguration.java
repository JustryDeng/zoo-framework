package com.ideaaedi.zoo.diy.feature.expression.engine.impl.qlexpress;

import com.ideaaedi.zoo.diy.feature.expression.engine.impl.qlexpress.core.QLExpressInitConfig;
import com.ideaaedi.zoo.diy.feature.expression.engine.impl.qlexpress.core.QLExpressionExecutor;
import com.ideaaedi.zoo.diy.feature.expression.engine.impl.qlexpress.properties.ZooQLExpressDIYGuideProperties;
import com.ideaaedi.zoo.diy.feature.expression.engine.impl.qlexpress.properties.ZooQLExpressProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * QLExpress 自动配置
 */
@EnableConfigurationProperties({ZooQLExpressDIYGuideProperties.class, ZooQLExpressProperties.class})
public class ZooQLExpressAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public QLExpressionExecutor qlExpressionExecutor(ZooQLExpressProperties zooQLExpressProperties, ApplicationContext applicationContext) {
        return new QLExpressionExecutor(zooQLExpressProperties, applicationContext);
    }
    
    @Bean
    public QLExpressInitConfig qlExpressInitConfig(ZooQLExpressProperties zooQLExpressProperties) {
        return new QLExpressInitConfig(zooQLExpressProperties);
    }
    
}