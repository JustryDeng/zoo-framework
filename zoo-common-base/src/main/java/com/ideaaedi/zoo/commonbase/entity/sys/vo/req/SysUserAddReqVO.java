package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import com.ideaaedi.zoo.commonbase.entity.sys.dto.DeptIdPostIdDTO;
import com.ideaaedi.zoo.commonbase.enums.sys.CertTypeEnum;
import com.ideaaedi.zoo.commonbase.enums.sys.UserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统-用户表 add req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-用户表 add req")
public class SysUserAddReqVO extends BaseDTO {

    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;
    
    /**
     * 用户类型
     */
    @NotNull(message = "用户类型不能为空")
    @Schema(description = "用户类型")
    private UserTypeEnum type;
    
    /**
     * 头像(附件id)
     */
    @Schema(description = "头像(附件id)")
    private Long avatarId;
    
    /**
     * 用户登录账号(不能含有特殊字符)
     */
    @Schema(description = "用户登录账号(不能含有特殊字符)")
    private String accountNo;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    @Schema(description = "用户登录密码")
    private String password;

    /**
     * 用户姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Schema(description = "员工姓名")
    private String name;

    /**
     * 用户生日
     */
    @Schema(description = "用户生日")
    private String birthday;

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
     * 角色id集合
     */
    @Schema(description = "角色id")
    private Set<Long> roleIds;
    
    /**
     * 部门职位信息（注：用户将主归属到第一个部门下）
     */
    @Schema(description = "部门和职位（注：用户将主归属到第一个部门下）")
    private List<DeptIdPostIdDTO> deptPostList;
}