package com.ideaaedi.zoo.commonbase.entity.message.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 消息-模板表 list req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "消息-模板表 list req")
public class MsgTemplateListReqVO extends BasePageDTO {

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
     * 模版编码
     */
    @Schema(description = "模版编码")
    private String code;

    /**
     * 模版名称
     */
    @Schema(description = "模版名称")
    private String name;

    /**
     * 消息模板（支持占位符解析）
     */
    @Schema(description = "消息模板（支持占位符解析）")
    private String template;

    /**
     * 需要的参数信息数组（数组中的json对象包含这些字段：name-参数名；type-参数类型；desc-参数说明；required-是否必须）
     */
    @Schema(description = "需要的参数信息数组（数组中的json对象包含这些字段：name-参数名；type-参数类型；desc-参数说明；required-是否必须）")
    private String requiredVarInfo;

    /**
     * 模板状态（-1-草稿态；1-可用；2-禁用）
     */
    @Schema(description = "模板状态（-1-草稿态；1-可用；2-禁用）")
    private Integer state;

    /**
     * 是否已删除(0-未删除；1-已删除)
     */
    @Schema(description = "是否已删除(0-未删除；1-已删除)")
    private Integer deleted;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private Long updatedBy;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updatedAt;

}