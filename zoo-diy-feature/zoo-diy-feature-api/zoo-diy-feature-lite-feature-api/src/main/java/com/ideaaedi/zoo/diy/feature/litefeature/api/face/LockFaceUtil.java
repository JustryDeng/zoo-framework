package com.ideaaedi.zoo.diy.feature.litefeature.api.face;

import com.ideaaedi.commonds.env.Env;
import com.ideaaedi.commonds.env.RequiredEnv;
import com.ideaaedi.commonds.env.Unit;
import com.ideaaedi.commonds.lock.LockSupport;
import com.ideaaedi.commonds.lock.NotAcquiredLockException;
import com.ideaaedi.zoo.commonbase.zoo_face.Face;
import com.ideaaedi.zoo.diy.feature.litefeature.api.exception.LockSupplierNoSuchException;
import com.ideaaedi.zoo.diy.feature.litefeature.api.exception.LockSupplierNotDefaultException;
import com.ideaaedi.zoo.diy.feature.litefeature.api.lock.LockSupplier;
import com.ideaaedi.zoo.diy.feature.litefeature.api.lock.LockSupplierManager;
import com.ideaaedi.zoo.diy.feature.litefeature.api.properties.ZooLiteFeatureProperties;
import lombok.experimental.UtilityClass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁门面工具类
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@UtilityClass
public final class LockFaceUtil implements Face {
    
    static LockSupplierManager lockSupplierManager;
    
    static ZooLiteFeatureProperties liteFeatureProperties;
    
    /**
     * 获取默认的分布式锁供应器
     * <pre>
     * 你可以通过以下配置来指定默认的分布式锁供应器
     * {@code
     *  zoo:
     *    lite-feature:
     *       lock:
     *         # 指定分布式锁的默认实现器
     *         default-supplier-id: xxx
     * }
     * </pre>
     *
     * @return 默认的分布式锁供应器
     */
    @Nullable
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static LockSupplier supplier() {
        String supplierId = liteFeatureProperties.getLock().getDefaultSupplierId();
        return lockSupplierManager.find(supplierId);
    }
    
    /**
     * 获取分布式锁供应器管理器
     *
     * @param supplierId 分布式锁供应器id
     *
     * @return 分布式锁供应器管理器
     */
    @Nullable
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static LockSupplier supplier(@Nonnull String supplierId) {
        return lockSupplierManager.find(supplierId);
    }
    
    /**
     * 获取分布式锁供应器管理器
     *
     * @return 分布式锁供应器管理器
     */
    @Nonnull
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static LockSupplierManager manager() {
        return lockSupplierManager;
    }

    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     * <pre>
     *  此方法的默认缺失配置：
     *   1.锁等待时长：0，不等待（获取不到直接抛{@link NotAcquiredLockException}）
     *   2.持有锁的最大时长：-1，自动续期
     *   3.时间单位：秒
     * </pre>
     *
     * @see #supplier()
     * @see #get(LockSupplier, String)
     *
     * @return 分布式锁实现器
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static LockSupport get(@Nonnull String lockKey) {
        LockSupplier lockSupplier = supplier();
        if (lockSupplier == null) {
            throw new LockSupplierNotDefaultException("you could point default lock-supplier by config 'zoo.lite-feature.lock.default-supplier-id=xxx'");
        }
        return get(lockSupplier, lockKey);
    }

    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     * <pre>
     *  此方法的默认缺失配置：
     *   1.持有锁的最大时长：-1，自动续期
     *   2.时间单位：秒
     * </pre>
     *
     * @see #supplier()
     * @see #get(LockSupplier, String, long)
     *
     * @return 分布式锁实现器
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static LockSupport get(@Nonnull String lockKey, long waitTime) {
        LockSupplier lockSupplier = supplier();
        if (lockSupplier == null) {
            throw new LockSupplierNotDefaultException("you could point default lock-supplier by config 'zoo.lite-feature.lock.default-supplier-id=xxx'");
        }
        return get(lockSupplier, lockKey, waitTime);
    }

    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     * <pre>
     *  此方法的默认缺失配置：
     *   1.时间单位：秒
     * </pre>
     *
     * @see #supplier()
     * @see #get(LockSupplier, String, long, long)
     *
     * @return 分布式锁实现器
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static LockSupport get(@Nonnull String lockKey, long waitTime, long leaseTime) {
        LockSupplier lockSupplier = supplier();
        if (lockSupplier == null) {
            throw new LockSupplierNotDefaultException("you could point default lock-supplier by config 'zoo.lite-feature.lock.default-supplier-id=xxx'");
        }
        return get(lockSupplier, lockKey, waitTime, leaseTime);
    }

    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     *
     * @see #supplier()
     * @see #get(LockSupplier, String, long, long, TimeUnit)
     *
     * @return 分布式锁实现器
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static LockSupport get(@Nonnull String lockKey, long waitTime, long leaseTime, @Nonnull TimeUnit timeUnit) {
        LockSupplier lockSupplier = supplier();
        if (lockSupplier == null) {
            throw new LockSupplierNotDefaultException("you could point default lock-supplier by config 'zoo.lite-feature.lock.default-supplier-id=xxx'");
        }
        return get(lockSupplier, lockKey, waitTime, leaseTime, timeUnit);
    }
    
    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     * <pre>
     *  此方法的默认缺失配置：
     *   1.锁等待时长：0，不等待（获取不到直接抛{@link NotAcquiredLockException}）
     *   2.持有锁的最大时长：-1，自动续期
     *   3.时间单位：秒
     * </pre>
     *
     * @see #supplier(String)
     * @see #get(LockSupplier, String)
     *
     * @return 分布式锁实现器
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static LockSupport get(@Nonnull String supplierId, @Nonnull String lockKey) {
        LockSupplier lockSupplier = supplier(supplierId);
        if (lockSupplier == null) {
            throw new LockSupplierNoSuchException("not found lock supplier whose id is '" + supplierId + "'.");
        }
        return get(lockSupplier, lockKey);
    }
    
    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     * <pre>
     *  此方法的默认缺失配置：
     *   1.持有锁的最大时长：-1，自动续期
     *   2.时间单位：秒
     * </pre>
     *
     * @see #supplier(String)
     * @see #get(LockSupplier, String, long)
     *
     * @return 分布式锁实现器
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static LockSupport get(@Nonnull String supplierId, @Nonnull String lockKey, long waitTime) {
        LockSupplier lockSupplier = supplier(supplierId);
        if (lockSupplier == null) {
            throw new LockSupplierNoSuchException("not found lock supplier whose id is '" + supplierId + "'.");
        }
        return get(lockSupplier, lockKey, waitTime);
    }
    
    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     * <pre>
     *  此方法的默认缺失配置：
     *   1.时间单位：秒
     * </pre>
     *
     * @see #supplier(String)
     * @see #get(LockSupplier, String, long, long)
     *
     * @return 分布式锁实现器
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static LockSupport get(@Nonnull String supplierId, @Nonnull String lockKey,
                                  long waitTime, long leaseTime) {
        LockSupplier lockSupplier = supplier(supplierId);
        if (lockSupplier == null) {
            throw new LockSupplierNoSuchException("not found lock supplier whose id is '" + supplierId + "'.");
        }
        return get(lockSupplier, lockKey, waitTime, leaseTime);
    }
    
    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     *
     * @see #supplier(String)
     * @see #get(LockSupplier, String, long, long, TimeUnit)
     *
     * @return 分布式锁实现器
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static LockSupport get(@Nonnull String supplierId, @Nonnull String lockKey,
                                  long waitTime, long leaseTime, @Nonnull TimeUnit timeUnit) {
        LockSupplier lockSupplier = supplier(supplierId);
        if (lockSupplier == null) {
            throw new LockSupplierNoSuchException("not found lock supplier whose id is '" + supplierId + "'.");
        }
        return get(lockSupplier, lockKey, waitTime, leaseTime, timeUnit);
    }
    
    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     * <pre>
     *  此方法的默认缺失配置：
     *   1.锁等待时长：0，不等待（获取不到直接抛{@link NotAcquiredLockException}）
     *   2.持有锁的最大时长：-1，自动续期
     *   3.时间单位：秒
     * </pre>
     *
     * @param lockSupplier 锁操作器供应器
     * @param lockKey 锁唯一key
     *
     * @return 分布式锁实现器
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static LockSupport get(@Nonnull LockSupplier lockSupplier, @Nonnull String lockKey) {
        return lockSupplier.get(lockKey);
    }
    
    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     * <pre>
     *  此方法的默认缺失配置：
     *   1.持有锁的最大时长：-1，自动续期
     *   2.时间单位：秒
     * </pre>
     *
     * @param lockSupplier 锁操作器供应器
     * @param lockKey 锁唯一key
     * @param waitTime 锁等待时长 （取值范围：waitTime >= 0）
     *                  <br />0：表示不等待，没获取到将直接抛出{@link NotAcquiredLockException}
     *
     * @return 分布式锁实现器
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static LockSupport get(@Nonnull LockSupplier lockSupplier, @Nonnull String lockKey, long waitTime) {
        return lockSupplier.get(lockKey, waitTime);
    }
    
    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     * <pre>
     *  此方法的默认缺失配置：
     *   1.时间单位：秒
     * </pre>
     *
     * @param lockSupplier 锁操作器供应器
     * @param lockKey 锁唯一key
     * @param waitTime 锁等待时长 （取值范围：waitTime >= 0）
     *                  <br />0：表示不等待，没获取到将直接抛出{@link NotAcquiredLockException}
     * @param leaseTime 持有锁的最大时长 （取值范围：leaseTime > 0 || leaseTime == -1）
     *                  <br />-1：表示自动续期
     *
     * @return 分布式锁实现器
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static LockSupport get(@Nonnull LockSupplier lockSupplier, @Nonnull String lockKey, long waitTime, long leaseTime) {
        return lockSupplier.get(lockKey, waitTime, leaseTime);
    }
    
    /**
     * 配置并获取锁操作器
     * <p>
     * 注：此方法只是进行锁配置，并不会去尝试获取锁，只有在调用返回对象{@link LockSupport}的方法时才会触发锁获取
     * </p>
     *
     * @param lockSupplier 锁操作器供应器
     * @param lockKey 锁唯一key
     * @param waitTime 锁等待时长 （取值范围：waitTime >= 0）
     *                  <br />0：表示不等待，没获取到将直接抛出{@link NotAcquiredLockException}
     * @param leaseTime 持有锁的最大时长 （取值范围：leaseTime > 0 || leaseTime == -1）
     *                  <br />-1：表示自动续期
     * @param timeUnit waitTime、leaseTime的时长单位
     *
     * @return 分布式锁实现器
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static LockSupport get(@Nonnull LockSupplier lockSupplier, @Nonnull String lockKey,
                                  long waitTime, long leaseTime, @Nonnull TimeUnit timeUnit) {
        return lockSupplier.get(lockKey, waitTime, leaseTime, timeUnit);
    }
    
}
