package com.ideaaedi.zoo.commonbase.entity.message.po;

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
 * 消息-交互方表 po模型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@TableName("msg_from_to")
@Schema(description = "消息-交互方表")
public class MsgFromToPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId("id")
    private Long id;

    @Schema(description = "租户")
    @TableField("tenant")
    private String tenant;

    @Schema(description = "年月日")
    @TableField("yyyy_mm_dd")
    private String yyyyMmDd;

    @Schema(description = "发送方类型")
    @TableField("from_type")
    private String fromType;

    @Schema(description = "发送方用户id")
    @TableField("from_user_id")
    private Long fromUserId;

    @Schema(description = "接收放类型")
    @TableField("to_type")
    private String toType;

    @Schema(description = "接收方为：此用户的手机号")
    @TableField("to_user_phone")
    private Long toUserPhone;

    @Schema(description = "接收方：此电话号码")
    @TableField("to_phone")
    private String toPhone;

    @Schema(description = "交互状态（-1-已失效；0-待发送；1-已发送，发送成功；2-已发送，发送失败；3-已发送，结果未知）")
    @TableField("state")
    private Integer state;

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