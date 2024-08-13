package com.ideaaedi.zoo.diy.feature.expression.engine.api.advice;

import com.ideaaedi.zoo.commonbase.constant.AutoConfigurationConstant;
import com.ideaaedi.zoo.diy.feature.expression.engine.api.annotation.ExpressionReplacer;
import com.ideaaedi.zoo.diy.feature.expression.engine.api.entity.ExpressionScriptInfoDTO;
import com.ideaaedi.zoo.diy.feature.expression.engine.api.spi.ExpressionReplaceExecutor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotatedElementUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class ExpressionReplaceInterceptor implements MethodInterceptor, Ordered {
    
    private final ExpressionReplaceExecutor expressionReplaceExecutor;
    
    private ParameterNameDiscoverer parameterNameDiscoverer;
    
    private final ExpressionInterceptorOrderProvider expressionItpOrderProvider;
    
    public ExpressionReplaceInterceptor(ExpressionReplaceExecutor expressionReplaceExecutor,
                                        ParameterNameDiscoverer parameterNameDiscoverer,
                                        ExpressionInterceptorOrderProvider expressionItpOrderProvider) {
        this.expressionReplaceExecutor = expressionReplaceExecutor;
        this.parameterNameDiscoverer = parameterNameDiscoverer;
        this.expressionItpOrderProvider = expressionItpOrderProvider;
    }
    
    @PostConstruct
    private void init() {
        if (parameterNameDiscoverer == null) {
            parameterNameDiscoverer = new StandardReflectionParameterNameDiscoverer();
        }
    }
    
    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        ExpressionReplacer expressionReplacer = AnnotatedElementUtils.findMergedAnnotation(method, ExpressionReplacer.class);
        Objects.requireNonNull(expressionReplacer, "expressionReplacer should not be null.");
        Map<String, Object> params = new HashMap<>(8);
        Object[] arguments = invocation.getArguments();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        int paramCount = parameterNames == null ? 0 : parameterNames.length;
        if (paramCount != arguments.length) {
            // should never happen
            throw new IllegalStateException("obtain params name quantity incorrect. excepted is "
                    + arguments.length + ", but obtain " + paramCount);
        }
        for (int i = 0; i < paramCount; i++) {
            params.put(parameterNames[i], arguments[i]);
        }
        String bizKey = expressionReplacer.value();
        ExpressionScriptInfoDTO expressionScriptInfo = expressionReplaceExecutor.support(bizKey, method, params);
        if (expressionScriptInfo != null) {
            String supportScript = expressionScriptInfo.getSupportScript();
            String expressionScript = expressionScriptInfo.getExpressionScript();
            log.info("hit express-logic-replacer. supportScript -> {} expressionScript -> {}", supportScript, expressionScript);
            return expressionReplaceExecutor.replace(expressionScriptInfo, method, params);
        }
        return invocation.proceed();
    }
    
    @Override
    public int getOrder() {
        if (expressionItpOrderProvider != null) {
            return expressionItpOrderProvider.getOrder();
        }
        return AutoConfigurationConstant.ZOO_EXPRESSION_INTERCEPTOR_ORDER;
    }
    
    public interface ExpressionInterceptorOrderProvider extends Ordered {
    
    }
}
