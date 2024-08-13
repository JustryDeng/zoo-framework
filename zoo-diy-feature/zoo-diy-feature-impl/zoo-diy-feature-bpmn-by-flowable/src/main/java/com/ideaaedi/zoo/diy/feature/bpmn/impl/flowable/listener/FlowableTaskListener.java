package com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.listener;

import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnDelegateTask;
import com.ideaaedi.zoo.diy.feature.bpmn.api.listener.task.AbstractBpmnTaskListenerProxy;
import com.ideaaedi.zoo.diy.feature.bpmn.api.listener.task.BpmnTaskListener;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class FlowableTaskListener extends AbstractBpmnTaskListenerProxy<DelegateTask> implements TaskListener {
    
    public FlowableTaskListener(BpmnTaskListener bpmnTaskListener) {
        super(bpmnTaskListener);
    }
    
    @Override
    public BpmnDelegateTask buildTaskProxy(DelegateTask delegateTask) {
        return new FlowableDelegateTask(delegateTask);
    }
    
    @Override
    public void notify(DelegateTask delegateTask) {
        proxy.notify(buildTaskProxy(delegateTask));
    }
}
