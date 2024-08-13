package com.ideaaedi.zoo.diy.feature.expression.engine.impl.qlexpress.core;


import com.ql.util.express.IExpressContext;
import org.springframework.context.ApplicationContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * 支持spring bean的上下文
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class QLExpressContext extends HashMap<String, Object> implements IExpressContext<String, Object> {
    
    @Nullable
    private final ApplicationContext applicationContext;
    
    public QLExpressContext() {
        super(2);
        this.applicationContext = null;
    }
    
    public QLExpressContext(@Nonnull Map<String, Object> properties) {
        super(properties);
        this.applicationContext = null;
    }
    
    public QLExpressContext(@Nonnull Map<String, Object> properties, @Nullable ApplicationContext applicationContext) {
        super(properties);
        this.applicationContext = applicationContext;
    }

    @Override
    public Object get(Object name) {
        Object result;
        result = super.get(name);
        if (result == null && this.applicationContext != null && this.applicationContext.containsBean((String) name)) {
            result = this.applicationContext.getBean((String) name);
        }
        return result;
    }
}