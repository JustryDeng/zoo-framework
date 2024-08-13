package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import com.ideaaedi.zoo.commonbase.entity.sys.dto.DeptIdPostIdDTO;
import com.ideaaedi.zoo.commonbase.enums.sys.CertTypeEnum;
import com.ideaaedi.zoo.commonbase.enums.sys.UserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统-用户表 update req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-用户表 update req")
public class SysUserUpdateReqVO extends BaseDTO {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @Schema(description = "id")
    private Long id;

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
     * 用户登录密码
     */
    @Schema(description = "用户登录密码")
    private String password;
    
    /**
     * 员工姓名
     */
    @Schema(description = "用户姓名")
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
     * 头像(附件id)
     */
    @Schema(description = "头像(附件id)")
    private Long avatarId;

    /**
     * 账号状态(1-正常；2-禁用)
     */
    @Schema(description = "账号状态(1-正常；2-禁用)")
    private Integer state;
    
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
     * 角色id集合（null表示不作修改）
     */
    @Schema(description = "角色id")
    private Set<Long> roleIds;
    
    /**
     * 部门职位信息（null表示不作修改）
     */
    @Schema(description = "部门和职位")
    private List<DeptIdPostIdDTO> deptPostList;
}