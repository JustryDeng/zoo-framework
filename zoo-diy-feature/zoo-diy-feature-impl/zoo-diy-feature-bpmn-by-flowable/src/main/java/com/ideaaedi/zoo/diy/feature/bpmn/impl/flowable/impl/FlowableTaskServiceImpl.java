package com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.ideaaedi.zoo.commonbase.entity.PageInfo;
import com.ideaaedi.zoo.commonbase.zoo_util.DateTimeUtil;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnTaskQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnAttachment;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnComment;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnTask;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.DefaultBpmnAttachment;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.DefaultBpmnComment;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.DefaultBpmnTask;
import com.ideaaedi.zoo.diy.feature.bpmn.api.exception.BpmnDataDiscrepancyException;
import com.ideaaedi.zoo.diy.feature.bpmn.api.exception.BpmnElementNotFoundException;
import com.ideaaedi.zoo.diy.feature.bpmn.api.exception.BpmnIllegalArgumentException;
import com.ideaaedi.zoo.diy.feature.bpmn.api.exception.BpmnTenantException;
import com.ideaaedi.zoo.diy.feature.bpmn.api.service.BpmnTaskService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.persistence.entity.AttachmentEntityImpl;
import org.flowable.engine.impl.persistence.entity.CommentEntityImpl;
import org.flowable.engine.task.Attachment;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class FlowableTaskServiceImpl implements BpmnTaskService {
    
    private final TaskService taskService;
    
    public FlowableTaskServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @Nonnull
    @Override
    public TaskService getRaw() {
        return this.taskService;
    }
    
    @Nonnull
    @Override
    public BpmnTask create(@Nonnull String tenant, @Nonnull String taskId) {
        Task task = taskService.newTask(taskId);
        String tenantId = task.getTenantId();
        if (StringUtils.isNotBlank(tenantId) && !StringUtils.equals(tenant, tenantId)) {
            throw new BpmnTenantException("tenantId from task property is not match input tenant.");
        }
        DefaultBpmnTask defaultBpnTask = convertBmpTask(task);
        defaultBpnTask.setTenant(tenantId);
        return defaultBpnTask;
    }
    
    @Nonnull
    @Override
    public BpmnTask save(@Nonnull String tenant, @Nonnull BpmnTask bpmnTask) {
        TaskEntityImpl taskEntity = new TaskEntityImpl();
        taskEntity.setName(bpmnTask.getName());
        taskEntity.setDescription(bpmnTask.getDescription());
        taskEntity.setAssignee(bpmnTask.getAssignee());
        taskEntity.setAssigneeValue(bpmnTask.getAssignee());
        taskEntity.setOwner(bpmnTask.getOwner());
        taskEntity.setOwnerValue(bpmnTask.getOwner());
        taskEntity.setDueDate(DateTimeUtil.localDateTime2Date(bpmnTask.getDueDate()));
        taskEntity.setCategory(bpmnTask.getCategory());
        taskEntity.setParentTaskId(bpmnTask.getPid());
        taskEntity.setState(bpmnTask.getState());
        taskEntity.setCreateTime(DateTimeUtil.localDateTime2Date(bpmnTask.getCreateTime()));
        taskEntity.setClaimTime(DateTimeUtil.localDateTime2Date(bpmnTask.getClaimTime()));
        taskEntity.setClaimedBy(bpmnTask.getClaimedBy());
        taskEntity.setSuspendedTime(DateTimeUtil.localDateTime2Date(bpmnTask.getSuspendedTime()));
        taskEntity.setSuspendedBy(bpmnTask.getSuspendedBy());
        taskEntity.setExecutionId(bpmnTask.getExecutionId());
        taskEntity.setProcessInstanceId(bpmnTask.getProcessInstanceId());
        taskEntity.setTenantId(tenant);
        taskEntity.setId(bpmnTask.getId());
        taskEntity.setOriginalPersistentState(bpmnTask.getState());
        taskService.saveTask(taskEntity);
        return detail(tenant, bpmnTask.getId());
    }
    
    @Nonnull
    @Override
    public BpmnTask suspend(@Nonnull String tenant, @Nonnull String taskId, @Nullable String userId) {
        BpmnTask detail = detail(tenant, taskId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-task.");
        }
        taskService.suspendTask(taskId, userId);
        return detail(tenant, taskId);
    }
    
    @Nonnull
    @Override
    public BpmnTask activate(@Nonnull String tenant, @Nonnull String taskId, @Nullable String userId) {
        BpmnTask detail = detail(tenant, taskId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-task.");
        }
        taskService.activateTask(taskId, userId);
        return detail(tenant, taskId);
    }
    
    @Nonnull
    @Override
    public BpmnTask delete(@Nonnull String tenant, @Nonnull String taskId, boolean cascade) {
        BpmnTask detail = detail(tenant, taskId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-task.");
        }
        taskService.deleteTask(taskId, cascade);
        return detail;
    }
    
    @Override
    public List<BpmnTask> delete(@Nonnull String tenant, @Nonnull Collection<String> taskIds, boolean cascade) {
        List<BpmnTask> list = new ArrayList<>(list(tenant, BpmnTaskQuery.builder().ids(taskIds).build()));
        if (list.size() != taskIds.size()) {
            throw new BpmnDataDiscrepancyException("input ids.size is siscrepancy with selected size.");
        }
        taskService.deleteTasks(taskIds, cascade);
        return list;
    }
    
    @Nonnull
    @Override
    public BpmnTask delegate(@Nonnull String tenant, @Nonnull String taskId, @Nonnull String userId) {
        BpmnTask detail = detail(tenant, taskId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-task.");
        }
        taskService.delegateTask(taskId, userId);
        return detail(tenant, taskId);
    }
    
    @Nonnull
    @Override
    public BpmnTask resolve(@Nonnull String tenant, @Nonnull String taskId) {
        BpmnTask detail = detail(tenant, taskId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-task.");
        }
        taskService.resolveTask(taskId);
        return detail(tenant, taskId);
    }
    
    @Nonnull
    @Override
    public BpmnTask complete(@Nonnull String tenant, @Nonnull String taskId, @Nullable String userId,
                             @Nullable Map<String, Object> processVariables) {
        BpmnTask detail = detail(tenant, taskId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-task.");
        }
        taskService.complete(taskId, userId, processVariables);
        return detail(tenant, taskId);
    }
    
    @Nonnull
    @Override
    public BpmnTask claim(@Nonnull String tenant, @Nonnull String taskId, @Nonnull String userId) {
        BpmnTask detail = detail(tenant, taskId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-task.");
        }
        taskService.claim(taskId, userId);
        return detail(tenant, taskId);
    }
    
    @Nonnull
    @Override
    public BpmnTask unClaim(@Nonnull String tenant, @Nonnull String taskId) {
        BpmnTask detail = detail(tenant, taskId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-task.");
        }
        taskService.unclaim(taskId);
        return detail(tenant, taskId);
    }
    
    @Nonnull
    @Override
    public BpmnTask setAssignee(@Nonnull String tenant, @Nonnull String taskId, @Nonnull String userId) {
        BpmnTask detail = detail(tenant, taskId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-task.");
        }
        taskService.setAssignee(taskId, userId);
        return detail(tenant, taskId);
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnTask> list(@Nonnull String tenant, @Nullable BpmnTaskQuery bpmnTaskQuery) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskTenantIdLike(tenant + "%");
        fillTaskCondition(bpmnTaskQuery, taskQuery);
        List<Task> list = taskQuery.list();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableTaskServiceImpl::convertBmpTask).toList();
    }
    
    @Nonnull
    @Override
    public PageInfo<? extends BpmnTask> page(@Nonnull String tenant, @Nullable BpmnTaskQuery bpmnTaskQuery,
                                             int pageNum, int pageSize) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskTenantIdLike(tenant + "%");
        fillTaskCondition(bpmnTaskQuery, taskQuery);
        long total = taskQuery.count();
        if (total == 0) {
            return PageInfo.of(0, pageNum, pageSize, Collections.emptyList());
        }
        List<Task> list = taskQuery.listPage(pageNum * pageSize, pageSize);
        return PageInfo.of(
                total,
                pageNum,
                pageSize,
                list.stream().map(FlowableTaskServiceImpl::convertBmpTask).toList()
        );
    }
    
    @Nullable
    @Override
    public BpmnTask detail(@Nonnull String tenant, @Nonnull String taskId) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.taskId(taskId)
                .includeTaskLocalVariables()
                .includeProcessVariables()
                .taskTenantIdLike(tenant + "%")
                .singleResult();
        return convertBmpTask(task);
    }
    
    @Override
    public void setVariableProcess(@Nonnull String taskId, @Nonnull String variableName, @Nullable Object value) {
        taskService.setVariable(taskId, variableName, value);
    }
    
    @Override
    public void setVariablesProcess(@Nonnull String taskId, @Nonnull Map<String, ?> variables) {
        taskService.setVariables(taskId, variables);
    }
    
    @Override
    public void setVariableLocal(@Nonnull String taskId, @Nonnull String variableName, @Nullable Object value) {
        taskService.setVariableLocal(taskId, variableName, value);
    }
    
    @Override
    public void setVariablesLocal(@Nonnull String taskId, @Nonnull Map<String, ?> variables) {
        taskService.setVariablesLocal(taskId, variables);
    }
    
    @Nullable
    @Override
    public Object getVariable(@Nonnull String taskId, @Nonnull String variableName) {
        return taskService.getVariable(taskId, variableName);
    }
    
    @Nullable
    @Override
    public <T> T getVariable(@Nonnull String taskId, @Nonnull String variableName, @Nonnull Class<T> variableClass) {
        return taskService.getVariable(taskId, variableName, variableClass);
    }
    
    @Nullable
    @Override
    public Object getVariableLocal(@Nonnull String taskId, @Nonnull String variableName) {
        return taskService.getVariableLocal(taskId, variableName);
    }
    
    @Nullable
    @Override
    public <T> T getVariableLocal(@Nonnull String taskId, @Nonnull String variableName,
                                  @Nonnull Class<T> variableClass) {
        return taskService.getVariableLocal(taskId, variableName, variableClass);
    }
    
    @Nonnull
    @Override
    public Map<String, Object> getVariables(@Nonnull String taskId) {
        Map<String, Object> map = taskService.getVariables(taskId);
        if (map == null) {
            return Collections.emptyMap();
        }
        return map;
    }
    
    @Nonnull
    @Override
    public BpmnComment addComment(@Nullable String taskId, @Nullable String processInstanceId, @Nonnull String message) {
        if (StringUtils.isAllBlank(taskId, processInstanceId)) {
            throw new BpmnIllegalArgumentException("taskId, processInstanceId cannot all be blank.");
        }
        Comment comment = taskService.addComment(taskId, processInstanceId, message);
        return convertBpmnComment(comment);
    }
    
    @Nonnull
    @Override
    public BpmnComment addComment(@Nullable String taskId, @Nullable String processInstanceId, @Nullable String type,
                                  @Nonnull String message) {
        if (StringUtils.isAllBlank(taskId, processInstanceId)) {
            throw new BpmnIllegalArgumentException("taskId, processInstanceId cannot all be blank.");
        }
        Comment comment = taskService.addComment(taskId, processInstanceId, type, message);
        return convertBpmnComment(comment);
    }
    
    @Override
    public void deleteComments(@Nullable String taskId, @Nullable String processInstanceId) {
        if (StringUtils.isAllBlank(taskId, processInstanceId)) {
            throw new BpmnIllegalArgumentException("taskId, processInstanceId cannot all be blank.");
        }
        taskService.deleteComments(taskId, processInstanceId);
    }
    
    @Override
    public void deleteComment(@Nonnull String commentId) {
        taskService.deleteComment(commentId);
    }
    
    @Override
    public void updateCommentById(@Nonnull BpmnComment comment) {
        CommentEntityImpl commentEntity = new CommentEntityImpl();
        commentEntity.setUserId(comment.getUserId());
        commentEntity.setTaskId(comment.getTaskId());
        commentEntity.setTime(DateTimeUtil.localDateTime2Date(comment.getDateTime()));
        commentEntity.setProcessInstanceId(comment.getProcessInstanceId());
        commentEntity.setType(comment.getType());
        commentEntity.setFullMessage(comment.getFullMessage());
        commentEntity.setId(comment.getId());
        taskService.saveComment(commentEntity);
    }
    
    @Nullable
    @Override
    public BpmnComment getComment(@Nonnull String commentId) {
        return convertBpmnComment(taskService.getComment(commentId));
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnComment> getTaskComments(String taskId) {
        List<Comment> list = taskService.getTaskComments(taskId);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableTaskServiceImpl::convertBpmnComment).collect(Collectors.toList());
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnComment> getTaskComments(@Nonnull String taskId, @Nonnull String type) {
        List<Comment> list = taskService.getTaskComments(taskId, type);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableTaskServiceImpl::convertBpmnComment).collect(Collectors.toList());
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnComment> getCommentsByType(@Nonnull String type) {
        List<Comment> list = taskService.getCommentsByType(type);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableTaskServiceImpl::convertBpmnComment).collect(Collectors.toList());
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnComment> getProcessInstanceComments(@Nonnull String processInstanceId) {
        List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableTaskServiceImpl::convertBpmnComment).collect(Collectors.toList());
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnComment> getProcessInstanceComments(@Nonnull String processInstanceId,
                                                                  @Nonnull String type) {
        List<Comment> list = taskService.getProcessInstanceComments(processInstanceId, type);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableTaskServiceImpl::convertBpmnComment).collect(Collectors.toList());
    }
    
    @Nonnull
    @Override
    public BpmnAttachment addAttachment(@Nonnull String attachmentName, @Nullable String attachmentType,
                                        @Nullable String attachmentDescription, @Nullable String taskId,
                                        @Nullable String processInstanceId, @Nonnull  byte[] contentBytes) {
        Attachment attachment = taskService.createAttachment(attachmentType, taskId, processInstanceId,
                attachmentName, attachmentDescription, new ByteArrayInputStream(contentBytes));
        return convertBpmnAttachment(attachment);
    }
    
    @Nonnull
    @Override
    public BpmnAttachment addAttachment(@Nonnull String attachmentName, @Nullable String attachmentType,
                                        @Nullable String attachmentDescription, @Nullable String taskId,
                                        @Nullable String processInstanceId, @Nonnull String url) {
        Attachment attachment = taskService.createAttachment(attachmentType, taskId, processInstanceId,
                attachmentName, attachmentDescription, url);
        return convertBpmnAttachment(attachment);
    }
    
    @Override
    public void deleteAttachment(@Nonnull String attachmentId) {
        taskService.deleteAttachment(attachmentId);
    }
    
    @Override
    public void updateAttachmentById(@Nonnull BpmnAttachment attachment) {
        AttachmentEntityImpl attachmentEntity = new AttachmentEntityImpl();
        attachmentEntity.setName(attachment.getName());
        attachmentEntity.setDescription(attachment.getDescription());
        attachmentEntity.setType(attachment.getType());
        attachmentEntity.setTaskId(attachment.getTaskId());
        attachmentEntity.setProcessInstanceId(attachment.getProcessInstanceId());
        attachmentEntity.setUrl(attachment.getContentUrl());
        attachmentEntity.setContentId(attachment.getContentId());
        attachmentEntity.setUserId(attachment.getUserId());
        attachmentEntity.setTime(DateTimeUtil.localDateTime2Date(attachment.getDateTime()));
        attachmentEntity.setId(attachment.getId());
        taskService.saveAttachment(attachmentEntity);
    }
    
    @Nullable
    @Override
    public BpmnAttachment getAttachment(@Nonnull String attachmentId) {
        return convertBpmnAttachment(taskService.getAttachment(attachmentId));
    }
    
    @Nullable
    @Override
    public InputStream getAttachmentContent(@Nonnull String attachmentId) {
        return taskService.getAttachmentContent(attachmentId);
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnAttachment> getTaskAttachments(@Nonnull String taskId) {
        List<Attachment> list = taskService.getTaskAttachments(taskId);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableTaskServiceImpl::convertBpmnAttachment).collect(Collectors.toList());
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnAttachment> getProcessInstanceAttachments(@Nonnull String processInstanceId) {
        List<Attachment> list = taskService.getProcessInstanceAttachments(processInstanceId);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableTaskServiceImpl::convertBpmnAttachment).collect(Collectors.toList());
    }
    
    @Nonnull
    @Override
    public Map<String, Object> getVariablesLocal(@Nonnull String taskId) {
        Map<String, Object> map = taskService.getVariablesLocal(taskId);
        if (map == null) {
            return Collections.emptyMap();
        }
        return map;
    }
    
    @Nullable
    @Override
    public Object getVariableProcess(@Nonnull String taskId, @Nonnull String variableName) {
        Task task = taskService.createTaskQuery().includeProcessVariables().taskId(taskId).singleResult();
        Map<String, Object> processVariables =
                Optional.ofNullable(task).map(Task::getProcessVariables).orElse(Collections.emptyMap());
        return processVariables.get(variableName);
    }
    
    @Nullable
    @Override
    public <T> T getVariableProcess(@Nonnull String taskId, @Nonnull String variableName,
                                    @Nonnull Class<T> variableClass) {
        Task task = taskService.createTaskQuery().includeProcessVariables().taskId(taskId).singleResult();
        Map<String, Object> processVariables =
                Optional.ofNullable(task).map(Task::getProcessVariables).orElse(Collections.emptyMap());
        return variableClass.cast(processVariables.get(variableName));
    }
    
    @Nonnull
    @Override
    public Map<String, Object> getVariablesProcess(@Nonnull String taskId) {
        Task task = taskService.createTaskQuery().includeProcessVariables().taskId(taskId).singleResult();
        return Optional.ofNullable(task).map(Task::getProcessVariables).orElse(Collections.emptyMap());
    }
    
    private static DefaultBpmnComment convertBpmnComment(Comment x) {
        if (x == null) {
            return null;
        }
        return DefaultBpmnComment.builder()
                .id(x.getId())
                .userId(x.getUserId())
                .dateTime(LocalDateTimeUtil.of(x.getTime()))
                .taskId(x.getTaskId())
                .processInstanceId(x.getProcessInstanceId())
                .type(x.getType())
                .fullMessage(x.getFullMessage())
                .raw(x)
                .build();
    }
    
    private static DefaultBpmnAttachment convertBpmnAttachment(Attachment x) {
        if (x == null) {
            return null;
        }
        return DefaultBpmnAttachment.builder()
                .id(x.getId())
                .name(x.getName())
                .description(x.getDescription())
                .type(x.getType())
                .taskId(x.getTaskId())
                .processInstanceId(x.getProcessInstanceId())
                .userId(x.getUserId())
                .dateTime(LocalDateTimeUtil.of(x.getTime()))
                .contentUrl(x.getUrl())
                .contentId(x.getContentId())
                .raw(x)
                .build();
    }
    
    private static DefaultBpmnTask convertBmpTask(Task task) {
        if (task == null) {
            return null;
        }
        return DefaultBpmnTask.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .pid(task.getParentTaskId())
                .category(task.getCategory())
                .tenant(task.getTenantId())
                .state(task.getState())
                .dueDate(LocalDateTimeUtil.of(task.getDueDate()))
                .createTime(LocalDateTimeUtil.of(task.getCreateTime()))
                .owner(task.getOwner())
                .assignee(task.getAssignee())
                .processInstanceId(task.getProcessDefinitionId())
                .executionId(task.getExecutionId())
                .claimTime(LocalDateTimeUtil.of(task.getClaimTime()))
                .claimedBy(task.getClaimedBy())
                .suspendedTime(LocalDateTimeUtil.of(task.getSuspendedTime()))
                .suspendedBy(task.getSuspendedBy())
                .taskLocalVariables(task.getTaskLocalVariables())
                .processVariables(task.getProcessVariables())
                .taskDefinitionKey(task.getTaskDefinitionKey())
                .raw(task)
                .build();
    }
    
    private static void fillTaskCondition(@Nullable BpmnTaskQuery bpmnTaskQuery,
                                          TaskQuery taskQuery) {
        if (bpmnTaskQuery == null) {
            return;
        }
        String processInstanceId = bpmnTaskQuery.getProcessInstanceId();
        String processDefinitionKey = bpmnTaskQuery.getProcessDefinitionKey();
        String deploymentId = bpmnTaskQuery.getDeploymentId();
        String taskCandidateUser = bpmnTaskQuery.getCandidateUser();
        String taskCandidateGroup = bpmnTaskQuery.getCandidateGroup();
        Collection<String> taskCandidateGroupIn = bpmnTaskQuery.getCandidateGroups();
        Pair<String, Collection<String>> userIdAndUserGroupsPair = bpmnTaskQuery.getUserIdAndUserGroupsPair();
        Collection<String> ids = bpmnTaskQuery.getIds();
        String name = bpmnTaskQuery.getName();
        String sate = bpmnTaskQuery.getSate();
        String assignee = bpmnTaskQuery.getAssignee();
        Boolean queryProcessVariables = bpmnTaskQuery.getQueryProcessVariables();
        Boolean queryTaskLocalVariables = bpmnTaskQuery.getQueryTaskLocalVariables();
        if (!CollectionUtils.isEmpty(ids)) {
            taskQuery.taskIds(ids);
        }
        if (StringUtils.isNotBlank(name)) {
            taskQuery.taskNameLike(name + "%");
        }
        if (StringUtils.isNotBlank(sate)) {
            taskQuery.taskState(sate);
        }
        if (StringUtils.isNotBlank(assignee)) {
            taskQuery.taskAssignee(assignee);
        }
        if (StringUtils.isNotBlank(processInstanceId)) {
            taskQuery.processInstanceId(processInstanceId);
        }
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskQuery.processDefinitionKey(processDefinitionKey);
        }
        if (StringUtils.isNotBlank(deploymentId)) {
            taskQuery.deploymentId(deploymentId);
        }
        if (StringUtils.isNotBlank(taskCandidateUser)) {
            taskQuery.taskCandidateUser(taskCandidateUser);
        }
        if (StringUtils.isNotBlank(taskCandidateGroup)) {
            taskQuery.taskCandidateGroup(taskCandidateGroup);
        }
        if (!CollectionUtils.isEmpty(taskCandidateGroupIn)) {
            taskQuery.taskCandidateGroupIn(taskCandidateGroupIn);
        }
        if (userIdAndUserGroupsPair != null) {
            String userId = userIdAndUserGroupsPair.getLeft();
            Collection<String> userGroups = userIdAndUserGroupsPair.getRight();
            boolean userIdNotBlank = StringUtils.isNotBlank(userId);
            boolean groupsNotEmpty = !CollectionUtils.isEmpty(userGroups);
            if (userIdNotBlank || groupsNotEmpty) {
                taskQuery.or();
                if (userIdNotBlank) {
                    taskQuery.taskAssignee(userId);
                    taskQuery.taskCandidateUser(userId);
                }
                if (groupsNotEmpty) {
                    taskQuery.taskCandidateGroupIn(userGroups);
                }
                taskQuery.endOr();
            }
        }
        if (queryProcessVariables != null && queryProcessVariables) {
            taskQuery.includeProcessVariables();
        }
        if (queryTaskLocalVariables != null && queryTaskLocalVariables) {
            taskQuery.includeTaskLocalVariables();
        }
    }
}
