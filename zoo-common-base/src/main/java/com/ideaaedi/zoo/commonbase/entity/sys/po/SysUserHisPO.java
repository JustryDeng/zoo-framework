package com.ideaaedi.zoo.commonbase.entity.sys.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ideaaedi.zoo.commonbase.enums.sys.CertTypeEnum;
import com.ideaaedi.zoo.commonbase.enums.sys.UserTypeEnum;
import com.ideaaedi.zoo.commonbase.his.HisIdentify;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统-用户历史表 po模型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@TableName("sys_user_his")
@Schema(description = "系统-用户历史表")
public class SysUserHisPO implements HisIdentify, Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "历史记录id（主键id）")
    @TableId(value = "his_id", type = IdType.AUTO)
    private Integer hisId;

    @Schema(description = "版本（格式为yyyyMMddHHmmss）")
    @TableField("his_version")
    private Long hisVersion;

    @Schema(description = "id")
    @TableField("id")
    private Long id;

    @Schema(description = "租户")
    @TableField("tenant")
    private String tenant;

    @Schema(description = "用户类型")
    @TableField("type")
    private UserTypeEnum type;

    @Schema(description = "用户账号")
    @TableField("account_no")
    private String accountNo;

    @Schema(description = "用户密码")
    @TableField("password")
    private String password;

    @Schema(description = "用户姓名")
    @TableField("name")
    private String name;

    @Schema(description = "用户生日")
    @TableField("birthday")
    private String birthday;

    @Schema(description = "用户性别（0-未知；1-男；2-女）")
    @TableField("gender")
    private Integer gender;

    @Schema(description = "头像(附件id)")
    @TableField("avatar_id")
    private Long avatarId;

    @Schema(description = "用户手机号")
    @TableField("phone")
    private String phone;

    @Schema(description = "用户邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "用户证件类型")
    @TableField("cert_type")
    private CertTypeEnum certType;

    @Schema(description = "用户证件号")
    @TableField("cert_no")
    private String certNo;

    @Schema(description = "账号状态(1-正常；2-禁用)")
    @TableField("state")
    private Integer state;

    @Schema(description = "是否已删除(0-未删除；1-已删除)")
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    @Schema(description = "删除时间(时间戳，单位s；未删除时默认值为0)")
    @TableField("deleted_time")
    private Long deletedTime;

    @Schema(description = "创建人")
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;

    @Schema(description = "创建时间")
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @Schema(description = "修改人")
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;

    @Schema(description = "修改时间")
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

}