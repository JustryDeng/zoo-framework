package com.ideaaedi.zoo.diy.feature.expression.engine.api.spi;

import com.ideaaedi.zoo.diy.feature.expression.engine.api.entity.ExpressionScriptInfoDTO;
import com.ideaaedi.zoo.diy.feature.expression.engine.api.service.ExpressionExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class DefaultExpressionReplaceExecutor implements ExpressionReplaceExecutor {
    
    private final ExpressionExecutor expressionExecutor;
    
    private final ExpressionScriptLoader expressionScriptLoader;
    
    public DefaultExpressionReplaceExecutor(ExpressionExecutor expressionExecutor, ExpressionScriptLoader expressionScriptLoader) {
        this.expressionExecutor = expressionExecutor;
        this.expressionScriptLoader = expressionScriptLoader;
    }
    
    @Nullable
    @Override
    public ExpressionScriptInfoDTO support(@Nonnull String bizKey, @Nonnull Method method, @Nonnull Map<String, Object> params) {
        List<ExpressionScriptInfoDTO> expressionInfoList = expressionScriptLoader.load(bizKey);
        if (CollectionUtils.isEmpty(expressionInfoList)) {
            return null;
        }
        for (ExpressionScriptInfoDTO expressionScriptInfo : expressionInfoList) {
            Serializable id = expressionScriptInfo.getId();
            String supportScript = expressionScriptInfo.getSupportScript();
            String expressionScript = expressionScriptInfo.getExpressionScript();
            if (StringUtils.isAnyBlank(supportScript, expressionScript)) {
                continue;
            }
            Object supportResult = expressionExecutor.execute(supportScript, params);
            log.debug("script-id -> {}, if-support -> {}", id, supportResult);
            if (supportResult != null && "true".equalsIgnoreCase(supportResult.toString())) {
                return expressionScriptInfo;
            }
        }
        return null;
    }
    
    @Override
    public Object replace(@Nonnull ExpressionScriptInfoDTO expressionScriptInfo, @Nonnull Method method,
                          @Nonnull Map<String, Object> params) {
        String expressionScript = expressionScriptInfo.getExpressionScript();
        return expressionExecutor.execute(expressionScript, params);
    }
}
