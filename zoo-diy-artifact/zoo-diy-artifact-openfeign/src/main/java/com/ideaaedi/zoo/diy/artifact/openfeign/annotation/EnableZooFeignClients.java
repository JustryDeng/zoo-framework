package com.ideaaedi.zoo.diy.artifact.openfeign.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.ZooFeignClientsRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 增强{@link EnableFeignClients}，增强点：
 *   1. 将@Import(FeignClientsRegistrar.class)改为@Import(ZooFeignClientsRegistrar.class)
 * </pre>
 * <hr />
 * Scans for interfaces that declare they are feign clients (via {@link org.springframework.cloud.openfeign.FeignClient}
 * <code>@FeignClient</code>). Configures component scanning directives for use with
 * {@link org.springframework.context.annotation.Configuration}
 * <code>@Configuration</code> classes.
 *
 * @author Spencer Gibb
 * @author Dave Syer
 * @author JustryDeng
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ZooFeignClientsRegistrar.class)
public @interface EnableZooFeignClients {
    
    /**
     * Alias for the {@link #basePackages()} attribute. Allows for more concise annotation declarations e.g.:
     * {@code @ComponentScan("org.my.pkg")} instead of {@code @ComponentScan(basePackages="org.my.pkg")}.
     *
     * @return the array of 'basePackages'.
     */
    String[] value() default {};
    
    /**
     * Base packages to scan for annotated components.
     * <p>
     * {@link #value()} is an alias for (and mutually exclusive with) this attribute.
     * <p>
     * Use {@link #basePackageClasses()} for a type-safe alternative to String-based package names.
     *
     * @return the array of 'basePackages'.
     */
    String[] basePackages() default {};
    
    /**
     * Type-safe alternative to {@link #basePackages()} for specifying the packages to scan for annotated components.
     * The package of each class specified will be scanned.
     * <p>
     * Consider creating a special no-op marker class or interface in each package that serves no purpose other than
     * being referenced by this attribute.
     *
     * @return the array of 'basePackageClasses'.
     */
    Class<?>[] basePackageClasses() default {};
    
    /**
     * A custom <code>@Configuration</code> for all feign clients. Can contain override
     * <code>@Bean</code> definition for the pieces that make up the client, for instance
     * {@link feign.codec.Decoder}, {@link feign.codec.Encoder}, {@link feign.Contract}.
     *
     * @return list of default configurations
     *
     * @see FeignClientsConfiguration for the defaults
     */
    Class<?>[] defaultConfiguration() default {};
    
    /**
     * List of classes annotated with @FeignClient. If not empty, disables classpath scanning.
     *
     * @return list of FeignClient classes
     */
    Class<?>[] clients() default {};
    
}
