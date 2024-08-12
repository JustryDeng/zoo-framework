package com.ideaaedi.zoo.diy.feature.litefeature.impl.cs.register;

import com.ideaaedi.commonspring.lite.register.FeatureRegistrarFace;
import com.ideaaedi.commonspring.register.CommonSpringFeatureRegistrar;
import com.ideaaedi.zoo.diy.feature.litefeature.api.lock.LockAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.Ordered;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class CsFeatureRegistrar extends CommonSpringFeatureRegistrar {
    
    @Override
    protected void registerLockAop(BeanDefinitionRegistry registry) {
        super.registerLockAop(registry);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(LockAdvice.class);
        registry.registerBeanDefinition(LockAdvice.BEAN_NAME, builder.getBeanDefinition());
        log.info("registry bean '{}' with class '{}'", LockAdvice.BEAN_NAME, LockAdvice.class.getName());
    }
    
    @Override
    protected void registerExtAutowiredInjector(BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(AutoConfigurationHelper4LiteFeature.class);
        registry.registerBeanDefinition(AutoConfigurationHelper4LiteFeature.BEAN_NAME, builder.getBeanDefinition());
        log.info("registry bean '{}' with class '{}'", AutoConfigurationHelper4LiteFeature.BEAN_NAME, AutoConfigurationHelper4LiteFeature.class.getName());
    }
    
    /**
     * 根据{@link FeatureRegistrarFace}中的逻辑，将会采用优先级最高的那个注册器；
     * 这里通过设置优先级高于{@link CommonSpringFeatureRegistrar}进行替换
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 200;
    }
}
