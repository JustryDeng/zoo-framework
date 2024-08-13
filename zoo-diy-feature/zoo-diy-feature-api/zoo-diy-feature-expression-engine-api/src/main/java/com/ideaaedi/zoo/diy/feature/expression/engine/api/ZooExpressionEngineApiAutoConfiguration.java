package com.ideaaedi.zoo.diy.feature.expression.engine.api;

import com.ideaaedi.zoo.diy.feature.expression.engine.api.advice.ExpressionReplaceInterceptor;
import com.ideaaedi.zoo.diy.feature.expression.engine.api.advice.ExpressionReplacePointcutAdvisor;
import com.ideaaedi.zoo.diy.feature.expression.engine.api.properties.ZooExpressionEngineDIYGuideProperties;
import com.ideaaedi.zoo.diy.feature.expression.engine.api.properties.ZooExpressionEngineProperties;
import com.ideaaedi.zoo.diy.feature.expression.engine.api.service.ExpressionExecutor;
import com.ideaaedi.zoo.diy.feature.expression.engine.api.spi.DefaultExpressionReplaceExecutor;
import com.ideaaedi.zoo.diy.feature.expression.engine.api.spi.ExpressionReplaceExecutor;
import com.ideaaedi.zoo.diy.feature.expression.engine.api.spi.ExpressionScriptLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterNameDiscoverer;

/**
 * 表达式引擎 自动配置
 */
@Slf4j
@EnableConfigurationProperties({ZooExpressionEngineDIYGuideProperties.class, ZooExpressionEngineProperties.class})
public class ZooExpressionEngineApiAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "zoo.expression-engine.exp-replacer-enable", havingValue = "true", matchIfMissing = true)
    public DefaultExpressionReplaceExecutor expressionReplaceExecutor(ExpressionExecutor expressionExecutor,
                                                                      ExpressionScriptLoader expressionScriptLoader) {
        return new DefaultExpressionReplaceExecutor(expressionExecutor, expressionScriptLoader);
    }
    
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "zoo.expression-engine.exp-replacer-enable", havingValue = "true", matchIfMissing = true)
    public ExpressionReplaceInterceptor fieldPermValidator(ExpressionReplaceExecutor expressionReplaceExecutor,
                                                           @Autowired(required = false)
                                                           ParameterNameDiscoverer parameterNameDiscoverer,
                                                           @Autowired(required = false)
                                                           ExpressionReplaceInterceptor.ExpressionInterceptorOrderProvider expressionInterceptorOrderProvider) {
        return new ExpressionReplaceInterceptor(expressionReplaceExecutor, parameterNameDiscoverer,
                expressionInterceptorOrderProvider);
    }
    
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "zoo.expression-engine.exp-replacer-enable", havingValue = "true", matchIfMissing = true)
    public ExpressionReplacePointcutAdvisor expressionReplacePointcutAdvisor(ExpressionReplaceInterceptor interceptor) {
        return new ExpressionReplacePointcutAdvisor(interceptor);
    }
    
}