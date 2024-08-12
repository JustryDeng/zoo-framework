package com.ideaaedi.zoo.commonbase.entity.message.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 消息-认证表 update req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "消息-认证表 update req")
public class MsgAuthInfoUpdateReqVO extends BaseDTO {

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
     * 对接方编码
     */
    @Schema(description = "对接方编码")
    private String code;

    /**
     * 对接名称
     */
    @Schema(description = "对接名称")
    private String name;

    /**
     * auth类型
     */
    @Schema(description = "auth类型")
    private String type;

    /**
     * 状态(1-正常)
     */
    @Schema(description = "状态(1-正常)")
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