package com.ideaaedi.zoo.diy.feature.expression.engine.impl.qlexpress.core;


import com.ideaaedi.zoo.diy.feature.expression.engine.impl.qlexpress.properties.ZooQLExpressProperties;
import com.ql.util.express.config.QLExpressRunStrategy;
import com.ql.util.express.config.QLExpressTimer;
import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * 支持spring bean的上下文
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class QLExpressInitConfig implements SmartInitializingSingleton {
    
    private final ZooQLExpressProperties zooQLExpressProperties;
    
    public QLExpressInitConfig(ZooQLExpressProperties zooQLExpressProperties) {
        this.zooQLExpressProperties = zooQLExpressProperties;
    }
    
    @Override
    public void afterSingletonsInstantiated() {
        QLExpressTimer.setTimeout(zooQLExpressProperties.getGlobalTimeout());
        QLExpressRunStrategy.checkArrLength(zooQLExpressProperties.getMaxArrLength());
    }
}