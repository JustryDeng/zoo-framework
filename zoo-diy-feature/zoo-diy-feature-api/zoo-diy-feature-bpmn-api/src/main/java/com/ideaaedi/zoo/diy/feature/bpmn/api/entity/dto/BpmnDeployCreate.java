package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 任务查询
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmnDeployCreate {
    
    /** 部署名称 */
    @Nullable
    private String name;
    
    /** 类别 */
    @Nullable
    private String category;
    
    /** 资源文件名 */
    @Nullable
    private String resourceName;
    
    /** 资源文件内容 */
    @Nonnull
    private byte[] resourceBytes;
}
