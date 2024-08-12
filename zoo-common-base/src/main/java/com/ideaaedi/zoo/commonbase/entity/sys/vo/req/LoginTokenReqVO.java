package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录获取token req
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginTokenReqVO extends BaseDTO {
    
    /** 账号 */
    @NotBlank(message = "账号不能为空")
    @Schema(description = "账号")
    private String accountNo;
    
    /** 密码 */
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码")
    private String password;

}
