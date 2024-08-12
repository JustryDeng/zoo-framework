package com.ideaaedi.zoo.diy.feature.litefeature.api.lock;

import com.ideaaedi.commonds.function.NoArgFunction;
import com.ideaaedi.commonds.lock.LockSupport;
import com.ideaaedi.commonspring.lite.EnableFeature;
import com.ideaaedi.zoo.commonbase.constant.AutoConfigurationConstant;
import com.ideaaedi.zoo.commonbase.zoo_util.SpelUtil;
import com.ideaaedi.zoo.diy.feature.litefeature.api.face.LockFaceUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 提示：此类在{@link EnableFeature#enableLockAnno()}=true时，才会动态注册进spring中
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
@Aspect
public class LockAdvice implements Ordered {
    
    public static final String BEAN_NAME = "LiteFeatureLockAdvice";
    
    @Autowired(required = false)
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private LockAdviceOrderProvider lockAdviceOrderProvider;
    
    @Around("@annotation(lockAnno)")
    public Object aroundAdvice(ProceedingJoinPoint thisJoinPoint, Lock lockAnno) {
        String lockKeySpel = lockAnno.lockKey();
        String supplierId = lockAnno.lockSupplierId();
        long waitTime = lockAnno.waitTime();
        long leaseTime = lockAnno.leaseTime();
        TimeUnit unit = lockAnno.unit();
        Method method = ((MethodSignature) thisJoinPoint.getSignature()).getMethod();
        Object[] arguments = thisJoinPoint.getArgs();
        String lockKey;
        if (StringUtils.isNotBlank(lockKeySpel) && lockKeySpel.contains("#")) {
            lockKey = SpelUtil.parseSpel(method, arguments, String.class, lockKeySpel);
        } else {
            lockKey = lockKeySpel;
        }
        Objects.requireNonNull(lockKey, "lockKey should noe be null.");
        log.debug("supplierId -> {}, lockKeySpel -> {}, lockKey -> {}, waitTime -> {}, leaseTime -> {}, unit -> {}, ",
                supplierId, lockKeySpel, lockKey, waitTime, leaseTime, unit);
        LockSupport lockSupport;
        if (StringUtils.isBlank(supplierId)) {
            lockSupport = LockFaceUtil.get(lockKey, waitTime, leaseTime, unit);
        } else {
            lockSupport = LockFaceUtil.get(supplierId, lockKey, waitTime, leaseTime, unit);
        }
        return lockSupport.exec(new NoArgFunction<>() {
            @Override
            @SneakyThrows
            public Object apply() {
                return thisJoinPoint.proceed();
            }
        });
    }
    
    @Override
    public int getOrder() {
        if (lockAdviceOrderProvider != null) {
            return lockAdviceOrderProvider.getOrder();
        }
        return AutoConfigurationConstant.ZOO_LITE_FEATURE_LOCK_ADVICE_ORDER;
    }
    
    public interface LockAdviceOrderProvider extends Ordered {
    
    }
}
