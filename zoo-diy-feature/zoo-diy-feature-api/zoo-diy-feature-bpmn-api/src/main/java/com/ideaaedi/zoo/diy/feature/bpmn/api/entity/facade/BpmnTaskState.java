package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

/**
 * 任务状态
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnTaskState {
    
    /**
     * 任务被创建，但尚未被任何用户领取。此时，任务处于待处理状态，等待分配给具体的处理人
     */
    String CREATED = "created";
    
    /**
     * 任务已被某个用户领取。这意味着任务已经有了一个明确的办理人（assignee），该用户现在负责处理此任务。
     */
    String CLAIMED = "claimed";
    
    /**
     * 任务正在处理中
     */
    String IN_PROGRESS = "inProgress";
    
    /**
     * 任务或与其相关的流程实例被挂起
     */
    String SUSPENDED = "suspended";
    
    /**
     * 任务已完成
     */
    String COMPLETED = "completed";
    
    /**
     * 任务被终止。这通常意味着任务在其自然完成之前就被强制结束，可能是因为错误、取消或者其他外部因素导致流程不再需要此任务的完成
     */
    String TERMINATED = "terminated";
    
}