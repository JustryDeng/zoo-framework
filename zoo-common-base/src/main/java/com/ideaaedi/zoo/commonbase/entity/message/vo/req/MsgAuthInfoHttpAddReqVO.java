package com.ideaaedi.zoo.commonbase.entity.message.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 消息-http(s)认证表 add req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "消息-http(s)认证表 add req")
public class MsgAuthInfoHttpAddReqVO extends BaseDTO {

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 认证总表id
     */
    @Schema(description = "认证总表id")
    private Long msgAuthId;

    /**
     * auth类型（ACT_PWD：账密；AK_SK：客户id与秘钥；TOKEN-凭据）
     */
    @Schema(description = "auth类型（ACT_PWD：账密；AK_SK：客户id与秘钥；TOKEN-凭据）")
    private String subtype;

    /**
     * 进行auth的url
     */
    @Schema(description = "进行auth的url")
    private String url;

    /**
     * 请求auth url的方法
     */
    @Schema(description = "请求auth url的方法")
    private String method;

    /**
     * 请求auth url时的content-type
     */
    @Schema(description = "请求auth url时的content-type")
    private String contentType;

    /**
     * 请求auth url时的header（支持spring占位符${}解析，可选的占位符变量见auth_vars、auth_sys_vars）
     */
    @Schema(description = "请求auth url时的header（支持spring占位符${}解析，可选的占位符变量见auth_vars、auth_sys_vars）")
    private String headers;

    /**
     * 请求auth url时的body（支持spring占位符${}解析，可选的占位符变量见auth_vars、auth_sys_vars）
     */
    @Schema(description = "请求auth url时的body（支持spring占位符${}解析，可选的占位符变量见auth_vars、auth_sys_vars）")
    private String body;

    /**
     * auth变量名与变量值，json对象
     */
    @Schema(description = "auth变量名与变量值，json对象")
    private String vars;

    /**
     * 需要的系统变量，json数组（RANDOM32STR-32位随机字符串；NOW_SECOND-当前到秒的时间戳；NOW-当前到毫秒的时间戳）
     */
    @Schema(description = "需要的系统变量，json数组（RANDOM32STR-32位随机字符串；NOW_SECOND-当前到秒的时间戳；NOW-当前到毫秒的时间戳）")
    private String sysVars;

    /**
     * auth扩展字段
     */
    @Schema(description = "auth扩展字段")
    private String ext;

    /**
     * auth文档（markdown格式）
     */
    @Schema(description = "auth文档（markdown格式）")
    private String doc;

    /**
     * auth状态(1-正常；2-禁用)
     */
    @Schema(description = "auth状态(1-正常；2-禁用)")
    private Integer substate;

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