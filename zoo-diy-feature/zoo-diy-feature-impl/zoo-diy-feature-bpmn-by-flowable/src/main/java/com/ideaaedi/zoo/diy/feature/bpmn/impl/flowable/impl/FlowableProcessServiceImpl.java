package com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.ideaaedi.zoo.commonbase.entity.PageInfo;
import com.ideaaedi.zoo.diy.feature.bpmn.api.bpmn20.model.Bpmn20SequenceFlow;
import com.ideaaedi.zoo.diy.feature.bpmn.api.bpmn20.model.Bpmn20UserTask;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnProcessDefinitionQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnProcessInstanceQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnProcessDefinition;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnProcessInstance;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.DefaultBpmnProcessDefinition;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.DefaultBpmnProcessInstance;
import com.ideaaedi.zoo.diy.feature.bpmn.api.exception.BpmnElementNotFoundException;
import com.ideaaedi.zoo.diy.feature.bpmn.api.exception.BpmnTenantNotMatchException;
import com.ideaaedi.zoo.diy.feature.bpmn.api.service.BpmnProcessService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.util.ExecutionGraphUtil;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class FlowableProcessServiceImpl implements BpmnProcessService {
    
    private final RuntimeService runtimeService;
    
    private final RepositoryService repositoryService;
    
    
    public FlowableProcessServiceImpl(RuntimeService runtimeService, RepositoryService repositoryService) {
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
    }
    
    @Nonnull
    @Override
    public Pair<RuntimeService, RepositoryService> getRaw() {
        return Pair.of(this.runtimeService, this.repositoryService);
    }
    
    @Nonnull
    @Override
    public BpmnProcessInstance startInstanceByKey(@Nonnull String tenant, @Nonnull String processDefinitionKey,
                                                  @Nullable String businessKey,
                                                  @Nullable Map<String, Object> variables, @Nullable String userId) {
        ProcessInstance processInstance;
        try {
            // Flowable发起流程时，会直接从登录态中获取当前用户作为流程发起人
            Authentication.setAuthenticatedUserId(userId);
            processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(processDefinitionKey, businessKey,
                    variables, tenant);
        } finally {
            Authentication.setAuthenticatedUserId(null);
        }
        return DefaultBpmnProcessInstance.builder()
                .id(processInstance.getId())
                .pid(processInstance.getParentId())
                .suspended(processInstance.isSuspended())
                .ended(processInstance.isEnded())
                .tenant(processInstance.getTenantId())
                .name(processInstance.getName())
                .description(processInstance.getDescription())
                .build();
    }
    
    @Nonnull
    @Override
    public BpmnProcessInstance suspendInstance(@Nonnull String tenant, @Nonnull String processInstanceId) {
        BpmnProcessInstance detail = detailInstance(tenant, processInstanceId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-process.");
        }
        runtimeService.suspendProcessInstanceById(processInstanceId);
        return detailInstance(tenant, processInstanceId);
    }
    
    @Nonnull
    @Override
    public BpmnProcessInstance activateInstance(@Nonnull String tenant, @Nonnull String processInstanceId) {
        BpmnProcessInstance detail = detailInstance(tenant, processInstanceId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-process.");
        }
        runtimeService.activateProcessInstanceById(processInstanceId);
        return detailInstance(tenant, processInstanceId);
    }
    
    @Nonnull
    @Override
    public BpmnProcessInstance deleteInstance(@Nonnull String tenant, @Nonnull String processInstanceId,
                                              @Nullable String deleteReason) {
        BpmnProcessInstance detail = detailInstance(tenant, processInstanceId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-process.");
        }
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
        return detail;
    }
    
    @Override
    public void deleteInstanceIfExist(@Nonnull String tenant, @Nonnull String processInstanceId,
                                      @Nullable String deleteReason) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if (processInstance == null) {
            return;
        }
        String dbTenant = processInstance.getTenantId();
        if (!StringUtils.startsWith(dbTenant, tenant)) {
            throw new BpmnTenantNotMatchException(
                    String.format("dbTenant -> '%s', tenant -> '%s'", dbTenant, tenant)
            );
        }
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }
    
    @Override
    public void updateInstanceBusinessStatus(@Nonnull String tenant, @Nonnull String processInstanceId,
                                             @Nullable String businessStatus) {
        BpmnProcessInstance detail = detailInstance(tenant, processInstanceId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-process.");
        }
        runtimeService.updateBusinessStatus(processInstanceId, businessStatus);
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnProcessInstance> listInstance(@Nonnull String tenant,
                                                            @Nullable BpmnProcessInstanceQuery bpmnProcessQuery) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        fillProcessInstanceCondition(bpmnProcessQuery, processInstanceQuery);
        processInstanceQuery.processInstanceTenantIdLike(tenant + "%");
        List<ProcessInstance> list = processInstanceQuery
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableProcessServiceImpl::convertBpmnProcessInstance).toList();
    }
    
    @Nonnull
    @Override
    public PageInfo<? extends BpmnProcessInstance> pageInstance(@Nonnull String tenant,
                                                                @Nullable BpmnProcessInstanceQuery bpmnProcessQuery,
                                                                int pageNum,
                                                                int pageSize) {
        Assert.isTrue(pageNum >= 0, "pageNum must be >= 0");
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        fillProcessInstanceCondition(bpmnProcessQuery, processInstanceQuery);
        processInstanceQuery.processInstanceTenantIdLike(tenant + "%");
        long total = processInstanceQuery.count();
        if (total == 0) {
            return PageInfo.of(0, pageNum, pageSize, Collections.emptyList());
        }
        List<ProcessInstance> list = processInstanceQuery.listPage(pageNum * pageSize, pageSize);
        return PageInfo.of(total, pageNum, pageSize, list.stream()
                .map(FlowableProcessServiceImpl::convertBpmnProcessInstance).toList());
    }
    
    @Nullable
    @Override
    public BpmnProcessInstance detailInstance(@Nonnull String tenant, @Nonnull String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .processInstanceTenantIdLike(tenant + "%")
                .singleResult();
        return processInstance == null ? null : convertBpmnProcessInstance(processInstance);
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnProcessDefinition> listDefinition(@Nonnull String tenant,
                                                                @Nullable BpmnProcessDefinitionQuery bpmnProcessQuery) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (bpmnProcessQuery != null) {
            String name = bpmnProcessQuery.getName();
            String deploymentId = bpmnProcessQuery.getDeploymentId();
            Collection<String> deploymentIds = bpmnProcessQuery.getDeploymentIds();
            Collection<String> definitionIds = bpmnProcessQuery.getDefinitionIds();
            if (StringUtils.isNotBlank(name)) {
                processDefinitionQuery.processDefinitionNameLike(name + "%");
            }
            if (StringUtils.isNotBlank(deploymentId)) {
                processDefinitionQuery.deploymentId(deploymentId);
            }
            if (!CollectionUtils.isEmpty(deploymentIds)) {
                processDefinitionQuery.deploymentIds(new HashSet<>(deploymentIds));
            }
            if (!CollectionUtils.isEmpty(definitionIds)) {
                processDefinitionQuery.processDefinitionIds(new HashSet<>(definitionIds));
            }
        }
        List<ProcessDefinition> list = processDefinitionQuery
                .processDefinitionTenantIdLike(tenant + "%")
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableProcessServiceImpl::convertBpmnProcessDefinition).toList();
    }
    
    @Nonnull
    @Override
    public PageInfo<? extends BpmnProcessDefinition> pageDefinition(@Nonnull String tenant,
                                                                    @Nullable BpmnProcessDefinitionQuery bpmnProcessQuery,
                                                                    int pageNum, int pageSize) {
        Assert.isTrue(pageNum >= 0, "pageNum must be >= 0");
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (bpmnProcessQuery != null) {
            String name = bpmnProcessQuery.getName();
            String deploymentId = bpmnProcessQuery.getDeploymentId();
            Collection<String> deploymentIds = bpmnProcessQuery.getDeploymentIds();
            Collection<String> definitionIds = bpmnProcessQuery.getDefinitionIds();
            if (StringUtils.isNotBlank(name)) {
                processDefinitionQuery.processDefinitionName(name + "%");
            }
            if (StringUtils.isNotBlank(deploymentId)) {
                processDefinitionQuery.deploymentId(deploymentId);
            }
            if (!CollectionUtils.isEmpty(deploymentIds)) {
                processDefinitionQuery.deploymentIds(new HashSet<>(deploymentIds));
            }
            if (!CollectionUtils.isEmpty(definitionIds)) {
                processDefinitionQuery.processDefinitionIds(new HashSet<>(definitionIds));
            }
        }
        processDefinitionQuery.processDefinitionTenantIdLike(tenant + "%");
        long total = processDefinitionQuery.count();
        if (total == 0) {
            return PageInfo.of(0, pageNum, pageSize, Collections.emptyList());
        }
        List<ProcessDefinition> list = processDefinitionQuery
                .listPage(pageNum * pageSize, pageSize);
        return PageInfo.of(total, pageNum, pageSize, list.stream()
                .map(FlowableProcessServiceImpl::convertBpmnProcessDefinition).toList());
    }
    
    @Nullable
    @Override
    public BpmnProcessDefinition detailDefinition(@Nonnull String tenant, @Nonnull String processDefId) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition processDefinition = processDefinitionQuery.processDefinitionId(processDefId)
                .processDefinitionTenantIdLike(tenant + "%")
                .singleResult();
        return processDefinition == null ? null : convertBpmnProcessDefinition(processDefinition);
    }
    
    @Nonnull
    @Override
    public List<Bpmn20UserTask> queryUserTaskFromBpmnModel(@Nonnull String tenant, @Nonnull String processDefId,
                                                           @Nullable String xmlProcessId) {
        // 获取流程定义的BpmnModel，其中包含了流程的所有节点信息
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefId);
        if (bpmnModel == null) {
            throw new BpmnElementNotFoundException("Not found BpmnModel.");
        }
        // 获取流程定义的主流程
        Process process;
        if (StringUtils.isNotBlank(xmlProcessId)) {
            process = bpmnModel.getProcessById(xmlProcessId);
        } else {
            process = bpmnModel.getMainProcess();
        }
        if (process == null) {
            throw new BpmnElementNotFoundException("Not found Process.");
        }
        
        List<Bpmn20UserTask> list = new ArrayList<>();
        // 此方法只关注用户任务，其余方法忽略
        for (FlowElement flowElement : process.getFlowElements()) {
            if (flowElement instanceof UserTask userTask) {
                Bpmn20UserTask bpmn20UserTask = new Bpmn20UserTask();
                bpmn20UserTask.setOwner(userTask.getOwner());
                bpmn20UserTask.setCategory(userTask.getCategory());
                bpmn20UserTask.setAssignee(userTask.getAssignee());
                bpmn20UserTask.setCandidateUsers(userTask.getCandidateUsers());
                bpmn20UserTask.setCandidateGroups(userTask.getCandidateGroups());
                bpmn20UserTask.setDueDate(userTask.getDueDate());
                bpmn20UserTask.setIncomingFlows(convertBpmn20SequenceFlow(userTask.getIncomingFlows()));
                bpmn20UserTask.setOutgoingFlows(convertBpmn20SequenceFlow(userTask.getOutgoingFlows()));
                bpmn20UserTask.setName(userTask.getName());
                bpmn20UserTask.setDescription(userTask.getDocumentation());
                bpmn20UserTask.setId(userTask.getId());
                bpmn20UserTask.setXmlRowNumber(userTask.getXmlRowNumber());
                bpmn20UserTask.setXmlColumnNumber(userTask.getXmlColumnNumber());
                bpmn20UserTask.setRaw(userTask);
                list.add(bpmn20UserTask);
            }
        }
        return list;
    }
    
    @Override
    public boolean isReachableByBpmnModel(@Nonnull String tenant, @Nonnull String processDefId,
                                          @Nonnull String sourceElementId, @Nonnull String targetElementId,
                                          @Nullable String xmlProcessId) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition processDefinition = processDefinitionQuery.processDefinitionId(processDefId)
                .processDefinitionTenantIdLike(tenant + "%")
                .singleResult();
        if (processDefinition == null) {
            throw new BpmnElementNotFoundException("not found process definition");
        }
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefId);
        Process mainProcess;
        if (StringUtils.isBlank(xmlProcessId)) {
            mainProcess = bpmnModel.getMainProcess();
        } else {
            mainProcess = bpmnModel.getProcessById(xmlProcessId);
        }
        FlowElement sourceElement = mainProcess.getFlowElement(sourceElementId, true);
        if (!(sourceElement instanceof FlowNode sourceFlowNode)) {
            throw new UnsupportedOperationException();
        }
        FlowElement targetElement = mainProcess.getFlowElement(targetElementId, true);
        if (!(targetElement instanceof FlowNode targetFlowNode)) {
            throw new UnsupportedOperationException();
        }
        return ExecutionGraphUtil.isReachable(mainProcess, sourceFlowNode, targetFlowNode , new HashSet<>());
    }
    
    private static List<Bpmn20SequenceFlow> convertBpmn20SequenceFlow(List<SequenceFlow> incomingFlows) {
        if (CollectionUtils.isEmpty(incomingFlows)) {
            return Collections.emptyList();
        }
        return incomingFlows.stream().map(x -> {
            Bpmn20SequenceFlow bpmn20SequenceFlow = new Bpmn20SequenceFlow();
            bpmn20SequenceFlow.setConditionExpression(x.getConditionExpression());
            bpmn20SequenceFlow.setSourceRef(x.getSourceRef());
            bpmn20SequenceFlow.setTargetRef(x.getTargetRef());
            ///FlowElement sourceFlowElement = x.getSourceFlowElement();
            ///FlowElement targetFlowElement = x.getTargetFlowElement();
            bpmn20SequenceFlow.setName(x.getName());
            bpmn20SequenceFlow.setDescription(x.getDocumentation());
            bpmn20SequenceFlow.setId(x.getId());
            bpmn20SequenceFlow.setXmlRowNumber(x.getXmlRowNumber());
            bpmn20SequenceFlow.setXmlColumnNumber(x.getXmlColumnNumber());
            return bpmn20SequenceFlow;
        }).collect(Collectors.toList());
    }
    
    private static DefaultBpmnProcessInstance convertBpmnProcessInstance(ProcessInstance processInstance) {
        if (processInstance == null) {
            return null;
        }
        return DefaultBpmnProcessInstance
                .builder()
                .id(processInstance.getId())
                .pid(processInstance.getParentId())
                .businessKey(processInstance.getBusinessKey())
                .businessStatus(processInstance.getBusinessStatus())
                .suspended(processInstance.isSuspended())
                .startUserId(processInstance.getStartUserId())
                .startTime(LocalDateTimeUtil.of(processInstance.getStartTime()))
                .ended(processInstance.isEnded())
                .tenant(processInstance.getTenantId())
                .name(processInstance.getName())
                .description(processInstance.getDescription())
                .raw(processInstance)
                .build();
    }
    
    private static DefaultBpmnProcessDefinition convertBpmnProcessDefinition(ProcessDefinition processDefinition) {
        if (processDefinition == null) {
            return null;
        }
        return DefaultBpmnProcessDefinition
                .builder()
                .id(processDefinition.getId())
                .key(processDefinition.getKey())
                .name(processDefinition.getName())
                .category(processDefinition.getCategory())
                .description(processDefinition.getDescription())
                .version(processDefinition.getVersion() + "")
                .resourceName(processDefinition.getResourceName())
                .deploymentId(processDefinition.getDeploymentId())
                .tenant(processDefinition.getTenantId())
                .ifSuspended(processDefinition.isSuspended())
                .raw(processDefinition)
                .build();
    }
    
    private static void fillProcessInstanceCondition(@Nullable BpmnProcessInstanceQuery bpmnProcessQuery,
                                                     ProcessInstanceQuery processInstanceQuery) {
        if (bpmnProcessQuery == null) {
            return;
        }
        String name = bpmnProcessQuery.getName();
        String deploymentId = bpmnProcessQuery.getDeploymentId();
        Collection<String> deploymentIds = bpmnProcessQuery.getDeploymentIds();
        Collection<String> instanceIds = bpmnProcessQuery.getInstanceIds();
        Collection<String> definitionIds = bpmnProcessQuery.getDefinitionIds();
        String businessKey = bpmnProcessQuery.getBusinessKey();
        String businessStatus = bpmnProcessQuery.getBusinessStatus();
        if (StringUtils.isNotBlank(name)) {
            processInstanceQuery.processInstanceNameLike(name + "%");
        }
        if (StringUtils.isNotBlank(businessKey)) {
            processInstanceQuery.processInstanceBusinessKey(businessKey);
        }
        if (StringUtils.isNotBlank(businessStatus)) {
            processInstanceQuery.processInstanceBusinessStatus(businessStatus);
        }
        if (StringUtils.isNotBlank(deploymentId)) {
            processInstanceQuery.deploymentId(deploymentId);
        }
        if (!CollectionUtils.isEmpty(deploymentIds)) {
            processInstanceQuery.deploymentIdIn(new ArrayList<>(deploymentIds));
        }
        if (!CollectionUtils.isEmpty(instanceIds)) {
            processInstanceQuery.processInstanceIds(new HashSet<>(instanceIds));
        }
        if (!CollectionUtils.isEmpty(definitionIds)) {
            processInstanceQuery.processDefinitionIds(new HashSet<>(definitionIds));
        }
    }
}
