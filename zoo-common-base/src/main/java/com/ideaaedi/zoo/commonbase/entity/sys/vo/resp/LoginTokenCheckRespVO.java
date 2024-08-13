package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.ideaaedi.zoo.commonbase.enums.sys.UserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 校验token resp
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class LoginTokenCheckRespVO {
    
    /** 账号 */
    @Schema(description = "accountNo")
    private String accountNo;
    
    /** 用户id */
    @Schema(description = "用户id")
    private Long userId;
    
    /** 用户类型 */
    @Schema(description = "用户类型")
    private UserTypeEnum userType;
    
    /** token是否处于激活状态 */
    @Schema(description = "token是否处于激活状态")
    private Boolean active;
}
