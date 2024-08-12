package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 企业微信登录 - 通过用户授权码code req
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class LoginTokenQyWechatByCodeReqVO {
    
    /** 企业微信登录授权码 */
    @NotBlank(message = "qyLoginCode cannot be blank.")
    @Schema(description = "企业微信登录授权码")
    private String qyLoginCode;
    
}
