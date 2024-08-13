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
 * 消息-http(s)认证表 po模型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@TableName("msg_auth_info_http")
@Schema(description = "消息-http(s)认证表")
public class MsgAuthInfoHttpPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId("id")
    private Long id;

    @Schema(description = "认证总表id")
    @TableField("msg_auth_id")
    private Long msgAuthId;

    @Schema(description = "auth类型（ACT_PWD：账密；AK_SK：客户id与秘钥；TOKEN-凭据）")
    @TableField("subtype")
    private String subtype;

    @Schema(description = "进行auth的url")
    @TableField("url")
    private String url;

    @Schema(description = "请求auth url的方法")
    @TableField("method")
    private String method;

    @Schema(description = "请求auth url时的content-type")
    @TableField("content_type")
    private String contentType;

    @Schema(description = "请求auth url时的header（支持spring占位符${}解析，可选的占位符变量见auth_vars、auth_sys_vars）")
    @TableField("headers")
    private String headers;

    @Schema(description = "请求auth url时的body（支持spring占位符${}解析，可选的占位符变量见auth_vars、auth_sys_vars）")
    @TableField("body")
    private String body;

    @Schema(description = "auth变量名与变量值，json对象")
    @TableField("vars")
    private String vars;

    @Schema(description = "需要的系统变量，json数组（RANDOM32STR-32位随机字符串；NOW_SECOND-当前到秒的时间戳；NOW-当前到毫秒的时间戳）")
    @TableField("sys_vars")
    private String sysVars;

    @Schema(description = "auth扩展字段")
    @TableField("ext")
    private String ext;

    @Schema(description = "auth文档（markdown格式）")
    @TableField("doc")
    private String doc;

    @Schema(description = "auth状态(1-正常；2-禁用)")
    @TableField("substate")
    private Integer substate;

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