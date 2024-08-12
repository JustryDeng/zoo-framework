package com.ideaaedi.zoo.commonbase.entity.sys.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ideaaedi.zoo.commonbase.enums.sys.ApiTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统-api资源表 po模型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@TableName("sys_api_resource")
@Schema(description = "系统-api资源表")
public class SysApiResourcePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId("id")
    private Long id;

    @Schema(description = "类型（PUBLIC-公开的（访问无需token）；PRIVATE-私有的（访问需要token）；ANT_MATCH-ant匹配的api）")
    @TableField("type")
    private ApiTypeEnum type;

    @Schema(description = "资源名")
    @TableField("name")
    private String name;

    @Schema(description = "资源路径")
    @TableField("path")
    private String path;

    @Schema(description = "请求该资源所需要的方法(多个之间使用逗号分割)")
    @TableField("request_method")
    private String requestMethod;

    @Schema(description = "所属微服务")
    @TableField("micro_service")
    private String microService;

    @Schema(description = "资源备注说明")
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
    
    /**
     * ApiResourceRefresher使用。 数据库中是否存在当前api
     */
    @TableField(exist = false)
    private Boolean existCurrApi;
}