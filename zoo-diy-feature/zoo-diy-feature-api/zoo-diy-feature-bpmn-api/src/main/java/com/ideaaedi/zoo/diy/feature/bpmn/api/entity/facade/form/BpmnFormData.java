package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.form;

import java.util.List;

/**
 * 表单信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnFormData {
    
    /**
     * 表单数据id
     */
    String getId();
    
    /**
     * 表单唯一key
     */
    String getFormKey();
    
    /**
     * 表单名称
     */
    String getFormName();
    
    /**
     * 表单描述
     */
    String getFormDescription();
    
    /**
     * 租户
     *
     * @return 租户
     */
    String getTenant();
    
    /**
     * 分类
     *
     * @return 分类
     */
    String getCategory();
    
    /**
     * 表单详细字段
     */
    List<? extends BpmnFormProperty> getFormProperties();
}
