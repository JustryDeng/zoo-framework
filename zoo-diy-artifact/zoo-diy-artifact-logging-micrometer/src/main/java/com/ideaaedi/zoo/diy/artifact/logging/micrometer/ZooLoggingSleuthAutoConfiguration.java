package com.ideaaedi.zoo.diy.artifact.logging.micrometer;

import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import com.ideaaedi.zoo.diy.artifact.logging.micrometer.filter.TraceXdFilter;
import com.ideaaedi.zoo.diy.artifact.logging.micrometer.properties.ZooLoggingMicrometerDIYGuideProperties;
import com.ideaaedi.zoo.diy.artifact.logging.micrometer.util.TraceUtil;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.IdGenerator;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.SdkTracerProviderBuilder;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.tracing.OpenTelemetryAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 * micrometer自动配置
 */
@Slf4j
@Import(TraceXdFilter.class)
@AutoConfigureBefore(OpenTelemetryAutoConfiguration.class)
@EnableConfigurationProperties({ZooLoggingMicrometerDIYGuideProperties.class})
public class ZooLoggingSleuthAutoConfiguration {
    
    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        /// observationRegistry.observationConfig().observationHandler(...); // 注册观察处理器
        return new ObservedAspect(observationRegistry);
    }
    
    /**
     * 重写traceId spanId
     *
     * @see org.springframework.boot.actuate.autoconfigure.tracing.OpenTelemetryAutoConfiguration otelSdkTracerProvider
     */
    @Bean
    @ConditionalOnMissingBean
    public SdkTracerProvider otelSdkTracerProvider(Environment environment,
                                                   ObjectProvider<SpanProcessor> spanProcessors,
                                                   Sampler sampler) {
        String applicationName = environment.getProperty("spring.application.name", "application");
        SdkTracerProviderBuilder builder = SdkTracerProvider.builder()
                .setIdGenerator(new IdGenerator() {
                    /*
                     * 长度要16位
                     */
                    @Override
                    public String generateSpanId() {
                        return IdGenerator.random().generateSpanId();
                    }
                    
                    /*
                     * 长度要32位
                     */
                    @Override
                    public String generateTraceId() {
                        String traceXd = MDC.get(BaseConstant.TRACE_XD);
                        if (StringUtils.isBlank(traceXd)) {
                            return IdGenerator.random().generateTraceId();
                        }
                        return TraceUtil.extractTraceId(traceXd);
                    }
                })
                .setSampler(sampler)
                .setResource(Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, applicationName)));
        spanProcessors.orderedStream().forEach(builder::addSpanProcessor);
        return builder.build();
    }
    
    /**
     * 重写traceId spanId
     *
     * @see org.springframework.boot.actuate.autoconfigure.tracing.OpenTelemetryAutoConfiguration otelSampler
     */
    @Bean
    @ConditionalOnMissingBean
    Sampler otelSampler(Environment environment) {
        String probability = environment.getProperty("management.tracing.sampling.probability", "0");
        Sampler rootSampler = Sampler.traceIdRatioBased(Double.parseDouble(probability));
        return Sampler.parentBased(rootSampler);
    }
}
