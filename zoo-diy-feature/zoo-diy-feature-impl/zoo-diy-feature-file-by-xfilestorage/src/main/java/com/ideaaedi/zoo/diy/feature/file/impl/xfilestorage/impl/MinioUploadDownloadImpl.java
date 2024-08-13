package com.ideaaedi.zoo.diy.feature.file.impl.xfilestorage.impl;

import cn.hutool.core.util.StrUtil;
import com.ideaaedi.commonds.constants.SymbolConstant;
import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import com.ideaaedi.zoo.diy.feature.file.api.entity.FileInfoDTO;
import com.ideaaedi.zoo.diy.feature.file.api.entity.FileUploadDTO;
import com.ideaaedi.zoo.diy.feature.file.api.entity.ThumbnailUploadDTO;
import com.ideaaedi.zoo.diy.feature.file.api.service.FileUploadDownload;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.constant.Constant;
import org.dromara.x.file.storage.core.hash.HashInfo;
import org.dromara.x.file.storage.core.platform.FileStorage;
import org.dromara.x.file.storage.core.platform.MinioFileStorage;
import org.dromara.x.file.storage.core.presigned.GeneratePresignedUrlPretreatment;
import org.dromara.x.file.storage.core.presigned.GeneratePresignedUrlResult;
import org.dromara.x.file.storage.core.upload.UploadPretreatment;
import org.dromara.x.file.storage.core.util.Tools;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * minio实现文件下载
 * <pre>
 * 你需要先引入minio依赖：
 * {@code
 *  <dependency>
 *       <groupId>io.minio</groupId>
 *       <artifactId>minio</artifactId>
 *       <version>version</version>
 *  </dependency>
 * }
 *
 * 然后再进行相关配置：
 * {@code
 *   minio:
 *     - platform: minio-1 # 存储平台标识
 *       enable-storage: true  # 启用存储
 *       access-key: ??
 *       secret-key: ??
 *       end-point: ??
 *       bucket-name: ??
 *       domain: ?? # 访问域名，注意“/”结尾，例如：http://minio.abc.com/abc/
 *       base-path: test/ # 基础路径
 * }
 * </pre>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class MinioUploadDownloadImpl implements FileUploadDownload {
    
    private final FileStorageService fileStorageService;
    
    public MinioUploadDownloadImpl(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    
    @Nonnull
    @Override
    public FileInfoDTO upload(@Nonnull FileUploadDTO upload) {
        File fileDirect = upload.getFileDirect();
        byte[] fileBytes = upload.getFileBytes();
        InputStream fileIs = upload.getFileIs();
        MultipartFile fileMf = upload.getFileMf();
        String platform = upload.getPlatform();
        String originalFilename = upload.getOriginalFilename();
        String filename = upload.getFilename();
        String contentType = upload.getContentType();
        String dirPath = upload.getDirPath();
        boolean isCalculateHashMd5 = upload.isCalculateHashMd5();
        ThumbnailUploadDTO thumbnailUpload = upload.getThumbnailUpload();
        
        if (StringUtils.isBlank(originalFilename)) {
            if (fileDirect != null) {
                originalFilename = fileDirect.getName();
            }
        }
        if (StringUtils.isBlank(originalFilename)) {
            if (fileMf != null) {
                originalFilename = fileMf.getName();
            }
        }
        if (StringUtils.isBlank(contentType)) {
            if (fileMf != null) {
                contentType = fileMf.getContentType();
            }
        }
        if (originalFilename == null) {
            throw new IllegalArgumentException("originalFilename should not be null.");
        }
        
        UploadPretreatment uploadPretreatment = null;
        if (fileDirect != null) {
            uploadPretreatment = fileStorageService.of(fileDirect);
        }
        if (fileBytes != null) {
            uploadPretreatment = fileStorageService.of(fileBytes);
        }
        if (fileIs != null) {
            uploadPretreatment = fileStorageService.of(fileIs);
        }
        if (fileMf != null) {
            uploadPretreatment = fileStorageService.of(fileMf);
        }
        if (uploadPretreatment == null) {
            throw new IllegalArgumentException("file should not be null.");
        }
        if (StringUtils.isBlank(filename)) {
            int idx = originalFilename.lastIndexOf(SymbolConstant.POINT);
            String ext  = BaseConstant.EMPTY;
            if (idx >= 0) {
                ext = originalFilename.substring(idx);
            }
            filename = UUID.randomUUID().toString().replace("-", "") + ext;
        }
        if (dirPath.startsWith(SymbolConstant.SLASH)) {
            dirPath = dirPath.substring(1);
        }
        if (!dirPath.endsWith(SymbolConstant.SLASH)) {
            dirPath = dirPath + SymbolConstant.SLASH;
        }
        uploadPretreatment.setSaveFilename(filename)
                .setOriginalFilename(originalFilename)
                .setPath(dirPath);
        if (StringUtils.isNotBlank(contentType)) {
            uploadPretreatment.setContentType(contentType);
        }
        if (StringUtils.isNotBlank(platform)) {
            uploadPretreatment.setPlatform(platform);
        }
        if (StringUtils.isBlank(uploadPretreatment.getPlatform())) {
            uploadPretreatment.setPlatform(getDefaultPlatform());
        }
        if (isCalculateHashMd5) {
            uploadPretreatment.setHashCalculatorMd5();
        }
        if (thumbnailUpload != null) {
            int thWidth = thumbnailUpload.getWidth();
            int thHeight = thumbnailUpload.getHeight();
            String thFilenameNoSuffix = thumbnailUpload.getFilenameNoSuffix();
            String thFilenameSuffix = thumbnailUpload.getFilenameSuffix();
            String thContentType = thumbnailUpload.getContentType();
            uploadPretreatment.thumbnail(thWidth, thHeight);
            if (StringUtils.isNotBlank(thFilenameNoSuffix)) {
                uploadPretreatment.setSaveThFilename(thFilenameNoSuffix);
            }
            if (StringUtils.isNotBlank(thFilenameSuffix)) {
                uploadPretreatment.setThumbnailSuffix(thFilenameSuffix);
            }
            if (StringUtils.isNotBlank(thContentType)) {
                uploadPretreatment.setThContentType(thFilenameSuffix);
            }
        }
        FileInfo fileInfo = uploadPretreatment.upload();
    
        String filepath = Tools.getNotNull(fileInfo.getBasePath(), StrUtil.EMPTY)
                + Tools.getNotNull(fileInfo.getPath(), StrUtil.EMPTY)
                + Tools.getNotNull(fileInfo.getFilename(), StrUtil.EMPTY);
    
        String thFilepath = null;
        String thFilename = fileInfo.getThFilename();
        if (StringUtils.isNotBlank(thFilename)) {
            thFilepath = Tools.getNotNull(fileInfo.getBasePath(), StrUtil.EMPTY)
                    + Tools.getNotNull(fileInfo.getPath(), StrUtil.EMPTY)
                    + Tools.getNotNull(thFilename, StrUtil.EMPTY);
        }
        dirPath = Tools.getNotNull(fileInfo.getBasePath(), StrUtil.EMPTY)
                    + Tools.getNotNull(fileInfo.getPath(), StrUtil.EMPTY);
    
    

        FileInfoDTO fileInfoDto = FileInfoDTO.builder()
                .id(fileInfo.getId())
                .platform(fileInfo.getPlatform())
                .url(fileInfo.getUrl())
                .size(fileInfo.getSize())
                .filename(fileInfo.getFilename())
                .originalFilename(fileInfo.getOriginalFilename())
                .hashMd5(Optional.ofNullable(fileInfo.getHashInfo()).map(HashInfo::getMd5).orElse(null))
                .dirPath(dirPath)
                .filepath(filepath)
                .thFilepath(thFilepath)
                .ext(fileInfo.getExt())
                .contentType(fileInfo.getContentType())
                .thUrl(fileInfo.getThUrl())
                .thFilename(thFilename)
                .thSize(fileInfo.getThSize())
                .thContentType(fileInfo.getThContentType())
                .build();
    
        // 填充其它信息
        fillOtherInfo(fileInfoDto);
        return fileInfoDto;
    }
    
    @Nonnull
    @Override
    public byte[] download(@Nullable String platform, @Nonnull String filepath) {
        return fileStorageService.download(buildFileInfo(platform, filepath)).bytes();
    }
    
    @Override
    public void downloadToFile(@Nullable String platform, @Nonnull String filepath, @Nonnull File downloadTo) {
        fileStorageService.download(buildFileInfo(platform, filepath)).file(downloadTo);
    }
    
    @Override
    public void downloadToOutputStream(@Nullable String platform, @Nonnull String filepath, @Nonnull OutputStream downloadTo) {
        fileStorageService.download(buildFileInfo(platform, filepath)).outputStream(downloadTo);
    }
    
    @Nullable
    @Override
    public String generateTempUrl(@Nullable String platform, @Nonnull String filepath, int duration,
                                  @Nonnull TimeUnit unit) {
        if (StringUtils.isBlank(platform)) {
            platform = getDefaultPlatform();
        }
        GeneratePresignedUrlPretreatment generatePresignedUrlPretreatment = new GeneratePresignedUrlPretreatment()
                .setFileStorageService(fileStorageService)
                .setPlatform(platform);
    
        Pair<String, String> pair = determineDirPathAndFilename(filepath);
        String dirPath = pair.getLeft();
        String filename = pair.getRight();
        GeneratePresignedUrlResult result = generatePresignedUrlPretreatment
                .setExpiration(new Date(System.currentTimeMillis() + unit.toMillis(duration)))
                .setPath(dirPath)
                .setFilename(filename)
                .setMethod(Constant.GeneratePresignedUrl.Method.GET)
                .generatePresignedUrl();
        return result == null ? null : result.getUrl();
    }
    
    @Override
    public void delete(@Nullable String platform, @Nonnull String dirPath, @Nonnull Collection<String> filenames) {
        if (dirPath.startsWith(SymbolConstant.SLASH)) {
            dirPath = dirPath.substring(1);
        }
        if (!dirPath.endsWith(SymbolConstant.SLASH)) {
            dirPath = dirPath + SymbolConstant.SLASH;
        }
        if (StringUtils.isBlank(platform)) {
            platform = getDefaultPlatform();
        }
        for (String filename : filenames) {
            if (StringUtils.isBlank(filename)) {
                continue;
            }
            String filepath = dirPath + filename;
            FileInfo fileInfo = buildFileInfo(platform, filepath);
            fileStorageService.delete(fileInfo);
        }
    }
    
    @Nullable
    @Override
    public String getDefaultPlatform() {
        return fileStorageService.getProperties().getDefaultPlatform();
    }
    
    /**
     * 构建FileInfo查询对象
     *
     * @param platform 文件存储平台实例唯一标识
     * @param filepath 文件绝对路径
     *
     * @return  FileInfo查询对象
     */
    @Nonnull
    private FileInfo buildFileInfo(@Nullable String platform, String filepath) {
        Pair<String, String> pair = determineDirPathAndFilename(filepath);
        String dirPath = pair.getLeft();
        String filename = pair.getRight();
        if (StringUtils.isBlank(platform)) {
            platform = getDefaultPlatform();
        }
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFilename(filename);
        fileInfo.setPath(dirPath);
        fileInfo.setPlatform(platform);
        return fileInfo;
    }
    
    /**
     * 填充其它信息
     */
    private void fillOtherInfo(FileInfoDTO fileInfoDto) {
        String platform = fileInfoDto.getPlatform();
        if (StringUtils.isBlank(platform)) {
            return;
        }
        FileStorage fileStorageVerify = fileStorageService.getFileStorageVerify(platform);
        if (fileStorageVerify instanceof MinioFileStorage minioFileStorage) {
            String bucketName = minioFileStorage.getBucketName();
            fileInfoDto.setBucketName(bucketName);
        }
    }
    
    /**
     * 根据文件路径获取文件目录路径、文件名
     *
     * @param filepath 文件路径
     *
     * @return  左-文件所属目录； 右-文件名
     */
    public static Pair<String, String> determineDirPathAndFilename(String filepath) {
        int slashIdx = filepath.lastIndexOf(SymbolConstant.SLASH);
        String filename;
        String dirPath;
        if (slashIdx >= 0) {
            filename = filepath.substring(slashIdx + 1);
            dirPath = filepath.substring(0, slashIdx + 1);
        } else {
            filename = filepath;
            dirPath = BaseConstant.EMPTY;
        }
        return Pair.of(dirPath, filename);
    }
}
