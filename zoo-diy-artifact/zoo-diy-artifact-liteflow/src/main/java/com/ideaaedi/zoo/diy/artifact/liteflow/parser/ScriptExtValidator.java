package com.ideaaedi.zoo.diy.artifact.liteflow.parser;

import com.yomahub.liteflow.enums.ScriptTypeEnum;
import com.yomahub.liteflow.script.ScriptExecutor;
import com.yomahub.liteflow.script.validator.ScriptValidator;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 增强点：校验不通过时，将异常返回来
 * <p>
 * 增强{@link ScriptValidator#validateScript(String, ScriptTypeEnum)}
 * </p>
 */
public class ScriptExtValidator {
    
    private static Map<ScriptTypeEnum, ScriptExecutor> scriptExecutors;
    
    static {
        try {
            Field field = ScriptValidator.class.getDeclaredField("scriptExecutors");
            field.setAccessible(true);
            //noinspection unchecked
            scriptExecutors = (Map<ScriptTypeEnum, ScriptExecutor>) field.get(ScriptValidator.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
    
    /**
     * 校验script语法是否正确
     *
     * @return null表示正确，否则返回异常
     */
    public static Exception validateScript(String script, ScriptTypeEnum scriptType) {
        try {
            scriptExecutors.get(scriptType).compile(script);
        } catch (Exception e) {
            return e;
        }
        return null;
    }
}
