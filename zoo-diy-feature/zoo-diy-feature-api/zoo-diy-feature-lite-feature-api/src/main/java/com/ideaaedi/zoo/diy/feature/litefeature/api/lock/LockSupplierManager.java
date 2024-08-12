package com.ideaaedi.zoo.diy.feature.litefeature.api.lock;

import com.ideaaedi.zoo.diy.feature.litefeature.api.exception.LockSupplierAlreadyExistException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * MultiLockSupplier管理器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface LockSupplierManager {
    
    /**
     * 获取指定分布式锁供应器
     *
     * @param supplierId 分布式锁供应器id
     *
     * @return 分布式锁供应器
     */
    @Nullable
    LockSupplier find(@Nullable String supplierId);
    
    /**
     * 按顺序获取第一个存在的分布式锁供应器
     *
     * @param supplierIds 分布式锁供应器ids
     *
     * @return 第一个存在的分布式锁供应器
     */
    @Nullable
    LockSupplier findFirst(@Nullable String... supplierIds);
    
    /**
     * 获取所有分布式锁供应器
     *
     * @return key-分布式锁供应器id, value-分布式锁供应器
     */
    @Nonnull
    Map<String, LockSupplier> findAll();
    
    /**
     * 判断指定分布式锁供应器是否存在
     *
     * @param supplierId 分布式锁供应器id
     *
     * @return 分布式锁供应器是否存在
     */
    boolean exist(@Nullable String supplierId);

    /**
     * 添加分布式锁供应器
     * <pre>
     * 注：这里添加只会往当前节点中添加；在分布式环境下，需要外部确保往所有节点都添加了
     * 注：建议项目启动时调用
     * </pre>
     *
     * @param lockSupplier 分布式锁供应器
     * @throws LockSupplierAlreadyExistException 分布式锁供应器已存在时抛出
     */
    void add(@Nullable LockSupplier lockSupplier) throws LockSupplierAlreadyExistException;
    
    /**
     * 添加分布式锁供应器
     * <pre>
     * 注：这里添加只会往当前节点中添加；在分布式环境下，需要外部确保往所有节点都添加了
     * 注：建议项目启动时调用
     * </pre>
     *
     * @param lockSuppliers 分布式锁供应器
     * @throws LockSupplierAlreadyExistException 分布式锁供应器已存在时抛出
     */
    void add(@Nullable LockSupplier... lockSuppliers) throws LockSupplierAlreadyExistException;
    
    /**
     * 移除分布式锁供应器
     * <pre>
     * 注：这里移除只会从当前节点中移除；在分布式环境下，需要外部确保所有节点都移除了
     * </pre>
     *
     * @param supplierId 分布式锁供应器id
     */
    void remove(@Nullable String supplierId);

    /**
     * 移除分布式锁供应器
     * <pre>
     * 注：这里移除只会从当前节点中移除；在分布式环境下，需要外部确保所有节点都移除了
     * </pre>
     *
     * @param supplierIds 分布式锁供应器ids
     */
    void remove(@Nullable String... supplierIds);
}
