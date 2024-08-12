package com.ideaaedi.zoo.commonbase.entity.message.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 消息-内容表 update req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "消息-内容表 update req")
public class MsgMessageUpdateReqVO extends BaseDTO {

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
     * 根消息 id（0表示无）
     */
    @Schema(description = "根消息 id（0表示无）")
    private Long rid;

    /**
     * 父消息 id（0表示无）
     */
    @Schema(description = "父消息 id（0表示无）")
    private Long pid;

    /**
     * 消息类型(0-即时消息; 1-定时消息)
     */
    @Schema(description = "消息类型(0-即时消息; 1-定时消息)")
    private Integer type;

    /**
     * 消息标题
     */
    @Schema(description = "消息标题")
    private String title;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容")
    private String content;

    /**
     * 消息内容标签
     */
    @Schema(description = "消息内容标签")
    private String contentTags;

    /**
     * 消息内容格式
     */
    @Schema(description = "消息内容格式")
    private String contentType;

    /**
     * 消息过期时间(单位秒; 0-永不过期)
     */
    @Schema(description = "消息过期时间(单位秒; 0-永不过期)")
    private Integer timeToLive;

    /**
     * 业务端产生消息的时间
     */
    @Schema(description = "业务端产生消息的时间")
    private LocalDateTime generateTime;

    /**
     * 消息中心接收到消息的时间
     */
    @Schema(description = "消息中心接收到消息的时间")
    private LocalDateTime receiveTime;

    /**
     * 消息中心保存消息的时间
     */
    @Schema(description = "消息中心保存消息的时间")
    private LocalDateTime saveTime;

    /**
     * 预期消息中心消费消息的时间
     */
    @Schema(description = "预期消息中心消费消息的时间")
    private String expectExecTime;

    /**
     * 消息状态（负数表示执行前状态，正数表示执行后状态：-3-草稿态；-2-已失效；-1-已过期；0-待执行；1-执行成功；2-执行失败；3-执行中）
     */
    @Schema(description = "消息状态（负数表示执行前状态，正数表示执行后状态：-3-草稿态；-2-已失效；-1-已过期；0-待执行；1-执行成功；2-执行失败；3-执行中）")
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