package com.ideaaedi.zoo.commonbase.entity.sys.po;

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
 * 系统-文件表 po模型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@TableName("sys_file")
@Schema(description = "系统-文件表")
public class SysFilePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId("id")
    private Long id;

    @Schema(description = "租户")
    @TableField("tenant")
    private String tenant;

    @Schema(description = "文件md5指纹")
    @TableField("md5")
    private String md5;

    @Schema(description = "下载文件url")
    @TableField("url")
    private String url;

    @Schema(description = "存储路径")
    @TableField("path")
    private String path;

    @Schema(description = "基础存储路径")
    @TableField("base_path")
    private String basePath;

    @Schema(description = "存储平台标识")
    @TableField("storage_platform")
    private String storagePlatform;

    @Schema(description = "文件大小(byte)")
    @TableField("size")
    private Long size;

    @Schema(description = "文件名")
    @TableField("filename")
    private String filename;

    @Schema(description = "文件原名")
    @TableField("original_filename")
    private String originalFilename;

    @Schema(description = "文件扩展名")
    @TableField("ext")
    private String ext;

    @Schema(description = "MIME类型")
    @TableField("content_type")
    private String contentType;

    @Schema(description = "缩略图访问路径")
    @TableField("th_url")
    private String thUrl;

    @Schema(description = "缩略图名称")
    @TableField("th_filename")
    private String thFilename;

    @Schema(description = "缩略图文件大小(byte)")
    @TableField("th_size")
    private Long thSize;

    @Schema(description = "缩略图MIME类型")
    @TableField("th_content_type")
    private String thContentType;

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