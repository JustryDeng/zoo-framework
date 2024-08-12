package com.ideaaedi.zoo.commonbase.enums.sys;


import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;

/**
 * 登录场景枚举
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public enum LoginSceneEnum implements EnumDescriptor {
    
    CLIENT_AK_SK("客户端ak/sk登录"),
    
    USERNAME("账号密码登录"),
    
    WECHAT_APPLET("小程序登录"),
    
    QY_WECHAT_APPLET("企业微信登录");
    
    private final String desc;
    
    LoginSceneEnum(String desc) {
        this.desc = desc;
    }
    
    @Override
    public String obtainDescription() {
        return this.desc;
    }
}
