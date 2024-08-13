package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 微信小程序登录 req
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class LoginTokenWechatReqVO {
    
    /** 微信openId授权码 */
    @NotBlank(message = "openIdAuthCode cannot be blank.")
    @Schema(description = "微信openId授权码")
    private String openIdAuthCode;
    
    /** 微信手机号动态令牌 */
    @Schema(description = "微信手机号动态令牌")
    private String phoneAuthCode;
}
