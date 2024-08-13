package com.ideaaedi.zoo.diy.feature.bpmn.api.bpmn20.model;

import com.ideaaedi.zoo.commonbase.zoo_support.MoreSupport;
import lombok.Data;

import java.util.Map;

/**
 * bpmn20流程文件中，所有元素类的父类
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public abstract class Bpmn20BaseElement implements MoreSupport {
    
    /**
     * id
     */
    protected String id;
    
    /**
     * 元素在BPMN XML文件中的起始行号
     */
    protected Integer xmlRowNumber;
    
    /**
     * 元素在BPMN XML文件中的起始列号
     */
    protected Integer xmlColumnNumber;
    
    /**
     * 更多
     */
    protected Map<String, Object> more;
}
