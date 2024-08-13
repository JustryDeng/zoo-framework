package com.ideaaedi.zoo.diy.feature.i18n.api;

import com.ideaaedi.zoo.diy.feature.i18n.api.provider.I18Translator;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;

/**
 * i18n 自动配置
 */
@Slf4j
public class ZooI18nApiAutoConfiguration  implements SmartInitializingSingleton {
    
    @Resource
    private ApplicationContext applicationContext;
    
    @Override
    public void afterSingletonsInstantiated() {
        try {
            I18nUtil.i18Translator = applicationContext.getBean(I18Translator.class);
        } catch (BeansException e) {
            log.info("Not found i18-translator.");
        }
    }
}