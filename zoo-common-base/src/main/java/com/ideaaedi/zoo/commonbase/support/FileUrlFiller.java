package com.ideaaedi.zoo.commonbase.support;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * 文件url填充器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2021.0.1.E
 */
public interface FileUrlFiller extends Filler {
    
    /**
     * 获取fileIds字符串集合
     *
     * @see com.ideaaedi.cloud.commonspring.support.CommonService#obtainFileIdUrl(List) 参数介绍
     *
     * @return fileIds字符串集合
     */
    @Nullable
    List<String> obtainFileIdsList();
    
    /**
     * 填充文件url
     *
     * @param fileIdAndFileUrlMap key-文件id; value-文件url
     */
    void fillFileUrl(@Nonnull Map<Long, String> fileIdAndFileUrlMap);
}
