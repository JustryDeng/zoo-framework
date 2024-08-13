package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 系统-文件表 list resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-文件表 list resp")
public class SysFileListRespVO {

    /**
     * 主键
     */
    @Schema(description = "主键")
    @ExcelProperty("主键")
    private Long id;
    
    /**
     * 租户编码
     */
    @Schema(description = "租户编码")
    @ExcelProperty("租户编码")
    private String tenant;
    
    /**
     * 所属业务租户的名称
     */
    @Schema(description = "所属业务租户的名称")
    @ExcelProperty("所属业务租户的名称")
    private String tenantName;
    
    /**
     * 文件md5指纹
     */
    @Schema(description = "文件md5指纹")
    @ExcelProperty("文件md5指纹")
    private String md5;

    /**
     * 下载文件url
     */
    @Schema(description = "下载文件url")
    @ExcelProperty("下载文件url")
    private String url;

    /**
     * 存储路径
     */
    @Schema(description = "存储路径")
    @ExcelProperty("存储路径")
    private String path;

    /**
     * 基础存储路径
     */
    @Schema(description = "基础存储路径")
    @ExcelProperty("基础存储路径")
    private String basePath;

    /**
     * 存储平台标识
     */
    @Schema(description = "存储平台标识")
    @ExcelProperty("存储平台标识")
    private String storagePlatform;

    /**
     * 文件大小(byte)
     */
    @Schema(description = "文件大小(byte)")
    @ExcelProperty("文件大小(byte)")
    private Long size;

    /**
     * 文件名
     */
    @Schema(description = "文件名")
    @ExcelProperty("文件名")
    private String filename;

    /**
     * 文件原名
     */
    @Schema(description = "文件原名")
    @ExcelProperty("文件原名")
    private String originalFilename;

    /**
     * 文件扩展名
     */
    @Schema(description = "文件扩展名")
    @ExcelProperty("文件扩展名")
    private String ext;

    /**
     * MIME类型
     */
    @Schema(description = "MIME类型")
    @ExcelProperty("MIME类型")
    private String contentType;

    /**
     * 缩略图访问路径
     */
    @Schema(description = "缩略图访问路径")
    @ExcelProperty("缩略图访问路径")
    private String thUrl;

    /**
     * 缩略图名称
     */
    @Schema(description = "缩略图名称")
    @ExcelProperty("缩略图名称")
    private String thFilename;

    /**
     * 缩略图文件大小(byte)
     */
    @Schema(description = "缩略图文件大小(byte)")
    @ExcelProperty("缩略图文件大小(byte)")
    private Long thSize;

    /**
     * 缩略图MIME类型
     */
    @Schema(description = "缩略图MIME类型")
    @ExcelProperty("缩略图MIME类型")
    private String thContentType;
}