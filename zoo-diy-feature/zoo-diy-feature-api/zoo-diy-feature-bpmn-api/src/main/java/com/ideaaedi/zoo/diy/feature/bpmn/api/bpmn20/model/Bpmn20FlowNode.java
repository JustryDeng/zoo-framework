package com.ideaaedi.zoo.diy.feature.bpmn.api.bpmn20.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程节点
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Bpmn20FlowNode extends Bpmn20FlowElement {
    
    /**
     * 所有可能流入当前{@link Bpmn20FlowNode}的顺序流
     */
    protected List<Bpmn20SequenceFlow> incomingFlows = new ArrayList<>();
    
    /**
     * 所有可能从当前{@link Bpmn20FlowNode}流出的顺序流
     */
    protected List<Bpmn20SequenceFlow> outgoingFlows = new ArrayList<>();

}
