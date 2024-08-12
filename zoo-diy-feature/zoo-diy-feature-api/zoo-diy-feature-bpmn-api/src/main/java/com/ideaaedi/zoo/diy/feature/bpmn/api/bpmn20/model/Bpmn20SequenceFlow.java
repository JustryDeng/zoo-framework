package com.ideaaedi.zoo.diy.feature.bpmn.api.bpmn20.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 连接两个流程节点（{@link Bpmn20FlowNode}）的有向连线
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Bpmn20SequenceFlow extends Bpmn20FlowElement {
    /**
     * 条件表达式
     * <p>
     * 当此表达式为true时，则表示流程需要走该箭头指向的路径
     * </p>
     */
    protected String conditionExpression;
    
    /**
     * 存储源{@link Bpmn20FlowNode}的引用
     */
    protected String sourceRef;
    
    /**
     * 存储目标{@link Bpmn20FlowNode}的引用
     */
    protected String targetRef;
    
    /**
     * 在解析流程定义数据时赋值的，源流程元素 <br /> 流程流转：sourceFlowElement -> targetFlowElement
     */
    @JsonIgnore
    protected Bpmn20FlowElement sourceFlowElement;
    
    /*
     * 在解析流程定义数据时赋值的，目标流程元素
     * <br />
     * 流程流转：sourceFlowElement -> targetFlowElement
     */
    @JsonIgnore
    protected Bpmn20FlowElement targetFlowElement;
}
