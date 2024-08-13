package com.ideaaedi.zoo.diy.feature.bpmn.api.bpmn20.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * bpmn20流程文件中，流程相关的元素
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Bpmn20FlowElement extends Bpmn20BaseElement {
    
    /**
     * 名称
     */
    protected String name;
    
    /**
     * 描述
     */
    protected String description;
    
}
