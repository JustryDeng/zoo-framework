package com.ideaaedi.zoo.commonbase.entity.sys.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统-职位表 po模型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@TableName("sys_post")
@Schema(description = "系统-职位表")
public class SysPostPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId("id")
    private Long id;

    @Schema(description = "租户")
    @TableField("tenant")
    private String tenant;

    @Schema(description = "职位编码")
    @TableField("code")
    private String code;

    @Schema(description = "职位名称")
    @TableField("name")
    private String name;

    @Schema(description = "职级")
    @TableField("grade")
    private Integer grade;

    @Schema(description = "排序（同职级，sort越小越排前面）")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "状态(1-正常；2-禁用)")
    @TableField("state")
    private Integer state;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;

    @Schema(description = "是否已删除(0-未删除；1-已删除)")
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

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