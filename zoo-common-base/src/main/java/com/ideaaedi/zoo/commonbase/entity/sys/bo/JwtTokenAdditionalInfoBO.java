package com.ideaaedi.zoo.commonbase.entity.sys.bo;

import com.ideaaedi.zoo.commonbase.enums.sys.LoginSceneEnum;
import com.ideaaedi.zoo.commonbase.enums.sys.UserTypeEnum;
import lombok.Data;

/**
 * jwt附加信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class JwtTokenAdditionalInfoBO {
    
    /** 用户id */
    private Long userId;
    
    /** 登录场景 */
    private LoginSceneEnum loginScene;
    
    /** 用户类型 */
    private UserTypeEnum userType;
    
}
