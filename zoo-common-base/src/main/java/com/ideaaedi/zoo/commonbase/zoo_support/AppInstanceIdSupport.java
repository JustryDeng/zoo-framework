package com.ideaaedi.zoo.commonbase.zoo_support;

import java.util.UUID;

/**
 * 扩展
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface AppInstanceIdSupport {
    
    String APP_INSTANCE_ID = UUID.randomUUID().toString().replaceAll("-", "");
}
