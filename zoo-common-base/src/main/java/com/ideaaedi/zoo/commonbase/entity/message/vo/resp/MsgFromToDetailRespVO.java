package com.ideaaedi.zoo.commonbase.entity.message.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 消息-交互方表 detail resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "消息-交互方表 detail resp")
public class MsgFromToDetailRespVO {

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
     * 年月日
     */
    @Schema(description = "年月日")
    private String yyyyMmDd;

    /**
     * 发送方类型
     */
    @Schema(description = "发送方类型")
    private String fromType;

    /**
     * 发送方用户id
     */
    @Schema(description = "发送方用户id")
    private Long fromUserId;

    /**
     * 接收放类型
     */
    @Schema(description = "接收放类型")
    private String toType;

    /**
     * 接收方为：此用户的手机号
     */
    @Schema(description = "接收方为：此用户的手机号")
    private Long toUserPhone;

    /**
     * 接收方：此电话号码
     */
    @Schema(description = "接收方：此电话号码")
    private String toPhone;

    /**
     * 交互状态（-1-已失效；0-待发送；1-已发送，发送成功；2-已发送，发送失败；3-已发送，结果未知）
     */
    @Schema(description = "交互状态（-1-已失效；0-待发送；1-已发送，发送成功；2-已发送，发送失败；3-已发送，结果未知）")
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