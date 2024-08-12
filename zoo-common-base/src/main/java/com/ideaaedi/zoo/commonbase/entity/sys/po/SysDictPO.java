package com.ideaaedi.zoo.commonbase.entity.sys.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 系统-数据字典表 po模型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@TableName("sys_dict")
@Schema(description = "系统-数据字典表")
public class SysDictPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "租户（为空则表示公共字典）")
    @TableField("tenant")
    private String tenant;

    @Schema(description = "所属字典类型")
    @TableField("dict_type_id")
    private Integer dictTypeId;

    @Schema(description = "字典路径(格式为：类型id_字典id)")
    @TableField("path")
    private String path;

    @Schema(description = "字典编码")
    @TableField("code")
    private String code;

    @Schema(description = "字典名称")
    @TableField("name")
    private String name;

    @Schema(description = "字典值")
    @TableField("`value`")
    private String value;

    @Schema(description = "字典值json扩展")
    @TableField("value_ext")
    private String valueExt;
    
    @Schema(description = "排序（越小越排前面）")
    @TableField("sort")
    private Integer sort;
    
    @Schema(description = "备注")
    @TableField("remark")
    private String remark;

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