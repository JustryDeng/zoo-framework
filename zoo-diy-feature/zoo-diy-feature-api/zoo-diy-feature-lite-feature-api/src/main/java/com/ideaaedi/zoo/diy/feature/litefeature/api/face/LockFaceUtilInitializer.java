package com.ideaaedi.zoo.diy.feature.litefeature.api.face;

import com.ideaaedi.zoo.diy.feature.litefeature.api.lock.LockSupplierManager;
import com.ideaaedi.zoo.diy.feature.litefeature.api.properties.ZooLiteFeatureProperties;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class LockFaceUtilInitializer {
    
    public LockFaceUtilInitializer(LockSupplierManager lockSupplierManager, ZooLiteFeatureProperties liteFeatureProperties) {
        init(lockSupplierManager, liteFeatureProperties);
    }
    
    protected void init(LockSupplierManager lockSupplierManager, ZooLiteFeatureProperties liteFeatureProperties) {
        LockFaceUtil.lockSupplierManager = lockSupplierManager;
        LockFaceUtil.liteFeatureProperties = liteFeatureProperties;
    }
}
