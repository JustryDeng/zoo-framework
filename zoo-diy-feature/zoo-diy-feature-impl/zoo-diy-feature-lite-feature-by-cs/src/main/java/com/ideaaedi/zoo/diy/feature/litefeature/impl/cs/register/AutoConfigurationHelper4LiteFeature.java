package com.ideaaedi.zoo.diy.feature.litefeature.impl.cs.register;

import com.ideaaedi.commonds.constants.BeanNameConstant;
import com.ideaaedi.zoo.commonbase.zoo_injector.ExtAutowiredInjector;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@AutoConfiguration
public class AutoConfigurationHelper4LiteFeature {
    
    public static final String BEAN_NAME = "autoConfigurationHelper4LiteFeature";
    
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
}