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
import java.util.List;

/**
 * <p>
 * 消息-内容表 po模型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@TableName("msg_message")
@Schema(description = "消息-内容表")
public class MsgMessagePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId("id")
    private Long id;

    @Schema(description = "租户")
    @TableField("tenant")
    private String tenant;

    @Schema(description = "年月日")
    @TableField("yyyy_mm_dd")
    private String yyyyMMdd;

    @Schema(description = "根消息 id（0表示无）")
    @TableField("rid")
    private Long rid;

    @Schema(description = "父消息 id（0表示无）")
    @TableField("pid")
    private Long pid;

    @Schema(description = "消息类型(0-即时消息; 1-定时消息)")
    @TableField("type")
    private Integer type;

    @Schema(description = "消息标题")
    @TableField("title")
    private String title;

    @Schema(description = "消息内容")
    @TableField("content")
    private String content;

    @Schema(description = "消息内容标签")
    @TableField("content_tags")
    private List<String> contentTags;

    @Schema(description = "消息内容格式")
    @TableField("content_type")
    private String contentType;

    @Schema(description = "消息过期时间(单位秒; 0-永不过期)")
    @TableField("time_to_live")
    private Integer timeToLive;

    @Schema(description = "业务端产生消息的时间")
    @TableField("generate_time")
    private LocalDateTime generateTime;

    @Schema(description = "消息中心接收到消息的时间")
    @TableField("receive_time")
    private LocalDateTime receiveTime;

    @Schema(description = "消息中心保存消息的时间")
    @TableField("save_time")
    private LocalDateTime saveTime;

    @Schema(description = "预期消息中心消费消息的时间")
    @TableField("expect_exec_time")
    private List<String> expectExecTime;

    @Schema(description = "消息状态（负数表示执行前状态，正数表示执行后状态：-3-草稿态；-2-已失效；-1-已过期；0-待执行；1-执行成功；2-执行失败；3-执行中）")
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