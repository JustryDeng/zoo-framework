package com.ideaaedi.zoo.diy.feature.file.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 缩略图
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThumbnailUploadDTO {
    
    /**
     * 缩略图宽高
     */
    private int width;
    
    /**
     * 缩略图宽高
     */
    private int height;
    
    /**
     * 不带后缀的缩略图名称（如：zhangsan.jpg中的zhangsan）
     */
    private String filenameNoSuffix;
    
    /**
     * 缩略图后缀（如：.jpg）
     */
    private String filenameSuffix;
    
    /**
     * 缩略图 MIME 类型（为空则自动探测）
     */
    private String contentType;
}
