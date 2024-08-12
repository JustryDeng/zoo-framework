package com.ideaaedi.zoo.commonbase.enums.sys;

import com.ideaaedi.zoo.commonbase.entity.sys.po.SysThirdLoginCredentialPO;
import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;
import lombok.Getter;

/**
 * <p>
 * 系统-第三方登录类型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public enum ThirdLoginTypeEnum implements EnumDescriptor {
    
    /**
     * 客户端ak/sk登录
     */
    QY_WECHAT("企业微信", "thirdUserId的值为：企业微信userId值"),
    WECHAT("微信", "thirdUserId的值为：微信openId值");
    
    private final String desc;
    
    /**
     * 对{@link SysThirdLoginCredentialPO#getThirdUserId()}字段的说明
     */
    private final String thirdUserIdDesc;
    
    ThirdLoginTypeEnum(String desc, String thirdUserIdDesc) {
        this.desc = desc;
        this.thirdUserIdDesc = thirdUserIdDesc;
    }
    
    @Override
    public String obtainDescription() {
        return this.desc;
    }
}
