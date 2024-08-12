package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 刷新token req
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2022/9/6 19:42:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRefreshTokenReqVO extends BaseDTO {
    
    /** 刷新token的凭证 */
    @NotBlank(message = "refreshToken不能为空")
    @Schema(description = "刷新token的凭证")
    private String refreshToken;
    
}
