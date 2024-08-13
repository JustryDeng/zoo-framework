package com.ideaaedi.zoo.commonbase.zoo_injector;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 对普通bean(即:非spring-bean)的autowired注入支持
 * <pre>
 *     使用方式一：
 *         User user = new User();
 *         // 直接使用ExtAutowiredInjector.inject注入
 *         ExtAutowiredInjector.inject(user);
 *         user.sayHello();
 *     使用方式二：
 *        // 直接继承ExtAutowiredInjector
 *        public class User extends ExtAutowiredInjector {
 *        User user = new User();
 *        user.sayHello();
 * </pre>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public abstract class ExtAutowiredInjector {
    
    public ExtAutowiredInjector() {
        Object obj = applyAutowired();
        ExtAutowiredInjector.inject(obj);
    }
    
    /**
     * 对返回的object进行autowired注入处理
     *
     * @return 要进行注入处理的Object
     */
    protected Object applyAutowired() {
        return this;
    }
    
    /**
     * 对目标对象中的被@Auwired标注了的字段进行注入
     *
     * @param obj
     *            目标obj
     */
    public static void inject(Object obj) {
        if (obj == null) {
            log.warn("obj is null, skip inject.");
            return;
        }
        if (ExtAutowiredInjectorProcessor.applicationContext != null) {
            ExtAutowiredInjectorProcessor.applicationContext.publishEvent(new AutowiredInjectEvent(obj));
        } else {
            ExtAutowiredInjectorProcessor.queue.add(obj);
        }
    }
    
    /**
     * Autowired注入支持事件
     *
     * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
     * @since 1.0.0
     */
    public static class AutowiredInjectEvent extends ApplicationEvent {
        
        @Getter
        private final Object bean;
        
        /**
         * Create a new ApplicationEvent.
         *
         * @param source
         *         the object on which the event initially occurred (never {@code null})
         */
        public AutowiredInjectEvent(Object source) {
            super(source);
            Objects.requireNonNull(source, "source cannot be null.");
            this.bean = source;
        }
    }
    
    /**
     * {@link ExtAutowiredInjector}处理器
     *
     * <pre>
     * 注：此类是从{@code com.ideaaedi:common-spring:2100.9.4}中拷贝出来的，
     *    如果当前项目还引入了com.ideaaedi:common-spring依赖，
     *    那么就存在两个名为{@code ExtAutowiredInjectorProcessor}的类（当然，他们的全类名一样）
     *
     *    不论后续这两个类是否会存在差异，本项目中都使用当前这个{@link ExtAutowiredInjectorProcessor}
     * </pre>
     *
     * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
     * @since 1.0.0
     */
    @Slf4j
    public static class ExtAutowiredInjectorProcessor implements SmartInitializingSingleton, ApplicationListener<AutowiredInjectEvent> {
    
        static ApplicationContext applicationContext;
        
        static Queue<Object> queue = new ConcurrentLinkedQueue<>();
        
        private final AutowireCapableBeanFactory autowireCapableBeanFactory;
        
        public ExtAutowiredInjectorProcessor(AutowireCapableBeanFactory autowireCapableBeanFactory,
                                             ApplicationContext applicationContext) {
            this.autowireCapableBeanFactory = autowireCapableBeanFactory;
            ExtAutowiredInjectorProcessor.applicationContext = applicationContext;
        }
        
        @Override
        public void onApplicationEvent(@NonNull AutowiredInjectEvent event) {
            Object bean = event.getBean();
            autowireCapableBeanFactory.autowireBean(bean);
            afterSingletonsInstantiated();
        }
        
        @Override
        public void afterSingletonsInstantiated() {
            Object obj = queue.poll();
            while (obj != null) {
                autowireCapableBeanFactory.autowireBean(obj);
                obj = queue.poll();
            }
        }
    }
}
