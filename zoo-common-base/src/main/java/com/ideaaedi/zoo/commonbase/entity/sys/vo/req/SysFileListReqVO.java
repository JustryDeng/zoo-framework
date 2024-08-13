package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统-文件表 list req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-文件表 list req")
public class SysFileListReqVO extends BasePageDTO {

    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;

    /**
     * 存储路径
     */
    @Schema(description = "存储路径")
    private String path;

    /**
     * 基础存储路径
     */
    @Schema(description = "基础存储路径")
    private String basePath;

    /**
     * 存储平台标识
     */
    @Schema(description = "存储平台标识")
    private String storagePlatform;

    /**
     * 文件原名
     */
    @Schema(description = "文件原名")
    private String originalFilename;

    /**
     * 文件扩展名
     */
    @Schema(description = "文件扩展名")
    private String ext;

    /**
     * MIME类型
     */
    @Schema(description = "MIME类型")
    private String contentType;

    /**
     * 缩略图名称
     */
    @Schema(description = "缩略图名称")
    private String thFilename;

    /**
     * 缩略图MIME类型
     */
    @Schema(description = "缩略图MIME类型")
    private String thContentType;

}