package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 企业微信登录 - 通过用户手机号信息 req
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class LoginTokenQyWechatByPhoneReqVO {
    
    @NotBlank(message = "sessionKey cannot be blank.")
    @Schema(description = "会话key")
    private String sessionKey;
    
    @NotBlank(message = "userId cannot be blank.")
    @Schema(description = "企业微信用户id")
    private String userId;
    
    @NotBlank(message = "encryptedData cannot be blank.")
    @Schema(description = "企业微信的加密数据")
    private String encryptedData;
    
    @NotBlank(message = "iv cannot be blank.")
    @Schema(description = "企业微信加密数据的向量")
    private String iv;
    
}
