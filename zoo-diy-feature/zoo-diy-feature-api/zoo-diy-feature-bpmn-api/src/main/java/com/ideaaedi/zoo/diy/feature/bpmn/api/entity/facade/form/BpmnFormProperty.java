package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.form;

/**
 * 表单字段信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnFormProperty {
    
    /**
     * 字段key
     */
    String getKey();
    
    /**
     * 字段（展示）名称
     */
    String getName();
    
    /**
     * 字段描述
     */
    String getDescription();
    
    /**
     * 字段类型
     */
    String getType();
    
    /**
     * 字段value
     */
    String getValue();
    
    /**
     * 是否可读
     */
    Boolean getIfReadable();
    
    /**
     * 是否可写
     */
    Boolean getIfWritable();
    
    /**
     * 是否必须
     */
    Boolean getIfRequired();
}
