package com.ideaaedi.zoo.commonbase.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;

/*
 * 环境变量处理
 * <br />
 * 参考<a href="https://docs.spring.io/spring-boot/docs/3.0.2/reference/htmlsingle/#howto.application.customize-the-environment-or-application-context">官网</a>
 */
public abstract class ZooEnvironmentPostProcessor implements EnvironmentPostProcessor {
    
    protected final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
    
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Resource path = new ClassPathResource(propertySourceClasspath());
        PropertySource<?> propertySource = loadYaml(path);
        // environment.getPropertySources()中，越后面PropertySources中的键的优先级越低
        if (highestPriority()) {
            environment.getPropertySources().addFirst(propertySource);
        } else {
            environment.getPropertySources().addLast(propertySource);
        }
    }
    
    /*
     * 加载yaml、yml文件
     */
    protected PropertySource<?> loadYaml(Resource path) {
        Assert.isTrue(path.exists(), () -> "Resource " + path + " does not exist");
        try {
            return this.loader.load(propertySourceName(), path).get(0);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load yaml configuration from " + path, ex);
        }
    }
    
    /*
     * 定义资源文件唯一名称
     *
     * @return 资源文件唯一名称
     */
    protected abstract String propertySourceName();
    
    /*
     * 资源文件classpath路径
     * <br />
     * 如：user-setting.yml
     *
     * @return 资源文件classpath路径
     */
    protected abstract String propertySourceClasspath();
    
    /*
     * 资源文件里的key优先级是否设置为最高
     *
     * @return true-key优先级最高；false-key优先级最低
     */
    protected abstract boolean highestPriority();
}
