package com.ideaaedi.zoo.diy.feature.msg.api.sms;

import com.ideaaedi.zoo.diy.feature.msg.api.properties.ZooMsgProperties;
import com.ideaaedi.zoo.diy.feature.msg.api.sms.core.SmsSenderManager;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class SmsFaceUtilInitializer {
    
    public SmsFaceUtilInitializer(SmsSenderManager smsSenderManager, ZooMsgProperties zooMsgProperties) {
        init(smsSenderManager, zooMsgProperties);
    }
    
    protected void init(SmsSenderManager smsSenderManager, ZooMsgProperties zooMsgProperties) {
        SmsFaceUtil.smsSenderManager = smsSenderManager;
        SmsFaceUtil.zooMsgProperties = zooMsgProperties;
    }
}
