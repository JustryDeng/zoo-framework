package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.form;

import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.form.BpmnFormProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 表单详情
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmnFormCreate {
    
    /**
     * 表单唯一key
     */
    private String formKey;
    
    /**
     * 表单名称
     */
    private String formName;
    
    /**
     * 表单描述
     */
    private String formDescription;
    
    /**
     * 分类
     */
    private String category;
    
    /**
     * 表单详细字段
     */
    private List<? extends BpmnFormProperty> formProperties;
}

