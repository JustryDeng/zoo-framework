package com.ideaaedi.zoo.diy.feature.bpmn.api.listener.task;

import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnDelegateTask;

/**
 * 任务监听器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnTaskListener {
    
    String EVENTNAME_CREATE = "create";
    
    String EVENTNAME_ASSIGNMENT = "assignment";
    
    String EVENTNAME_COMPLETE = "complete";
    
    String EVENTNAME_DELETE = "delete";
    
    /**
     * Not an actual event, used as a marker-value for {@link BpmnTaskListener}s that should be called for all events, including {@link #EVENTNAME_CREATE} , {@link #EVENTNAME_ASSIGNMENT} and
     * {@link #EVENTNAME_COMPLETE} and {@link #EVENTNAME_DELETE}.
     */
    String EVENTNAME_ALL_EVENTS = "all";
    
    /**
     * 任务通知
     *
     * @param bpmnDelegateTask 任务信息
     */
    void notify(BpmnDelegateTask bpmnDelegateTask);
}
