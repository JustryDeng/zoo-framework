package com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.listener;

import com.ideaaedi.zoo.diy.feature.bpmn.api.listener.task.BpmnTaskListener;
import com.ideaaedi.zoo.diy.feature.bpmn.api.listener.task.BpmnTaskListenerWrapper;

import javax.annotation.Nonnull;

/**
 * BpmnTaskListener包装
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class FlowableTaskListenerWrapper extends BpmnTaskListenerWrapper {
    
    @Nonnull
    public BpmnTaskListener doWrap(@Nonnull BpmnTaskListener bpmnTaskListenerBean) {
        return new FlowableTaskListener(bpmnTaskListenerBean);
    }
}
