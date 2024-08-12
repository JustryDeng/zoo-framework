package com.ideaaedi.zoo.diy.feature.fieldperm.api;

import com.ideaaedi.zoo.diy.feature.fieldperm.api.advice.FieldPermPointcutAdvisor;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.advice.FieldPermValidator;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.spi.MethodArgFieldParser;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.spi.MethodArgFieldRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 接口字段权限 自动配置
 */
@Slf4j
public class ZooFieldPermAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public FieldPermValidator fieldPermValidator(MethodArgFieldRepositoryService repositoryService,
                                                 @Autowired(required = false) FieldPermValidator.FieldPermOrderProvider fieldPermOrderProvider) {
        return new FieldPermValidator(repositoryService, fieldPermOrderProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public FieldPermPointcutAdvisor fieldPermPointcutAdvisor(MethodArgFieldParser methodArgFieldParser,
                                                             FieldPermValidator fieldPermValidator) {
        return new FieldPermPointcutAdvisor(methodArgFieldParser, fieldPermValidator);
    }
}