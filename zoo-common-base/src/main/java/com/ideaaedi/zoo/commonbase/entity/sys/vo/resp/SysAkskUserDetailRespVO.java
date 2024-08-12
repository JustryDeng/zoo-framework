package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.ideaaedi.zoo.commonbase.enums.sys.UserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 系统-aksk用户表 detail resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-aksk用户表 detail resp")
public class SysAkskUserDetailRespVO {

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;
    
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long userId;
    
    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private UserTypeEnum type;
    
    /**
     * 租户编码
     */
    @Schema(description = "租户编码")
    private String tenant;
    
    /**
     * 所属业务租户的名称
     */
    @Schema(description = "所属业务租户的名称")
    private String tenantName;
    
    /**
     * ak
     */
    @Schema(description = "ak")
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
    
    /**
     * 用户拥有的角色
     */
    @Schema(description = "用户拥有的角色")
    private List<Long> roleIds;
}