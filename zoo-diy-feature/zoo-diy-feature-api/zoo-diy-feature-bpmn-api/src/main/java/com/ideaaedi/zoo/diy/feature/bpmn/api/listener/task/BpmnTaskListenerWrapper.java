package com.ideaaedi.zoo.diy.feature.bpmn.api.listener.task;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

import javax.annotation.Nonnull;

/**
 * BpmnTaskListener包装器。
 * <p>
 * 主要功能是保证：各个zoo-diy-feature-bpmn-api的实现里，适配了BpmnTaskListener监听器
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public abstract class BpmnTaskListenerWrapper implements BeanPostProcessor {
    
    @Nullable
    @Override
    public Object postProcessAfterInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        if (bean instanceof BpmnTaskListener bpmnTaskListener) {
            return doWrap(bpmnTaskListener);
        }
        return bean;
    }
    
    @Nonnull
    public abstract BpmnTaskListener doWrap(@Nonnull BpmnTaskListener bpmnTaskListenerBean);
}
