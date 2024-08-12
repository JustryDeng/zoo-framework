package com.ideaaedi.zoo.diy.feature.file.api.service;

import com.ideaaedi.zoo.commonbase.constant.JdSymbolConstant;
import com.ideaaedi.zoo.commonbase.constant.PathConstant;
import com.ideaaedi.zoo.commonbase.zoo_util.ZooContext;
import com.ideaaedi.zoo.diy.feature.file.api.entity.FileInfoDTO;
import com.ideaaedi.zoo.diy.feature.file.api.entity.FileUploadDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 文件上传与下载服务
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface FileUploadDownload {
    
    /**
     * 文件路径 日期格式
     */
    DateTimeFormatter FILE_DATE_PATH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM/dd/HHmmss");
    
    /**
     * 文件上传
     *
     * @see FileUploadDownload#upload(String, MultipartFile)
     */
    @Nonnull
    default FileInfoDTO upload(@Nonnull MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        Objects.requireNonNull(originalFilename, "originalFilename cannot be null.");
        return upload(null, file);
    }

    /**
     * 文件上传
     *
     * @see FileUploadDownload#upload(String, byte[], String, String)
     */
    @Nonnull
    default FileInfoDTO upload(@Nullable String platform, @Nonnull MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        Objects.requireNonNull(originalFilename, "originalFilename cannot be null.");
        return upload(
                FileUploadDTO.of(file)
                        .setPlatform(platform)
                        .setContentType(file.getContentType())
                        .setOriginalFilename(originalFilename)
                        .setDirPath(
                                LocalDateTime.now().format(PathConstant.FILE_DATE_PATH_FORMATTER)
                                + JdSymbolConstant.SLASH
                                + ZooContext.Auth.currUserIdDefault(0L) + JdSymbolConstant.SLASH
                        )
        );
    }
    
    /**
     * 文件上传
     *
     * @see FileUploadDownload#upload(String, byte[], String, String)
     */
    @Nonnull
    default FileInfoDTO upload(@Nonnull byte[] fileBytes, @Nonnull String originalFilename) {
        return upload(null, fileBytes, originalFilename, null);
    }
    
    /**
     * 文件上传
     *
     * @see FileUploadDownload#upload(String, byte[], String, String)
     */
    @Nonnull
    default FileInfoDTO upload(@Nonnull byte[] fileBytes, @Nonnull String originalFilename, @Nullable String contentType) {
        return upload(null, fileBytes, originalFilename, contentType);
    }
    /**
     * 文件上传
     *
     * @see FileUploadDownload#upload(String, byte[], String, String)
     */
    @Nonnull
    default FileInfoDTO upload(@Nullable String platform, @Nonnull byte[] fileBytes, @Nonnull String originalFilename) {
        return upload(platform, fileBytes, originalFilename, null);
    }
    
    /**
     * 文件上传
     *
     * @param platform 要上传的平台
     * @param fileBytes 文件内容
     * @param originalFilename 文件名
     * @param contentType 文件内容类型
     *
     * @return 文件详情
     */
    @Nonnull
    default FileInfoDTO upload(@Nullable String platform, @Nonnull byte[] fileBytes, @Nonnull String originalFilename, @Nullable String contentType) {
        return upload(
                FileUploadDTO.of(fileBytes)
                        .setPlatform(platform)
                        .setContentType(contentType)
                        .setOriginalFilename(originalFilename)
                        .setDirPath(
                                LocalDateTime.now().format(FILE_DATE_PATH_FORMATTER)
                                        + JdSymbolConstant.SLASH
                                        + ZooContext.Auth.currUserIdDefault(0L) + JdSymbolConstant.SLASH
                        )
        );
    }
    
    /**
     * 文件上传
     *
     * @see FileUploadDownload#upload(String, InputStream, String, String)
     */
    @Nonnull
    default FileInfoDTO upload(@Nonnull InputStream fileIs, @Nonnull String originalFilename) {
        return upload(null, fileIs, originalFilename, null);
    }
    
    /**
     * 文件上传
     *
     * @see FileUploadDownload#upload(String, InputStream, String, String)
     */
    @Nonnull
    default FileInfoDTO upload(@Nonnull InputStream fileIs, @Nonnull String originalFilename, @Nullable String contentType) {
        return upload(null, fileIs, originalFilename, contentType);
    }
    /**
     * 文件上传
     *
     * @see FileUploadDownload#upload(String, InputStream, String, String)
     */
    @Nonnull
    default FileInfoDTO upload(@Nullable String platform, @Nonnull InputStream fileIs, @Nonnull String originalFilename) {
        return upload(platform, fileIs, originalFilename, null);
    }
    
    /**
     * 文件上传
     *
     * @param platform 要上传的平台
     * @param fileIs 文件流
     *                <p>
     *                 在逻辑结束后，此流会自动关闭，外部不需要再关闭流
     *                </p>
     * @param originalFilename 文件名
     * @param contentType 文件内容类型
     *
     * @return 文件详情
     */
    @Nonnull
    default FileInfoDTO upload(@Nullable String platform, @Nonnull InputStream fileIs, @Nonnull String originalFilename, @Nullable String contentType) {
        return upload(
                FileUploadDTO.of(fileIs)
                        .setPlatform(platform)
                        .setContentType(contentType)
                        .setOriginalFilename(originalFilename)
                        .setDirPath(
                                LocalDateTime.now().format(FILE_DATE_PATH_FORMATTER)
                                        + JdSymbolConstant.SLASH
                                        + ZooContext.Auth.currUserIdDefault(0L) + JdSymbolConstant.SLASH
                        )
        );
    }
    
    /**
     * 文件上传
     *
     * @see FileUploadDownload#upload(String, File, String)
     */
    @Nonnull
    default FileInfoDTO upload(@Nonnull File file) {
        return upload(null, file, null);
    }
    
    /**
     * 文件上传
     *
     * @see FileUploadDownload#upload(String, File, String)
     */
    @Nonnull
    default FileInfoDTO upload(@Nullable String platform, @Nonnull File file) {
        return upload(platform, file,null);
    }
    
    /**
     * 文件上传
     *
     * @see FileUploadDownload#upload(String, File, String)
     */
    @Nonnull
    default FileInfoDTO upload(@Nonnull File file, @Nullable String contentType) {
        return upload(null, file, contentType);
    }
    
    /**
     * 文件上传
     *
     * @param platform 要上传的平台
     * @param file 文件内容
     * @param contentType 文件内容类型
     *
     * @return 文件详情
     */
    @Nonnull
    default FileInfoDTO upload(@Nullable String platform, @Nonnull File file, @Nullable String contentType) {
        return upload(
                FileUploadDTO.of(file)
                        .setPlatform(platform)
                        .setContentType(contentType)
                        .setOriginalFilename(file.getName())
                        .setDirPath(
                                LocalDateTime.now().format(FILE_DATE_PATH_FORMATTER)
                                        + JdSymbolConstant.SLASH
                                        + ZooContext.Auth.currUserIdDefault(0L) + JdSymbolConstant.SLASH
                        )
        );
    }
    
    /**
     * 文件上传
     *
     * @param upload 文件上传信息
     *
     * @return 文件详情
     */
    @Nonnull
    FileInfoDTO upload(@Nonnull FileUploadDTO upload);
    
    /**
     * 文件下载
     *
     * @see FileUploadDownload#download(String, String)
     */
    @Nonnull
    default byte[] download(@Nonnull String filepath) {
        return download(null, filepath);
    }
    
    /**
     * 文件下载
     *
     * @see FileUploadDownload#downloadToOutputStream(String, String, OutputStream)
     */
    default void downloadToFile(@Nonnull String filepath, @Nonnull File downloadTo) {
        downloadToFile(null, filepath, downloadTo);
    }
    
    /**
     * 文件下载
     *
     * @see FileUploadDownload#downloadToOutputStream(String, String, OutputStream)
     */
    default void downloadToOutputStream(@Nonnull String filepath, @Nonnull OutputStream downloadTo) {
        downloadToOutputStream(null, filepath, downloadTo);
    }
    
    /**
     * 文件下载
     *
     * @param platform 文件存储平台实例唯一标识
     * @param filepath 文件路径
     *
     * @return 文件内容字节
     */
    @Nonnull
    byte[] download(@Nullable String platform, @Nonnull String filepath);
    
    /**
     * 文件下载
     *
     * @param platform 文件存储平台实例唯一标识
     * @param filepath 文件路径
     * @param downloadTo 下载到指定的文件
     */
    void downloadToFile(@Nullable String platform, @Nonnull String filepath, @Nonnull File downloadTo);
    
    /**
     * 文件下载
     *
     * @param platform 文件存储平台实例唯一标识
     * @param filepath 文件路径
     * @param downloadTo 下载到指定的输出流
     */
    void downloadToOutputStream(@Nullable String platform, @Nonnull String filepath, @Nonnull OutputStream downloadTo);
    
    /**
     * 获取临时url连接
     *
     * @see FileUploadDownload#generateTempUrl(String, String, int, TimeUnit)
     */
    @Nullable
    default String generateTempUrl(@Nonnull String filepath, int duration, @Nonnull TimeUnit unit) {
        return generateTempUrl(null, filepath, duration, unit);
    }
    
    /**
     * 获取临时url连接
     * <p>
     * 对于一些需要权限的资源，我们希望能暴露一个有时限的可访问url时，可使用此方法获取临时url
     * </p>
     *
     * @param platform 文件存储平台实例唯一标识
     * @param filepath 文件(或缩略图)路径
     * @param duration 链接失效时长
     * @param unit 链接失效时长单位
     *
     * @return 临时url链接
     */
    @Nullable
    String generateTempUrl(@Nullable String platform, @Nonnull String filepath, int duration, @Nonnull TimeUnit unit);
    
    
    /**
     * 删除文件及缩略图
     *
     * @see FileUploadDownload#delete(String, String, Collection)
     */
    default void delete(@Nonnull String dirPath, @Nonnull Collection<String> filenames) {
        delete(null, dirPath, filenames);
    }
    
    /**
     * 删除文件
     * <br />
     *
     * @param platform 文件存储平台实例唯一标识
     * @param dirPath 文件所属目录
     * @param filenames 要删除的文件名
     */
    void delete(@Nullable String platform, @Nonnull String dirPath, @Nonnull Collection<String> filenames);
    
    /**
     * 默认的文件存储平台实例
     * <p>
     * 当需要platform时，如果用户不传platform时，则使用此值
     * </p>
     *
     */
    @Nullable
    String getDefaultPlatform();
}
