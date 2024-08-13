package com.ideaaedi.zoo.diy.feature.file.impl.xfilestorage;

import com.ideaaedi.zoo.diy.feature.file.impl.xfilestorage.impl.MinioUploadDownloadImpl;
import com.ideaaedi.zoo.diy.feature.file.impl.xfilestorage.properties.ZooXFileStorageDIYGuideProperties;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * file 自动配置
 */
@EnableFileStorage
@EnableConfigurationProperties({ZooXFileStorageDIYGuideProperties.class})
public class ZooXFileStorageAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public MinioUploadDownloadImpl minioUploadDownloadService(FileStorageService fileStorageService) {
        return new MinioUploadDownloadImpl(fileStorageService);
    }
    
}