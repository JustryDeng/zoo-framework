package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统-aksk用户表 update req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-aksk用户表 update req")
public class SysAkskUserUpdateReqVO extends BaseDTO {

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;
    
    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;
    
    /**
     * ak（将作为用户总表的accountNo，需保证唯一）
     */
    @Schema(description = "ak（将作为用户总表的accountNo，需保证唯一）")
    private String accessKey;
    
    /**
     * sk
     */
    @Schema(description = "sk")
    private String secretKey;
    
    /**
     * 用户组织名
     */
    @Schema(description = "用户组织名")
    private String orgName;
}