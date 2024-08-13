package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ideaaedi.zoo.commonbase.enums.sys.CertTypeEnum;
import com.ideaaedi.zoo.commonbase.enums.sys.UserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统-用户表 list resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-用户表 list resp")
public class SysUserListRespVO {

    /**
     * id
     */
    @Schema(description = "id")
    @ExcelProperty("id")
    private Long id;
    
    /**
     * 租户编码
     */
    @Schema(description = "租户编码")
    @ExcelProperty("租户编码")
    private String tenant;
    
    /**
     * 所属业务租户的名称
     */
    @Schema(description = "所属业务租户的名称")
    @ExcelProperty("所属业务租户的名称")
    private String tenantName;
    
    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    @ExcelProperty("用户类型")
    private UserTypeEnum type;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    @ExcelProperty("用户账号")
    private String accountNo;

    /**
     * 用户姓名
     */
    @Schema(description = "用户姓名")
    @ExcelProperty("用户姓名")
    private String name;

    /**
     * 用户生日
     */
    @Schema(description = "用户生日")
    @ExcelProperty("用户生日")
    private String birthday;

    /**
     * 用户性别（1-男；2-女; 0-未知）
     */
    @Schema(description = "用户性别（1-男；2-女; 0-未知）")
    @ExcelProperty("用户性别（1-男；2-女; 0-未知）")
    private Integer gender;

    /**
     * 头像(附件id)
     */
    @Schema(description = "头像(附件id)")
    @ExcelProperty("头像(附件id)")
    private Long avatarId;
    
    /**
     * 头像url
     */
    @Schema(description = "头像url")
    private String avatarUrl;
    
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
    @ExcelProperty("账号状态(1-正常；2-禁用)")
    private Integer state;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @ExcelProperty("修改时间")
    private LocalDateTime updatedAt;
    
    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private Integer deptId;
    
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