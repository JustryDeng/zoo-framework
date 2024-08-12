package com.ideaaedi.zoo.diy.feature.msg.api.mail;

import com.ideaaedi.zoo.diy.feature.msg.api.mail.core.MailSenderManager;
import com.ideaaedi.zoo.diy.feature.msg.api.properties.ZooMsgProperties;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class MailFaceUtilInitializer {
    
    public MailFaceUtilInitializer(MailSenderManager mailSenderManager, ZooMsgProperties zooMsgProperties) {
        init(mailSenderManager, zooMsgProperties);
    }
    
    protected void init(MailSenderManager mailSenderManager, ZooMsgProperties zooMsgProperties) {
        MailFaceUtil.mailSenderManager = mailSenderManager;
        MailFaceUtil.zooMsgProperties = zooMsgProperties;
    }
}
