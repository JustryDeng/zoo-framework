package com.ideaaedi.zoo.diy.feature.file.api.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStream;

/**
 * 文件信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
@ToString
public class FileUploadDTO {
    
    private FileUploadDTO() {
    }
    
    /**
     * 文件存储平台实例唯一标识
     */
    private String platform;
    
    /**
     * 文件
     */
    private File fileDirect;
    
    /**
     * 文件
     */
    private byte[] fileBytes;
    
    /**
     * 文件
     */
    private InputStream fileIs;
    
    /**
     * 文件
     */
    private MultipartFile fileMf;
    
    /**
     * 文件名称
     */
    private String filename;
    
    /**
     * 原始文件名
     */
    private String originalFilename;
    
    /**
     * MIME 类型（为空则自动探测）
     */
    private String contentType;
    
    /**
     * 存储路径（即：目录路径）
     * <p>
     * dirPath开头结尾有无'/'都可以，各个实现会根据自己的需要自动对齐
     * </p>
     */
    private String dirPath;
    
    /**
     * 计算文件的hash值（基于md5）
     */
    private boolean calculateHashMd5 = true;
    
    /**
     * 缩略图信息
     */
    private ThumbnailUploadDTO thumbnailUpload;
    
    public static FileUploadDTO of(@Nonnull final File file) {
        FileUploadDTO fileUpload = new FileUploadDTO();
        fileUpload.fileDirect = file;
        return fileUpload;
    }
    
    public static FileUploadDTO of(@Nonnull final byte[] file) {
        FileUploadDTO fileUpload = new FileUploadDTO();
        fileUpload.fileBytes = file;
        return fileUpload;
    }
    
    public static FileUploadDTO of(@Nonnull final InputStream file) {
        FileUploadDTO fileUpload = new FileUploadDTO();
        fileUpload.fileIs = file;
        return fileUpload;
    }
    
    public static FileUploadDTO of(@Nonnull final MultipartFile file) {
        FileUploadDTO fileUpload = new FileUploadDTO();
        fileUpload.fileMf = file;
        return fileUpload;
    }
    
    /**
     * 设置文件名
     */
    public FileUploadDTO setFilename(final String filename) {
        this.filename = filename;
        return this;
    }
    
    /**
     * 设置要上传的平台
     */
    public FileUploadDTO setPlatform(@Nullable final String platform) {
        this.platform = platform;
        return this;
    }
    
    /**
     * 设置文件原名
     */
    public FileUploadDTO setOriginalFilename(@Nonnull final String originalFilename) {
        this.originalFilename = originalFilename;
        return this;
    }
    
    /**
     * 是否计算文件的md5
     */
    public FileUploadDTO setCalculateHashMd5(final boolean calculateHashMd5) {
        this.calculateHashMd5 = calculateHashMd5;
        return this;
    }
    
    /**
     * 设置文件存放路径（不带文件名）
     * @param dirPath  存储路径（即：目录路径）
     *                 <p>
     *                 dirPath开头结尾有无'/'都可以，各个实现会根据自己的需要自动对齐
     *                 </p>
     */
    public FileUploadDTO setDirPath(@Nonnull final String dirPath) {
        this.dirPath = dirPath;
        return this;
    }
    
    /**
     * 设置文件类型
     */
    public FileUploadDTO setContentType(final String contentType) {
        this.contentType = contentType;
        return this;
    }
    
    /**
     * 设置缩略图文件名
     */
    public FileUploadDTO setThFilename(final String thFilename) {
        if (thumbnailUpload == null) {
            thumbnailUpload = new ThumbnailUploadDTO();
        }
        this.thumbnailUpload.setFilenameNoSuffix(filename);
        return this;
    }
    
    /**
     * 设置缩略图内容类型
     */
    public FileUploadDTO setThContentType(final String thContentType) {
        if (thumbnailUpload == null) {
            thumbnailUpload = new ThumbnailUploadDTO();
        }
        this.thumbnailUpload.setContentType(thContentType);
        return this;
    }
    
    /**
     * 设置缩略图宽高
     */
    public FileUploadDTO setThWidtHeight(int width, int height) {
        if (thumbnailUpload == null) {
            thumbnailUpload = new ThumbnailUploadDTO();
        }
        thumbnailUpload.setWidth(width);
        thumbnailUpload.setHeight(height);
        return this;
    }
}
