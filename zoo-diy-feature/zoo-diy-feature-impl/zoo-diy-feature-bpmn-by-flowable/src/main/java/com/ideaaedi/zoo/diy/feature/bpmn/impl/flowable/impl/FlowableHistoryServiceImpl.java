package com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.ideaaedi.zoo.commonbase.entity.PageInfo;
import com.ideaaedi.zoo.commonbase.zoo_util.DateTimeUtil;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnHisActivityQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnHisProcessInstanceQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnHisTaskQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnHisVariableQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnHisActivity;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnHisProcessInstance;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnHisTask;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnHisVariable;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.DefaultBpmnHisActivity;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.DefaultBpmnHisProcessInstance;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.DefaultBpmnHisTask;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.DefaultBpmnHisVariable;
import com.ideaaedi.zoo.diy.feature.bpmn.api.enums.ORDER_TYPE;
import com.ideaaedi.zoo.diy.feature.bpmn.api.service.BpmnHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricActivityInstanceQuery;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.flowable.variable.api.history.HistoricVariableInstanceQuery;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Slf4j
public class FlowableHistoryServiceImpl implements BpmnHistoryService {
    
    private final HistoryService historyService;
    
    public FlowableHistoryServiceImpl(HistoryService historyService) {
        this.historyService = historyService;
    }
    
    @Nullable
    @Override
    public HistoryService getRaw() {
        return this.historyService;
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnHisProcessInstance> listHisProcessInstance(@Nonnull String tenant,
                                                                         @Nullable BpmnHisProcessInstanceQuery hisProcessInstanceQuery) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        historicProcessInstanceQuery.processInstanceTenantIdLike(tenant + "%");
        fillHisProcessInstanceCondition(hisProcessInstanceQuery, historicProcessInstanceQuery);
        List<HistoricProcessInstance> list = historicProcessInstanceQuery.list();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableHistoryServiceImpl::convertHisProcessInstance).toList();
    }
    
    
    @Nonnull
    @Override
    public PageInfo<? extends BpmnHisProcessInstance> pageHisProcessInstance(@Nonnull String tenant,
                                                                             @Nullable BpmnHisProcessInstanceQuery hisProcessInstanceQuery, int pageNum, int pageSize) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        historicProcessInstanceQuery.processInstanceTenantIdLike(tenant + "%");
        fillHisProcessInstanceCondition(hisProcessInstanceQuery, historicProcessInstanceQuery);
        long total = historicProcessInstanceQuery.count();
        if (total == 0) {
            return PageInfo.of(0, pageNum, pageSize, Collections.emptyList());
        }
        List<HistoricProcessInstance> list = historicProcessInstanceQuery
                .listPage(pageNum * pageSize, pageSize);
        return PageInfo.of(
                total, pageNum, pageSize,
                list.stream().map(FlowableHistoryServiceImpl::convertHisProcessInstance).toList()
        );
    }
    
    @Nullable
    @Override
    public BpmnHisProcessInstance detailHisProcessInstance(@Nonnull String tenant,
                                                           @Nonnull String hisProcessInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(hisProcessInstanceId)
                .processInstanceTenantIdLike(tenant + "%")
                .singleResult();
        return historicProcessInstance == null ? null : convertHisProcessInstance(historicProcessInstance);
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnHisActivity> listHisActivity(@Nonnull String tenant,
                                                           @Nullable BpmnHisActivityQuery bpmnHisActivityQuery) {
        HistoricActivityInstanceQuery historicActivityInstanceQuery =
                historyService.createHistoricActivityInstanceQuery();
        historicActivityInstanceQuery.activityTenantIdLike(tenant + "%");
        fillHisActivityQueryCondition(bpmnHisActivityQuery, historicActivityInstanceQuery);
        List<HistoricActivityInstance> list = historicActivityInstanceQuery.list();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableHistoryServiceImpl::convertHisActivity).toList();
    }
    
    @Nonnull
    @Override
    public PageInfo<? extends BpmnHisActivity> pageHisActivity(@Nonnull String tenant,
                                                               @Nullable BpmnHisActivityQuery bpmnHisActivityQuery,
                                                               int pageNum, int pageSize) {
        HistoricActivityInstanceQuery historicActivityInstanceQuery =
                historyService.createHistoricActivityInstanceQuery();
        historicActivityInstanceQuery.activityTenantIdLike(tenant + "%");
        fillHisActivityQueryCondition(bpmnHisActivityQuery, historicActivityInstanceQuery);
        long total = historicActivityInstanceQuery.count();
        if (total == 0) {
            return PageInfo.of(0, pageNum, pageSize, Collections.emptyList());
        }
        List<HistoricActivityInstance> list = historicActivityInstanceQuery
                .listPage(pageNum * pageSize, pageSize);
        return PageInfo.of(
                total, pageNum, pageSize,
                list.stream().map(FlowableHistoryServiceImpl::convertHisActivity).toList()
        );
    }
    
    @Nullable
    @Override
    public BpmnHisActivity detailHisActivity(@Nonnull String tenant, @Nonnull String activityId) {
        HistoricActivityInstance historicActivityInstance = historyService.createHistoricActivityInstanceQuery()
                .activityTenantIdLike(tenant + "%")
                .activityId(activityId)
                .singleResult();
        return historicActivityInstance == null ? null : convertHisActivity(historicActivityInstance);
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnHisTask> listHisTask(@Nonnull String tenant, @Nullable BpmnHisTaskQuery bpmnHisTaskQuery) {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        historicTaskInstanceQuery.taskTenantIdLike(tenant + "%");
        fillHisTaskCondition(bpmnHisTaskQuery, historicTaskInstanceQuery);
        List<HistoricTaskInstance> list = historicTaskInstanceQuery.list();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableHistoryServiceImpl::convertHisTask).toList();
    }
    
    @Nonnull
    @Override
    public PageInfo<? extends BpmnHisTask> pageHisTask(@Nonnull String tenant,
                                                       @Nullable BpmnHisTaskQuery bpmnHisTaskQuery, int pageNum,
                                                       int pageSize) {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        historicTaskInstanceQuery.taskTenantIdLike(tenant + "%");
        fillHisTaskCondition(bpmnHisTaskQuery, historicTaskInstanceQuery);
        long total = historicTaskInstanceQuery.count();
        if (total == 0) {
            return PageInfo.of(0, pageNum, pageSize, Collections.emptyList());
        }
        List<HistoricTaskInstance> list = historicTaskInstanceQuery
                .listPage(pageNum * pageSize, pageSize);
        return PageInfo.of(
                total, pageNum, pageSize,
                list.stream().map(FlowableHistoryServiceImpl::convertHisTask).toList()
        );
    }
    
    @Nullable
    @Override
    public BpmnHisTask detailHisTask(@Nonnull String tenant, @Nonnull String hisTaskId) {
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
                .includeTaskLocalVariables()
                .includeProcessVariables()
                .taskId(hisTaskId)
                .taskTenantIdLike(tenant + "%")
                .singleResult();
        return historicTaskInstance == null ? null : convertHisTask(historicTaskInstance);
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnHisVariable> listHisVariable(@Nonnull String processInstanceId,
                                                           @Nullable BpmnHisVariableQuery bpmnHisVariableQuery) {
        HistoricVariableInstanceQuery historicVariableInstanceQuery =
                historyService.createHistoricVariableInstanceQuery();
        historicVariableInstanceQuery.processInstanceId(processInstanceId);
        fillHisVariableCondition(bpmnHisVariableQuery, historicVariableInstanceQuery);
        List<HistoricVariableInstance> list = historicVariableInstanceQuery.list();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableHistoryServiceImpl::convertHisVariable).toList();
    }
    
    @Nonnull
    @Override
    public PageInfo<? extends BpmnHisVariable> pageHisVariable(@Nonnull String processInstanceId,
                                                               @Nullable BpmnHisVariableQuery bpmnHisVariableQuery,
                                                               int pageNum, int pageSize) {
        HistoricVariableInstanceQuery historicVariableInstanceQuery =
                historyService.createHistoricVariableInstanceQuery();
        historicVariableInstanceQuery.processInstanceId(processInstanceId);
        fillHisVariableCondition(bpmnHisVariableQuery, historicVariableInstanceQuery);
        long total = historicVariableInstanceQuery.count();
        if (total == 0) {
            return PageInfo.of(0, pageNum, pageSize, Collections.emptyList());
        }
        List<HistoricVariableInstance> list = historicVariableInstanceQuery
                .listPage(pageNum * pageSize, pageSize);
        return PageInfo.of(
                total, pageNum, pageSize,
                list.stream().map(FlowableHistoryServiceImpl::convertHisVariable).toList()
        );
    }
    
    @Nullable
    @Override
    public BpmnHisVariable detailHisVariable(@Nonnull String processInstanceId, @Nonnull String hisVariableId) {
        HistoricVariableInstance historicVariableInstance = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .id(hisVariableId)
                .singleResult();
        return historicVariableInstance == null ? null : convertHisVariable(historicVariableInstance);
    }
    
    private static void fillHisVariableCondition(@Nullable BpmnHisVariableQuery bpmnHisVariableQuery,
                                                 HistoricVariableInstanceQuery historicVariableInstanceQuery) {
        
        if (bpmnHisVariableQuery == null) {
            return;
        }
        String executionId = bpmnHisVariableQuery.getExecutionId();
        Collection<String> executionIds = bpmnHisVariableQuery.getExecutionIds();
        String taskId = bpmnHisVariableQuery.getTaskId();
        Collection<String> taskIds = bpmnHisVariableQuery.getTaskIds();
        String name = bpmnHisVariableQuery.getName();
        Collection<Pair<String, String>> variableValuePairColl = bpmnHisVariableQuery.getVariableValuePairColl();
        if (StringUtils.isNotBlank(executionId)) {
            historicVariableInstanceQuery.executionId(executionId);
        }
        if (StringUtils.isNotBlank(taskId)) {
            historicVariableInstanceQuery.taskId(taskId);
        }
        if (StringUtils.isNotBlank(name)) {
            historicVariableInstanceQuery.variableNameLike(name + "%");
        }
        
        if (!CollectionUtils.isEmpty(executionIds)) {
            historicVariableInstanceQuery.executionIds(new HashSet<>(executionIds));
        }
        if (!CollectionUtils.isEmpty(taskIds)) {
            historicVariableInstanceQuery.taskIds(new HashSet<>(taskIds));
        }
        
        if (!CollectionUtils.isEmpty(variableValuePairColl)) {
            for (Pair<String, String> kv : variableValuePairColl) {
                String k = kv.getLeft();
                String v = kv.getRight();
                historicVariableInstanceQuery.variableValueEquals(k, v);
            }
        }
    }
    
    private static void fillHisTaskCondition(@Nullable BpmnHisTaskQuery bpmnHisTaskQuery,
                                             HistoricTaskInstanceQuery historicTaskInstanceQuery) {
        if (bpmnHisTaskQuery == null) {
            return;
        }
        Collection<String> taskIds = bpmnHisTaskQuery.getTaskIds();
        String name = bpmnHisTaskQuery.getName();
        String description = bpmnHisTaskQuery.getDescription();
        String assignee = bpmnHisTaskQuery.getAssignee();
        Boolean ifTaskAssigned = bpmnHisTaskQuery.getIfTaskAssigned();
        String owner = bpmnHisTaskQuery.getOwner();
        String processInstanceId = bpmnHisTaskQuery.getProcessInstanceId();
        String processInstanceBusinessKey = bpmnHisTaskQuery.getProcessInstanceBusinessKey();
        String executionId = bpmnHisTaskQuery.getExecutionId();
        String claimedBy = bpmnHisTaskQuery.getClaimedBy();
        String category = bpmnHisTaskQuery.getCategory();
        String state = bpmnHisTaskQuery.getState();
        LocalDateTime taskDueBefore = bpmnHisTaskQuery.getTaskDueBefore();
        LocalDateTime taskDueAfter = bpmnHisTaskQuery.getTaskDueAfter();
        Boolean ifFinished = bpmnHisTaskQuery.getIfFinished();
        Boolean ifProcessFinished = bpmnHisTaskQuery.getIfProcessFinished();
        LocalDateTime taskCompletedBefore = bpmnHisTaskQuery.getTaskCompletedBefore();
        LocalDateTime taskCompletedAfter = bpmnHisTaskQuery.getTaskCompletedAfter();
        Pair<Boolean, ORDER_TYPE> orderByTaskEndTimePair = bpmnHisTaskQuery.getOrderByTaskEndTimePair();
        Pair<Boolean, ORDER_TYPE> orderByTaskStartTimePair = bpmnHisTaskQuery.getOrderByTaskStartTimePair();
        Collection<Pair<String, Object>> taskVariableValueEqualsColl = bpmnHisTaskQuery.getTaskVariableValueEqualsColl();
        Boolean queryTaskLocalVariables = bpmnHisTaskQuery.getQueryTaskLocalVariables();
        Boolean queryProcessVariables = bpmnHisTaskQuery.getQueryProcessVariables();
        
        if (StringUtils.isNotBlank(name)) {
            historicTaskInstanceQuery.taskNameLike(name + "%");
        }
        if (StringUtils.isNotBlank(description)) {
            historicTaskInstanceQuery.taskDescriptionLike(description + "%");
        }
        if (StringUtils.isNotBlank(assignee)) {
            historicTaskInstanceQuery.taskAssignee(assignee);
        }
        if (StringUtils.isNotBlank(owner)) {
            historicTaskInstanceQuery.taskOwner(owner);
        }
        if (StringUtils.isNotBlank(processInstanceId)) {
            historicTaskInstanceQuery.processInstanceId(processInstanceId);
        }
        if (StringUtils.isNotBlank(processInstanceBusinessKey)) {
            historicTaskInstanceQuery.processInstanceBusinessKey(processInstanceBusinessKey);
        }
        if (StringUtils.isNotBlank(executionId)) {
            historicTaskInstanceQuery.executionId(executionId);
        }
        if (StringUtils.isNotBlank(claimedBy)) {
            historicTaskInstanceQuery.taskClaimedBy(claimedBy);
        }
        if (StringUtils.isNotBlank(category)) {
            historicTaskInstanceQuery.taskCategory(category);
        }
        if (StringUtils.isNotBlank(state)) {
            historicTaskInstanceQuery.taskState(state);
        }
        if (!CollectionUtils.isEmpty(taskIds)) {
            historicTaskInstanceQuery.taskIds(taskIds);
        }
        if (!CollectionUtils.isEmpty(taskVariableValueEqualsColl)) {
            for (Pair<String, Object> kv : taskVariableValueEqualsColl) {
                String k = kv.getLeft();
                Object v = kv.getRight();
                historicTaskInstanceQuery.taskVariableValueEquals(k, v);
            }
        }
        if (taskDueBefore != null) {
            historicTaskInstanceQuery.taskDueBefore(DateTimeUtil.localDateTime2Date(taskDueBefore));
        }
        if (taskDueAfter != null) {
            historicTaskInstanceQuery.taskDueAfter(DateTimeUtil.localDateTime2Date(taskDueAfter));
        }
        if (taskCompletedBefore != null) {
            historicTaskInstanceQuery.taskCompletedBefore(DateTimeUtil.localDateTime2Date(taskCompletedBefore));
        }
        if (taskCompletedAfter != null) {
            historicTaskInstanceQuery.taskCompletedAfter(DateTimeUtil.localDateTime2Date(taskCompletedAfter));
        }
        if (ifTaskAssigned != null) {
            if (ifTaskAssigned) {
                historicTaskInstanceQuery.taskAssigned();
            } else {
                historicTaskInstanceQuery.taskUnassigned();
            }
        }
        if (ifFinished != null) {
            if (ifFinished) {
                historicTaskInstanceQuery.finished();
            } else {
                historicTaskInstanceQuery.unfinished();
            }
        }
        if (ifProcessFinished != null) {
            if (ifProcessFinished) {
                historicTaskInstanceQuery.processFinished();
            } else {
                historicTaskInstanceQuery.processUnfinished();
            }
        }
        if (orderByTaskStartTimePair != null) {
            Boolean orderByTaskStartTime = orderByTaskStartTimePair.getLeft();
            ORDER_TYPE orderType = orderByTaskStartTimePair.getRight();
            if (BooleanUtils.isTrue(orderByTaskStartTime)) {
                historicTaskInstanceQuery.orderByHistoricTaskInstanceStartTime();
            }
            if (orderType == null || ORDER_TYPE.ASC == orderType) {
                historicTaskInstanceQuery.asc();
            } else {
                historicTaskInstanceQuery.desc();
            }
        }
        if (orderByTaskEndTimePair != null) {
            Boolean orderByTaskEndTime = orderByTaskEndTimePair.getLeft();
            ORDER_TYPE orderType = orderByTaskEndTimePair.getRight();
            if (BooleanUtils.isTrue(orderByTaskEndTime)) {
                historicTaskInstanceQuery.orderByHistoricTaskInstanceEndTime();
            }
            if (orderType == null || ORDER_TYPE.ASC == orderType) {
                historicTaskInstanceQuery.asc();
            } else {
                historicTaskInstanceQuery.desc();
            }
        }
        if (queryTaskLocalVariables != null && queryTaskLocalVariables) {
            historicTaskInstanceQuery.includeTaskLocalVariables();
        }
        if (queryProcessVariables != null && queryProcessVariables) {
            historicTaskInstanceQuery.includeProcessVariables();
        }
    }
    
    private static BpmnHisVariable convertHisVariable(HistoricVariableInstance x) {
        return DefaultBpmnHisVariable
                .builder()
                .time(LocalDateTimeUtil.of(x.getTime()))
                .id(x.getId())
                .variableName(x.getVariableName())
                .variableTypeName(x.getVariableTypeName())
                .value(x.getValue())
                .processInstanceId(x.getProcessInstanceId())
                .executionId(x.getExecutionId())
                .taskId(x.getTaskId())
                .createTime(LocalDateTimeUtil.of(x.getCreateTime()))
                .lastUpdatedTime(LocalDateTimeUtil.of(x.getLastUpdatedTime()))
                .raw(x)
                .build();
    }
    
    private static BpmnHisTask convertHisTask(HistoricTaskInstance x) {
        return DefaultBpmnHisTask
                .builder()
                .time(LocalDateTimeUtil.of(x.getTime()))
                .id(x.getId())
                .name(x.getName())
                .description(x.getDescription())
                .pid(x.getParentTaskId())
                .category(x.getCategory())
                .tenant(x.getTenantId())
                .state(x.getState())
                .dueDate(LocalDateTimeUtil.of(x.getDueDate()))
                .createTime(LocalDateTimeUtil.of(x.getCreateTime()))
                .owner(x.getOwner())
                .assignee(x.getAssignee())
                .processInstanceId(x.getProcessInstanceId())
                .executionId(x.getExecutionId())
                .claimTime(LocalDateTimeUtil.of(x.getClaimTime()))
                .claimedBy(x.getClaimedBy())
                .suspendedTime(LocalDateTimeUtil.of(x.getSuspendedTime()))
                .suspendedBy(x.getSuspendedBy())
                .deleteReason(x.getDeleteReason())
                .endTime(LocalDateTimeUtil.of(x.getEndTime()))
                .completedBy(x.getCompletedBy())
                .durationInMillis(x.getDurationInMillis())
                .workTimeInMillis(x.getWorkTimeInMillis())
                .taskLocalVariables(x.getTaskLocalVariables())
                .processVariables(x.getProcessVariables())
                .taskDefinitionKey(x.getTaskDefinitionKey())
                .raw(x)
                .build();
    }
    
    private static BpmnHisProcessInstance convertHisProcessInstance(HistoricProcessInstance x) {
        return DefaultBpmnHisProcessInstance
                .builder()
                .id(x.getId())
                .businessKey(x.getBusinessKey())
                .businessStatus(x.getBusinessStatus())
                .processDefinitionId(x.getProcessDefinitionId())
                .processDefinitionName(x.getProcessDefinitionName())
                .processDefinitionKey(x.getProcessDefinitionKey())
                .processDefinitionVersion(x.getProcessDefinitionVersion())
                .processDefinitionCategory(x.getProcessDefinitionCategory())
                .deploymentId(x.getDeploymentId())
                .startTime(LocalDateTimeUtil.of(x.getStartTime()))
                .endTime(LocalDateTimeUtil.of(x.getEndTime()))
                .durationInMillis(x.getDurationInMillis())
                .startUserId(x.getStartUserId())
                .deleteReason(x.getDeleteReason())
                .superProcessInstanceId(x.getSuperProcessInstanceId())
                .tenant(x.getTenantId())
                .name(x.getName())
                .description(x.getDescription())
                .processVariables(x.getProcessVariables())
                .raw(x)
                .build();
    }
    
    private static BpmnHisActivity convertHisActivity(HistoricActivityInstance x) {
        return DefaultBpmnHisActivity
                .builder()
                .time(LocalDateTimeUtil.of(x.getTime()))
                .id(x.getId())
                .activityId(x.getActivityId())
                .activityName(x.getActivityName())
                .activityType(x.getActivityType())
                .processDefinitionId(x.getProcessDefinitionId())
                .processInstanceId(x.getProcessInstanceId())
                .executionId(x.getExecutionId())
                .taskId(x.getTaskId())
                .calledProcessInstanceId(x.getCalledProcessInstanceId())
                .assignee(x.getAssignee())
                .startTime(LocalDateTimeUtil.of(x.getStartTime()))
                .endTime(LocalDateTimeUtil.of(x.getEndTime()))
                .durationInMillis(x.getDurationInMillis())
                .transactionOrder(x.getTransactionOrder())
                .deleteReason(x.getDeleteReason())
                .tenant(x.getTenantId())
                .raw(x)
                .build();
    }
    
    private static void fillHisActivityQueryCondition(@Nullable BpmnHisActivityQuery bpmnHisActivityQuery,
                                                      HistoricActivityInstanceQuery historicActivityInstanceQuery) {
        if (bpmnHisActivityQuery == null) {
            return;
        }
        String activityId = bpmnHisActivityQuery.getActivityId();
        String processInstanceId = bpmnHisActivityQuery.getProcessInstanceId();
        String processDefinitionId = bpmnHisActivityQuery.getProcessDefinitionId();
        String executionId = bpmnHisActivityQuery.getExecutionId();
        String activityName = bpmnHisActivityQuery.getActivityName();
        String activityType = bpmnHisActivityQuery.getActivityType();
        Collection<String> activityTypes = bpmnHisActivityQuery.getActivityTypes();
        String taskAssignee = bpmnHisActivityQuery.getTaskAssignee();
        Boolean ifFinished = bpmnHisActivityQuery.getIfFinished();
        LocalDateTime startedBefore = bpmnHisActivityQuery.getStartedBefore();
        LocalDateTime startedAfter = bpmnHisActivityQuery.getStartedAfter();
        LocalDateTime finishedBefore = bpmnHisActivityQuery.getFinishedBefore();
        LocalDateTime finishedAfter = bpmnHisActivityQuery.getFinishedAfter();
        String deleteReason = bpmnHisActivityQuery.getDeleteReason();
        Pair<Boolean, ORDER_TYPE> orderByHisActivityEndTimePair =
                bpmnHisActivityQuery.getOrderByHisActivityEndTimePair();
        if (StringUtils.isNotBlank(activityId)) {
            historicActivityInstanceQuery.activityId(activityId);
        }
        if (StringUtils.isNotBlank(processInstanceId)) {
            historicActivityInstanceQuery.processInstanceId(processInstanceId);
        }
        if (StringUtils.isNotBlank(processDefinitionId)) {
            historicActivityInstanceQuery.processDefinitionId(processDefinitionId);
        }
        if (StringUtils.isNotBlank(executionId)) {
            historicActivityInstanceQuery.executionId(executionId);
        }
        if (StringUtils.isNotBlank(activityName)) {
            historicActivityInstanceQuery.activityName(activityName);
        }
        if (StringUtils.isNotBlank(activityType)) {
            historicActivityInstanceQuery.activityType(activityType);
        }
        if (StringUtils.isNotBlank(taskAssignee)) {
            historicActivityInstanceQuery.taskAssignee(taskAssignee);
        }
        if (StringUtils.isNotBlank(deleteReason)) {
            historicActivityInstanceQuery.deleteReasonLike(deleteReason + "%");
        }
        if (!CollectionUtils.isEmpty(activityTypes)) {
            historicActivityInstanceQuery.activityTypes(new HashSet<>(activityTypes));
        }
        if (startedBefore != null) {
            historicActivityInstanceQuery.startedBefore(DateTimeUtil.localDateTime2Date(startedBefore));
        }
        if (startedAfter != null) {
            historicActivityInstanceQuery.startedAfter(DateTimeUtil.localDateTime2Date(startedAfter));
        }
        if (finishedBefore != null) {
            historicActivityInstanceQuery.finishedBefore(DateTimeUtil.localDateTime2Date(finishedBefore));
        }
        if (finishedAfter != null) {
            historicActivityInstanceQuery.finishedAfter(DateTimeUtil.localDateTime2Date(finishedAfter));
        }
        if (ifFinished != null) {
            if (ifFinished) {
                historicActivityInstanceQuery.finished();
            } else {
                historicActivityInstanceQuery.unfinished();
            }
        }
        if (orderByHisActivityEndTimePair != null) {
            Boolean orderByHisActivityEndTime = orderByHisActivityEndTimePair.getLeft();
            ORDER_TYPE orderType = orderByHisActivityEndTimePair.getRight();
            if (BooleanUtils.isTrue(orderByHisActivityEndTime)) {
                historicActivityInstanceQuery.orderByHistoricActivityInstanceEndTime();
            }
            if (orderType == null || ORDER_TYPE.ASC == orderType) {
                historicActivityInstanceQuery.asc();
            } else {
                historicActivityInstanceQuery.desc();
            }
        }
    }
    
    private static void fillHisProcessInstanceCondition(@Nullable BpmnHisProcessInstanceQuery bpmnProcessQuery,
                                                        HistoricProcessInstanceQuery historicProcessInstanceQuery) {
        if (bpmnProcessQuery == null) {
            return;
        }
        String processInstanceName = bpmnProcessQuery.getProcessInstanceName();
        Collection<String> processInstanceIds = bpmnProcessQuery.getProcessInstanceIds();
        String processDefinitionId = bpmnProcessQuery.getProcessDefinitionId();
        String processDefinitionKey = bpmnProcessQuery.getProcessDefinitionKey();
        Collection<String> processDefinitionKeys = bpmnProcessQuery.getProcessDefinitionKeys();
        String processDefinitionCategory = bpmnProcessQuery.getProcessDefinitionCategory();
        String processDefinitionName = bpmnProcessQuery.getProcessDefinitionName();
        Integer processDefinitionVersion = bpmnProcessQuery.getProcessDefinitionVersion();
        String processInstanceBusinessKey = bpmnProcessQuery.getProcessInstanceBusinessKey();
        String deploymentId = bpmnProcessQuery.getDeploymentId();
        Collection<String> deploymentIds = bpmnProcessQuery.getDeploymentIds();
        String businessKey = bpmnProcessQuery.getBusinessKey();
        String businessStatus = bpmnProcessQuery.getBusinessStatus();
        Boolean ifFinished = bpmnProcessQuery.getIfFinished();
        Boolean ifDeleted = bpmnProcessQuery.getIfDeleted();
        String userId = bpmnProcessQuery.getUserId();
        Pair<String, String> userIdAndTypePair = bpmnProcessQuery.getUserIdAndTypePair();
        Collection<String> groups = bpmnProcessQuery.getGroups();
        Pair<String, String> groupIdAndTypePair = bpmnProcessQuery.getGroupIdAndTypePair();
        Collection<Pair<String, String>> variableValuePairColl = bpmnProcessQuery.getVariableValuePairColl();
        LocalDateTime startedBefore = bpmnProcessQuery.getStartedBefore();
        LocalDateTime startedAfter = bpmnProcessQuery.getStartedAfter();
        LocalDateTime finishedBefore = bpmnProcessQuery.getFinishedBefore();
        LocalDateTime finishedAfter = bpmnProcessQuery.getFinishedAfter();
        String startedBy = bpmnProcessQuery.getStartedBy();
        Boolean queryProcessVariables = bpmnProcessQuery.getQueryProcessVariables();
        if (StringUtils.isNotBlank(processInstanceName)) {
            historicProcessInstanceQuery.processInstanceNameLike(processInstanceName + "%");
        }
        if (StringUtils.isNotBlank(processDefinitionId)) {
            historicProcessInstanceQuery.processDefinitionId(processDefinitionId);
        }
        if (StringUtils.isNotBlank(businessKey)) {
            historicProcessInstanceQuery.processInstanceBusinessKey(businessKey);
        }
        if (StringUtils.isNotBlank(businessStatus)) {
            historicProcessInstanceQuery.processInstanceBusinessStatus(businessStatus);
        }
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            historicProcessInstanceQuery.processDefinitionKey(processDefinitionKey);
        }
        if (StringUtils.isNotBlank(processDefinitionCategory)) {
            historicProcessInstanceQuery.processDefinitionCategory(processDefinitionCategory);
        }
        if (StringUtils.isNotBlank(processDefinitionName)) {
            historicProcessInstanceQuery.processDefinitionName(processDefinitionName);
        }
        if (processDefinitionVersion != null) {
            historicProcessInstanceQuery.processDefinitionVersion(processDefinitionVersion);
        }
        if (StringUtils.isNotBlank(processInstanceBusinessKey)) {
            historicProcessInstanceQuery.processInstanceBusinessKey(processInstanceBusinessKey);
        }
        if (StringUtils.isNotBlank(deploymentId)) {
            historicProcessInstanceQuery.deploymentId(deploymentId);
        }
        if (StringUtils.isNotBlank(userId)) {
            historicProcessInstanceQuery.involvedUser(userId);
        }
        if (StringUtils.isNotBlank(startedBy)) {
            historicProcessInstanceQuery.startedBy(startedBy);
        }
        if (!CollectionUtils.isEmpty(processInstanceIds)) {
            historicProcessInstanceQuery.processInstanceIds(new HashSet<>(processInstanceIds));
        }
        if (!CollectionUtils.isEmpty(processDefinitionKeys)) {
            historicProcessInstanceQuery.processDefinitionKeyIn(new ArrayList<>(processDefinitionKeys));
        }
        if (!CollectionUtils.isEmpty(deploymentIds)) {
            historicProcessInstanceQuery.deploymentIdIn(new ArrayList<>(deploymentIds));
        }
        if (!CollectionUtils.isEmpty(groups)) {
            historicProcessInstanceQuery.involvedGroups(new HashSet<>(groups));
        }
        if (!CollectionUtils.isEmpty(variableValuePairColl)) {
            for (Pair<String, String> kv : variableValuePairColl) {
                String k = kv.getLeft();
                String v = kv.getRight();
                historicProcessInstanceQuery.variableValueEquals(k, v);
            }
        }
        if (startedBefore != null) {
            historicProcessInstanceQuery.startedBefore(DateTimeUtil.localDateTime2Date(startedBefore));
        }
        if (startedAfter != null) {
            historicProcessInstanceQuery.startedAfter(DateTimeUtil.localDateTime2Date(startedAfter));
        }
        if (finishedBefore != null) {
            historicProcessInstanceQuery.finishedBefore(DateTimeUtil.localDateTime2Date(finishedBefore));
        }
        if (finishedAfter != null) {
            historicProcessInstanceQuery.finishedAfter(DateTimeUtil.localDateTime2Date(finishedAfter));
        }
        if (userIdAndTypePair != null) {
            String currUserId = userIdAndTypePair.getLeft();
            String identityLinkType = userIdAndTypePair.getRight();
            if (!StringUtils.isAnyBlank(currUserId, identityLinkType)) {
                historicProcessInstanceQuery.involvedUser(currUserId, identityLinkType);
            }
        }
        if (groupIdAndTypePair != null) {
            String currGroupId = groupIdAndTypePair.getLeft();
            String identityLinkType = groupIdAndTypePair.getRight();
            if (!StringUtils.isAnyBlank(currGroupId, identityLinkType)) {
                historicProcessInstanceQuery.involvedGroup(currGroupId, identityLinkType);
            }
        }
        if (ifFinished != null) {
            if (ifFinished) {
                historicProcessInstanceQuery.finished();
            } else {
                historicProcessInstanceQuery.unfinished();
            }
        }
        if (ifDeleted != null) {
            if (ifDeleted) {
                historicProcessInstanceQuery.deleted();
            } else {
                historicProcessInstanceQuery.notDeleted();
            }
        }
        if (queryProcessVariables != null && queryProcessVariables) {
            historicProcessInstanceQuery.includeProcessVariables();
        }
    }
}
