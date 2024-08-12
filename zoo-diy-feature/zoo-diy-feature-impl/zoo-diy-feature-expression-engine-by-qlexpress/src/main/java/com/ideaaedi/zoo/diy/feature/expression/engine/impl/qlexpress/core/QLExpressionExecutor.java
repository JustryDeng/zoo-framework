package com.ideaaedi.zoo.diy.feature.expression.engine.impl.qlexpress.core;

import com.ideaaedi.zoo.diy.feature.expression.engine.api.exception.ExpressionExecException;
import com.ideaaedi.zoo.diy.feature.expression.engine.api.service.ExpressionExecutor;
import com.ideaaedi.zoo.diy.feature.expression.engine.impl.qlexpress.properties.ZooQLExpressProperties;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.instruction.op.OperatorSelfDefineClassFunction;
import com.ql.util.express.instruction.op.OperatorSelfDefineServiceFunction;
import org.springframework.context.ApplicationContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class QLExpressionExecutor implements ExpressionExecutor {
    
    private final ExpressRunner runner;
    
    private final ZooQLExpressProperties zooQLExpressProperties;
    
    private final ApplicationContext applicationContext;
    
    public QLExpressionExecutor(ZooQLExpressProperties zooQLExpressProperties, ApplicationContext applicationContext) {
        this.zooQLExpressProperties = zooQLExpressProperties;
        this.applicationContext = applicationContext;
        runner = new ExpressRunner(zooQLExpressProperties.isPrecise(), zooQLExpressProperties.isTrace());
        runner.setShortCircuit(zooQLExpressProperties.isShortCircuit());
    }
    
    @Nullable
    @Override
    public <T> T execute(@Nonnull String expressScript, @Nullable Map<String, Object> context,
                          @Nullable List<String> errorList, long timeoutMillis) throws ExpressionExecException {
        context = context == null ? Collections.emptyMap() : context;
        boolean appendSpringContext = zooQLExpressProperties.isAppendSpringContext();
        QLExpressContext qlExpressContext = new QLExpressContext(context, appendSpringContext ? applicationContext :
                null);
        try {
            //noinspection unchecked
            return (T)runner.execute(expressScript, qlExpressContext, errorList, true, false, timeoutMillis);
        } catch (Exception e) {
            throw new ExpressionExecException(e);
        }
    }
    
    @Override
    public boolean checkSyntax(@Nonnull String expressScript) {
        return runner.checkSyntax(expressScript);
    }
    
    @Nonnull
    @Override
    public Set<String> getOutVarNames(@Nonnull String expressScript) {
        String[] outVarNames;
        try {
            outVarNames = runner.getOutVarNames(expressScript);
        } catch (Exception e) {
            throw new ExpressionExecException(e);
        }
        return outVarNames == null ? Collections.emptySet() : Set.of(outVarNames);
    }
    
    @Nonnull
    @Override
    public Set<String> getOutFunctionNames(@Nonnull String expressScript) throws ExpressionExecException {
        String[] outFunctionNames;
        try {
            outFunctionNames = runner.getOutFunctionNames(expressScript);
        } catch (Exception e) {
            throw new ExpressionExecException(e);
        }
        return outFunctionNames == null ? Collections.emptySet() : Set.of(outFunctionNames);
    }
    
    @Override
    public void addGlobalFunction(@Nonnull String name, @Nonnull Class<?> clazz, @Nonnull String methodName,
                                  @Nonnull Class<?>[] paramTypes, boolean force) throws ExpressionExecException {
        if (force) {
            boolean existOperator = runner.getOperatorFactory().isExistOperator(name);
            if (existOperator) {
                try {
                    runner.replaceOperator(
                            name,
                            new OperatorSelfDefineClassFunction(
                                    name, clazz, methodName, paramTypes, null, null, null
                            )
                    );
                    // 如果发生了覆盖，那么清一下缓存，防止覆盖后新的方法不生效（还生效的之前的）
                    runner.clearExpressCache();
                    return;
                } catch (Exception e) {
                    throw new ExpressionExecException(e);
                }
            }
        }
        try {
            runner.addFunctionOfClassMethod(name, clazz, methodName, paramTypes, null);
        } catch (Exception e) {
            throw new ExpressionExecException(e);
        }
    }
    
    @Override
    public void addGlobalFunction(@Nonnull String name, @Nonnull Object instance, @Nonnull String methodName,
                                  @Nonnull Class<?>[] paramTypes, boolean force) throws ExpressionExecException {
        if (force) {
            boolean existOperator = runner.getOperatorFactory().isExistOperator(name);
            if (existOperator) {
                try {
                    runner.replaceOperator(
                            name,
                            new OperatorSelfDefineServiceFunction(
                                    name, instance, methodName, paramTypes, null, null, null
                            )
                    );
                    // 如果发生了覆盖，那么清一下缓存，防止覆盖后新的方法不生效（还生效的之前的）
                    runner.clearExpressCache();
                    return;
                } catch (Exception e) {
                    throw new ExpressionExecException(e);
                }
            }
        }
        try {
            runner.addFunctionOfServiceMethod(name, instance, methodName, paramTypes, null);
        } catch (Exception e) {
            throw new ExpressionExecException(e);
        }
    }
}
