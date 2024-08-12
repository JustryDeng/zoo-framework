package com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.apiinfo;

import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.entity.ApiInfoDTO;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * api信息处理器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface ApiInfoHandler  {
    
    /**
     * 处理api信息
     *
     * @param apiInfoList 扫描出来的api信息
     */
    void handle(@Nonnull List<ApiInfoDTO> apiInfoList);
    
}
