package com.ideaaedi.zoo.diy.feature.bpmn.api.listener.task;

import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnDelegateTask;

/**
 * 任务监听器 抽象代理
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public abstract class AbstractBpmnTaskListenerProxy<T> implements BpmnTaskListener {
    
    protected final BpmnTaskListener proxy;
    
    public AbstractBpmnTaskListenerProxy(BpmnTaskListener bpmnTaskListener) {
        this.proxy = bpmnTaskListener;
    }
    
    @Override
    public void notify(BpmnDelegateTask bpmnDelegateTask) {
        proxy.notify(bpmnDelegateTask);
    }
    
    
    public abstract BpmnDelegateTask buildTaskProxy(T t);
}
