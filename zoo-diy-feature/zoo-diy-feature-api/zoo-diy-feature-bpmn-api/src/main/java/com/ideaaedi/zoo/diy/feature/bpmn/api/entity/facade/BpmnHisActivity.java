package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

import com.ideaaedi.zoo.commonbase.zoo_support.MoreSupport;

import java.time.LocalDateTime;

/**
 * 历史活动
 * <p>
 * 注：历史活动BpmnHisActivity是活动BpmnHisActivity的快照， 因为目前暂时没有使用活动BpmnHisActivity的需求，
 *     所以这里暂时没创建对应的活动BpmnHisActivity类，也没有提供对应的活动查询方法
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnHisActivity extends BpmnHisDateTime, MoreSupport {
    
    /**
     * 历史活动id
     */
    String getId();
    
    /**
     * 活动id
     */
    String getActivityId();
    
    /**
     * 活动名称
     */
    String getActivityName();
    
    /**
     * 活动类型
     */
    String getActivityType();
    
    /**
     * 流程定义id
     */
    String getProcessDefinitionId();
    
    /**
     * 流程实例id
     */
    String getProcessInstanceId();
    
    /**
     * 执行实例id
     */
    String getExecutionId();
    
    /**
     * 任务id
     */
    String getTaskId();
    
    /**
     * call activity调用的子流程的流程实例id
     */
    String getCalledProcessInstanceId();
    
    /**
     * 任务办理人
     */
    String getAssignee();
    
    /**
     * 活动开始时间
     */
    LocalDateTime getStartTime();
    
    /**
     * 活动结束时间
     */
    LocalDateTime getEndTime();
    
    /**
     * 活动持续时间（毫秒）
     */
    Long getDurationInMillis();
    
    /**
     * 在同一个事务中，该活动实例相对于其他活动实例的执行顺序。数值越小，表示活动实例在事务中执行得越早
     * <p>
     * 即：活动顺序
     * </p>
     */
    Integer getTransactionOrder();
    
    /**
     * 删除原因
     */
    String getDeleteReason();
    
    /**
     * 所属租户
     */
    String getTenant();
}
