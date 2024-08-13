package com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.markdown;


import com.github.xiaoymin.knife4j.core.extend.OpenApiExtendMarkdownFile;

import java.util.List;

/**
 * markdown内容提供器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface OpenApiExtendMarkdownProvider {
    
    /**
     * 获取markdown文件内容
     */
    List<OpenApiExtendMarkdownFile> provideMarkdowns();
}
