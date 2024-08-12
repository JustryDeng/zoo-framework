package com.ideaaedi.zoo.diy.feature.file.api.entity;

import com.ideaaedi.zoo.commonbase.zoo_entity.MoreDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文件信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FileInfoDTO extends MoreDTO {
    
    /**
     * 文件id
     */
    private String id;
    
    /**
     * 文件存储平台实例唯一标识
     */
    private String platform;
    
    /**
     * 存储桶
     */
    private String bucketName;
    
    /**
     * 文件访问地址
     */
    private String url;
    
    /**
     * 文件大小，单位字节
     */
    private Long size;
    
    /**
     * 文件名称
     */
    private String filename;
    
    /**
     * 原始文件名
     */
    private String originalFilename;
    
    /**
     * 文件hash值（基于md5）
     */
    private String hashMd5;
    
    /**
     * 存储路径
     */
    private String dirPath;
    
    /**
     * 文件路径
     * <p>
     * filepath = dirPath + filename
     * </p>
     */
    private String filepath;
    
    /**
     * 文件扩展名
     */
    private String ext;
    
    /**
     * MIME 类型
     */
    private String contentType;
    
    /**
     * 缩略图访问路径
     */
    private String thUrl;
    
    /**
     * 缩略图名称
     */
    private String thFilename;
    
    /**
     * 缩略图文件路径
     * <p>
     * thFilepath = dirPath + thFilename
     * </p>
     */
    private String thFilepath;
    
    /**
     * 缩略图大小，单位字节
     */
    private Long thSize;
    
    /**
     * 缩略图 MIME 类型
     */
    private String thContentType;
}
