package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.ideaaedi.zoo.commonbase.enums.sys.UserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 登录获取token resp
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class LoginTokenAkSkRespVO {
    
    /** token */
    @Schema(description = "token")
    private String token;
    
    /** 刷新token */
    @Schema(description = "刷新token")
    private String refreshToken;
    
    /** 用户类型（TEMP_E-长期临时用户，TEMP_T-短期临时用户，NORMAL-正常用户, CLIENT_AK_SK-客户端ak/sk用户） */
    @Schema(description = "用户类型")
    private UserTypeEnum userType;
}
