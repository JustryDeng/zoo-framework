package com.ideaaedi.zoo.diy.artifact.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.ideaaedi.zoo.commonbase.constant.AutoConfigurationConstant;
import com.ideaaedi.zoo.commonbase.zoo_component.auth.AuthUrlWhitelist;
import com.ideaaedi.zoo.diy.artifact.mybatisplus.handler.MybatisPlusMetaObjectHandler;
import com.ideaaedi.zoo.diy.artifact.mybatisplus.injector.MybatisPlusSqlInjector;
import com.ideaaedi.zoo.diy.artifact.mybatisplus.properties.ZooMybatisPlusDIYGuideProperties;
import com.ideaaedi.zoo.diy.artifact.mybatisplus.provider.DefaultOperatorIdProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.stream.Collectors;

/**
 * MybatisPlus ext 自动配置
 */
@Slf4j
@Import(MybatisPlusSqlInjector.class)
@EnableConfigurationProperties(ZooMybatisPlusDIYGuideProperties.class)
@AutoConfigureOrder(AutoConfigurationConstant.ZOO_MYBATIS_PLUS_AUTO_CONFIGURATION_ORDER)
public class ZooMybatisPlusAutoConfiguration {
    
    @Bean(name = AutoConfigurationConstant.MYBATIS_PLUS_INTERCEPTOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = {
            AutoConfigurationConstant.ZOO_TENANT_LIKE_AUTO_CONFIGURATION_BEAN_NAME,
            AutoConfigurationConstant.MYBATIS_PLUS_INTERCEPTOR_BEAN_NAME
    })
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        log.info("enable mybatis-plus interceptors -> {}.",
                interceptor.getInterceptors().stream().map(x -> x.getClass().getSimpleName()).collect(Collectors.toList()));
        return interceptor;
    }
    
    @Bean
    @ConditionalOnMissingBean(MetaObjectHandler.class)
    public MetaObjectHandler defaultMetaObjectHandler(
            @Autowired(required = false)  MybatisPlusMetaObjectHandler.OperatorIdProvider operatorIdProvider) {
        return new MybatisPlusMetaObjectHandler(operatorIdProvider);
    }
    
    @Bean
    @ConditionalOnMissingBean(MetaObjectHandler.class)
    public MybatisPlusMetaObjectHandler.OperatorIdProvider defaultOperatorIdProvider(
            @Autowired(required = false) AuthUrlWhitelist authUrlWhitelist) {
        return new DefaultOperatorIdProvider(authUrlWhitelist);
    }
}
