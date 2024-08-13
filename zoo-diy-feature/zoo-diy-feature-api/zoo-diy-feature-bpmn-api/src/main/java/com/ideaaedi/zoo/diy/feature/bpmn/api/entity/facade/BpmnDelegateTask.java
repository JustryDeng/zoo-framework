package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

import com.ideaaedi.zoo.diy.feature.bpmn.api.listener.task.BpmnTaskListener;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 委派任务
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnDelegateTask {
    
    /**
     * 获取任务id
     *
     * @return 任务id
     */
    String getId();
    
    /**
     * 获取任务名称
     *
     * @return 任务名称
     */
    String getName();
    
    /**
     * 设置任务名称
     *
     * @param name 任务名称
     */
    void setName(String name);
    
    /**
     * 获取任务描述
     *
     * @return 任务描述
     */
    String getDescription();
    
    /**
     * 设置任务描述
     *
     * @param description 任务描述
     */
    void setDescription(String description);
    
    /**
     * 获取任务优先级
     *
     * @return 任务优先级
     */
    Integer getPriority();
    
    /**
     * 设置任务优先级
     * <p>
     *     用介于 0 和 100 之间的数字表示此任务的重要性/紧急程度，其中值越高表示优先级越高，
     *     值越低表示优先级越低：[0..19] 最低，[20..39] 低，[40..59] 正常，[60..79] 高 [80..100] 最高
     * </p>
     *
     * @param priority 任务优先级
     */
    void setPriority(Integer priority);
    
    /**
     * 获取任务所属流程实例id
     *
     * @return 任务所属流程实例id
     */
    String getProcessInstanceId();
    
    /**
     * 获取任务所属执行实例id
     *
     * @return 任务所属执行实例id
     */
    String getExecutionId();
    
    /**
     * 获取任务所属流程定义id
     *
     * @return 任务所属流程定义id
     */
    String getProcessDefinitionId();
    
    /**
     * 获取任务状态
     *
     * @return 任务所属流程定义id
     */
    String getState();
    
    /**
     * 获取任务创建时间
     *
     * @return 任务创建时间
     */
    LocalDateTime getCreateTime();
    
    /**
     * 获取任务进入流程的时间（一般可理解为：启动任务时间）
     *
     * @return 任务进入流程的时间
     */
    LocalDateTime getInProgressStartTime();
    
    /**
     * 获取将任务放入流程的用户（一般可理解为：任务执行启动人）
     *
     * @return 将任务放入流程的用户
     */
    String getInProgressStartedBy();
    
    /**
     * 获取任务被认领的时间
     *
     * @return 任务被认领的时间
     */
    LocalDateTime getClaimTime();
    
    /**
     * 获取任务认领人
     *
     * @return 任务认领人
     */
    String getClaimedBy();
    
    /**
     * 获取任务暂停时间
     *
     * @return 任务暂停时间
     */
    LocalDateTime getSuspendedTime();
    
    /**
     * 获取任务暂停人
     *
     * @return 任务暂停人
     */
    String getSuspendedBy();
    
    /**
     * 获取任务是否暂停
     *
     * @return 任务是否暂停
     */
    Boolean ifSuspended();
    
    /**
     * 获取任务租户值
     *
     * @return 任务所属租户值
     */
    String getTenant();
    
    /**
     * 获得事件名称
     *
     * @return 事件名称 <ul>
     *     <li>{@link BpmnTaskListener#EVENTNAME_ASSIGNMENT}：任务被委派给某人后触发（create之前触发）</li>
     *     <li>{@link BpmnTaskListener#EVENTNAME_CREATE}：任务创建时，并且所有的任务属性设置完成后 触发</li>
     *     <li>{@link BpmnTaskListener#EVENTNAME_COMPLETE}：任务完成后，从运行时数据（runtime data）中删除前触发</li>
     *     <li>{@link BpmnTaskListener#EVENTNAME_DELETE}：在任务将要被删除之前发生（当任务通过completeTask完成任务时，它也会被执
     * 行）</li>
     * </ul>
     */
    String getEventName();
    
    /**
     * 获取任务委托状态
     *
     * @return 任务委托状态
     */
    String getDelegationState();
    
    /**
     * 添加执行任务的候选人
     *
     * @param userId 候选人
     */
    void addCandidateUser(String userId);
    
    /**
     * 添加执行任务的候选人
     *
     * @param candidateUsers 候选人
     */
    void addCandidateUsers(Collection<String> candidateUsers);
    
    /**
     * 添加执行任务的候选组
     *
     * @param groupId 候选组
     */
    void addCandidateGroup(String groupId);
    
    /**
     * 添加执行任务的候选组
     *
     * @param candidateGroups 候选组
     */
    void addCandidateGroups(Collection<String> candidateGroups);
    
    /**
     * 获取任务负责人
     *
     * @return 任务负责人
     */
    String getOwner();
    
    /**
     * 设置任务负责人
     *
     * @param owner 任务负责人
     */
    void setOwner(String owner);
    
    /**
     * 获取任务办理人
     *
     * @return 任务办理人
     */
    String getAssignee();
    
    /**
     * 设置任务办理人
     *
     * @param assignee 任务办理人
     */
    void setAssignee(String assignee);
    
    /**
     * 获取任务截止日期
     *
     * @return 任务截止日期
     */
    LocalDateTime getDueDate();
    
    /**
     * 设置任务截止日期
     *
     * @param dueDate 任务截止日期
     */
    void setDueDate(LocalDateTime dueDate);
    
    /**
     * 获取任务类别
     *
     * @return 任务类别
     */
    String getCategory();
    
    /**
     * 设置任务类别
     *
     * @param category 任务类别
     */
    void setCategory(String category);
}
