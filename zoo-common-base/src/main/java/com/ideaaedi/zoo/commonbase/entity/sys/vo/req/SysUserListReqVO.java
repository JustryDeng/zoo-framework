package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BasePageDTO;
import com.ideaaedi.zoo.commonbase.enums.sys.CertTypeEnum;
import com.ideaaedi.zoo.commonbase.enums.sys.UserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/**
 * <p>
 * 系统-用户表 list req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-用户表 list req")
public class SysUserListReqVO extends BasePageDTO {
    
    /**
     * id集合
     */
    @Schema(description = "id集合", hidden = true)
    private Collection<Long> idColl;
    
    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;
    
    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private UserTypeEnum type;
    
    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String accountNo;
    
    /**
     * 用户姓名
     */
    @Schema(description = "用户姓名")
    private String name;
    
    /**
     * 用户性别（1-男；2-女; 0-未知）
     */
    @Schema(description = "用户性别（1-男；2-女; 0-未知）")
    private Integer gender;
    
    /**
     * 用户手机号
     */
    @Schema(description = "用户手机号")
    private String phone;
    
    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    private String email;
    
    /**
     * 用户证件类型
     */
    @Schema(description = "用户证件类型")
    private CertTypeEnum certType;
    
    /**
     * 用户证件号
     */
    @Schema(description = "用户证件号")
    private String certNo;
    
    /**
     * 账号状态(1-正常；2-禁用)
     */
    @Schema(description = "账号状态(1-正常；2-禁用)")
    private Integer state;
    
    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private Long deptId;
    
    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;
    
    /**
     * 职位id
     */
    @Schema(description = "职位id")
    private Long postId;
    
    /**
     * 职位名称
     */
    @Schema(description = "职位名称")
    private String postName;
}