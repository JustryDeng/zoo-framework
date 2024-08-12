package com.ideaaedi.zoo.diy.feature.litefeature.api.lock;

import com.ideaaedi.commonds.lock.LockSupport;
import com.ideaaedi.commonds.lock.NotAcquiredLockException;
import com.ideaaedi.zoo.diy.feature.litefeature.api.exception.LockSupplierUnsupportException;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁实现供应器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface LockSupplier {
    
    /**
     * 等待获取锁的最大时长
     */
    long DEFAULT_WAIT_TIME = 0L;
    
    /**
     * 释放锁的最大时长
     */
    long DEFAULT_LEASE_TIME = -1L;
    
    /**
     * waitTime和leaseTime的时间单位
     */
    TimeUnit DEFAULT_UNIT = TimeUnit.SECONDS;
    
    /**
     * 分布式锁供应器唯一id
     *
     * @return 分布式锁供应器唯一id
     */
    @Nonnull
    String id();
    
    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     *
     * <pre>
     *  此方法的默认缺失配置：
     *   1.锁等待时长：0，不等待（获取不到直接抛{@link NotAcquiredLockException}）
     *   2.持有锁的最大时长：-1，自动续期
     *   3.时间单位：秒
     * </pre>
     *
     * @see #get(String, long, long, TimeUnit)
     */
    default LockSupport get(@Nonnull String lockKey) {
        return get(lockKey, DEFAULT_WAIT_TIME, DEFAULT_LEASE_TIME, DEFAULT_UNIT);
    }
    
    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     *
     * <pre>
     *  此方法的默认缺失配置：
     *   1.持有锁的最大时长：-1，自动续期
     *   2.时间单位：秒
     * </pre>
     * @see #get(String, long, long, TimeUnit)
     */
    default LockSupport get(@Nonnull String lockKey, long waitTime) {
        return get(lockKey, waitTime, DEFAULT_LEASE_TIME, DEFAULT_UNIT);
    }
    
    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     *
     * <pre>
     *  此方法的默认缺失配置：
     *   1.时间单位：秒
     * </pre>
     * @see #get(String, long, long, TimeUnit)
     */
    default LockSupport get(@Nonnull String lockKey, long waitTime, long leaseTime) {
        return get(lockKey, waitTime, leaseTime, DEFAULT_UNIT);
    }
    
    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     *
     * @param lockKey 锁唯一key
     * @param waitTime 锁等待时长 （取值范围：waitTime >= 0）
     *                  <br />0：表示不等待，没获取到将直接抛出{@link NotAcquiredLockException}
     * @param leaseTime 持有锁的最大时长 （取值范围：leaseTime > 0 || leaseTime == -1）
     *                  <br />-1：表示自动续期
     * @param timeUnit waitTime、leaseTime的时长单位
     *
     * @return 分布式锁实现器
     */
    default LockSupport get(@Nonnull String lockKey, long waitTime, long leaseTime, @Nonnull TimeUnit timeUnit) {
        validateWaitTime(waitTime);
        validateLeaseTime(leaseTime);
        throw new LockSupplierUnsupportException();
    }
    
    /**
     * 校验waitTime是否合法
     */
    default void validateWaitTime(long waitTime) {
        boolean waitTimeLegal = waitTime >= 0;
        if (!waitTimeLegal) {
            throw  new IllegalArgumentException("waitTimeLegal should be 'waitTime >= 0'");
        }
    }
    
    /**
     * 校验leaseTime是否合法
     */
    default void validateLeaseTime(long leaseTime) {
        boolean leaseTimeLegal = leaseTime > 0 || leaseTime == -1;
        if (!leaseTimeLegal) {
            throw  new IllegalArgumentException("leaseTime should be 'leaseTime > 0 || leaseTime == -1'");
        }
    }
    
}