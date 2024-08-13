package com.ideaaedi.zoo.diy.feature.litefeature.api.lock;

import com.ideaaedi.zoo.diy.feature.litefeature.api.exception.LockSupplierAlreadyExistException;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class DefaultLockSupplierManager implements LockSupplierManager {
    
    private static final Map<String, LockSupplier> LOCK_SUPPLIER_HOLDER = new ConcurrentHashMap<>(16);
    
    public DefaultLockSupplierManager(List<LockSupplier> lockSupportList) {
        if (!CollectionUtils.isEmpty(lockSupportList)) {
            add(lockSupportList.toArray(new LockSupplier[0]));
        }
    }
    
    @Nullable
    @Override
    public LockSupplier find(@Nullable String supplierId) {
        if (supplierId == null) {
            return null;
        }
        return LOCK_SUPPLIER_HOLDER.get(supplierId);
    }
    
    @Nullable
    @Override
    public LockSupplier findFirst(@Nullable String... supplierIds) {
        if (supplierIds == null) {
            return null;
        }
        for (String senderId : supplierIds) {
            LockSupplier lockSupplier = LOCK_SUPPLIER_HOLDER.get(senderId);
            if (lockSupplier != null) {
                return lockSupplier;
            }
        }
        return null;
    }
    
    @Nonnull
    @Override
    public Map<String, LockSupplier> findAll() {
        return new HashMap<>(LOCK_SUPPLIER_HOLDER);
    }
    
    @Override
    public boolean exist(@Nullable String supplierId) {
        return LOCK_SUPPLIER_HOLDER.containsKey(supplierId);
    }
    
    @Override
    public void add(@Nullable LockSupplier lockSupplier) throws LockSupplierAlreadyExistException {
        if (lockSupplier == null) {
            return;
        }
        String id = lockSupplier.id();
        boolean exist = LOCK_SUPPLIER_HOLDER.containsKey(id);
        if (exist) {
            throw new LockSupplierAlreadyExistException(String.format("senderId[%s] already exist.", id));
        }
        LOCK_SUPPLIER_HOLDER.put(id, lockSupplier);
    }
    
    @Override
    public void add(@Nullable LockSupplier... lockSuppliers) throws LockSupplierAlreadyExistException {
        if (lockSuppliers == null) {
            return;
        }
        for (LockSupplier lockSupplier : lockSuppliers) {
            String id = lockSupplier.id();
            boolean exist = LOCK_SUPPLIER_HOLDER.containsKey(id);
            if (exist) {
                throw new LockSupplierAlreadyExistException(String.format("senderId[%s] already exist.", id));
            }
            LOCK_SUPPLIER_HOLDER.put(id, lockSupplier);
        }
    }
    
    @Override
    public void remove(@Nullable String supplierId) {
        if (supplierId == null) {
            return;
        }
        LOCK_SUPPLIER_HOLDER.remove(supplierId);
    }
    
    @Override
    public void remove(@Nullable String... supplierIds) {
        if (supplierIds == null) {
            return;
        }
        for (String senderId : supplierIds) {
            LOCK_SUPPLIER_HOLDER.remove(senderId);
        }
    }
}
