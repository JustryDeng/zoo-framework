package com.ideaaedi.zoo.diy.feature.litefeature.impl.cs.lock;

import com.ideaaedi.commonds.lock.LockSupport;
import com.ideaaedi.commonspring.lock.DefaultRedisLockSupport;
import com.ideaaedi.zoo.diy.feature.litefeature.api.lock.LockSupplier;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.SmartInitializingSingleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class CsLockSupplier implements LockSupplier, SmartInitializingSingleton {
    
    public static final String ID = "cs";
    
    private final RedissonClient redissonClient;
    
    public CsLockSupplier(@Nullable RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
    
    @Nonnull
    @Override
    public String id() {
        return ID;
    }
    
    @Override
    public LockSupport get(@Nonnull String lockKey, long waitTime, long leaseTime, @Nonnull TimeUnit timeUnit) {
        validateWaitTime(waitTime);
        validateLeaseTime(leaseTime);
        return new DefaultRedisLockSupport(lockKey, waitTime, leaseTime, timeUnit);
    }
    
    @Override
    public void afterSingletonsInstantiated() {
        if (redissonClient != null && !DefaultRedisLockSupport.hasDefaultRedissonClient()) {
            DefaultRedisLockSupport.initDefaultRedissonClient(redissonClient);
        }
    }
}
