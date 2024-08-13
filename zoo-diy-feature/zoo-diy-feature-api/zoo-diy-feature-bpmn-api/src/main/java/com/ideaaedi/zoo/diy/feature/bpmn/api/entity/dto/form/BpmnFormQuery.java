package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 表单查询
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmnFormQuery {
    
    /**
     * 父id
     * <p>
     * 注：父id，表示当前创建的表单，是基于该父表单进行的更改；这在审计表单变更的时候会用到
     * </p>
     */
    @Nonnull
    private String pid;
    
    /**
     * 表单名称
     */
    @Nullable
    private String name;
    
    /**
     * 表单类别
     */
    @Nullable
    private String category;
    
    /**
     * 资源文件名, 如格式一般为：xxx.form
     */
    @Nullable
    private String resourceName;
    
    /**
     * 资源文件内容
     */
    @Nonnull
    private byte[] resourceBytes;
}

