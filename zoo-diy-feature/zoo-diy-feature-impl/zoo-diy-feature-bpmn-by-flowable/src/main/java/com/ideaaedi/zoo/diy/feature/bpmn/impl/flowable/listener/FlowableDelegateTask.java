package com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.listener;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.ideaaedi.zoo.commonbase.zoo_util.DateTimeUtil;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnDelegateTask;
import org.flowable.task.service.delegate.DelegateTask;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class FlowableDelegateTask implements BpmnDelegateTask {
    
    private final DelegateTask delegateTask;
    
    public FlowableDelegateTask(DelegateTask delegateTask) {
        this.delegateTask = delegateTask;
    }
    
    @Override
    public String getId() {
        return delegateTask.getId();
    }
    
    @Override
    public String getName() {
        return delegateTask.getName();
    }
    
    @Override
    public void setName(String name) {
        delegateTask.setName(name);
    }
    
    @Override
    public String getDescription() {
        return delegateTask.getDescription();
    }
    
    @Override
    public void setDescription(String description) {
        delegateTask.setDescription(description);
    }
    
    @Override
    public Integer getPriority() {
        return delegateTask.getPriority();
    }
    
    @Override
    public void setPriority(Integer priority) {
        if (priority == null) {
            return;
        }
        delegateTask.setPriority(priority);
    }
    
    @Override
    public String getProcessInstanceId() {
        return delegateTask.getProcessInstanceId();
    }
    
    @Override
    public String getExecutionId() {
        return delegateTask.getExecutionId();
    }
    
    @Override
    public String getProcessDefinitionId() {
        return delegateTask.getProcessDefinitionId();
    }
    
    @Override
    public String getState() {
        return delegateTask.getState();
    }
    
    @Override
    public LocalDateTime getCreateTime() {
        return LocalDateTimeUtil.of(delegateTask.getCreateTime());
    }
    
    @Override
    public LocalDateTime getInProgressStartTime() {
        return LocalDateTimeUtil.of(delegateTask.getInProgressStartTime());
    }
    
    @Override
    public String getInProgressStartedBy() {
        return delegateTask.getInProgressStartedBy();
    }
    
    @Override
    public LocalDateTime getClaimTime() {
        return LocalDateTimeUtil.of(delegateTask.getClaimTime());
    }
    
    @Override
    public String getClaimedBy() {
        return delegateTask.getClaimedBy();
    }
    
    @Override
    public LocalDateTime getSuspendedTime() {
        return LocalDateTimeUtil.of(delegateTask.getSuspendedTime());
    }
    
    @Override
    public String getSuspendedBy() {
        return delegateTask.getSuspendedBy();
    }
    
    @Override
    public Boolean ifSuspended() {
        return delegateTask.isSuspended();
    }
    
    @Override
    public String getTenant() {
        return delegateTask.getTenantId();
    }
    
    @Override
    public String getEventName() {
        return delegateTask.getEventName();
    }
    
    @Override
    public String getDelegationState() {
        return delegateTask.getState();
    }
    
    @Override
    public void addCandidateUser(String userId) {
        delegateTask.addCandidateUser(userId);
    }
    
    @Override
    public void addCandidateUsers(Collection<String> candidateUsers) {
        delegateTask.addCandidateUsers(candidateUsers);
    }
    
    @Override
    public void addCandidateGroup(String groupId) {
        delegateTask.addCandidateGroup(groupId);
    }
    
    @Override
    public void addCandidateGroups(Collection<String> candidateGroups) {
        delegateTask.addCandidateGroups(candidateGroups);
    }
    
    @Override
    public String getOwner() {
        return delegateTask.getOwner();
    }
    
    @Override
    public void setOwner(String owner) {
        delegateTask.setOwner(owner);
    }
    
    @Override
    public String getAssignee() {
        return delegateTask.getAssignee();
    }
    
    @Override
    public void setAssignee(String assignee) {
        delegateTask.setAssignee(assignee);
    }
    
    @Override
    public LocalDateTime getDueDate() {
        return LocalDateTimeUtil.of(delegateTask.getDueDate());
    }
    
    @Override
    public void setDueDate(LocalDateTime dueDate) {
        delegateTask.setDueDate(DateTimeUtil.localDateTime2Date(dueDate));
    }
    
    @Override
    public String getCategory() {
        return delegateTask.getCategory();
    }
    
    @Override
    public void setCategory(String category) {
        delegateTask.setCategory(category);
    }
}
