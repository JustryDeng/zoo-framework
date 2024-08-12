package com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.impl;

import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnActivityStateUpdate;
import com.ideaaedi.zoo.diy.feature.bpmn.api.service.BpmnGoBackService;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ChangeActivityStateBuilder;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class BpmnGoBackServiceImpl implements BpmnGoBackService {
    
    private final RuntimeService runtimeService;
    
    public BpmnGoBackServiceImpl(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }
    
    @Nonnull
    @Override
    public RuntimeService getRaw() {
        return this.runtimeService;
    }
    
    @Override
    public void moveExecutionToActivityId(@Nonnull String executionId, @Nonnull String activityId, @Nullable BpmnActivityStateUpdate activityStateUpdate) {
        ChangeActivityStateBuilder changeActivityStateBuilder = runtimeService.createChangeActivityStateBuilder();
        fillChangeActivityCondition(activityStateUpdate, changeActivityStateBuilder);
        changeActivityStateBuilder.moveExecutionToActivityId(executionId, activityId);
        changeActivityStateBuilder.changeState();
    }
    
    @Override
    public void moveExecutionsToSingleActivityId(@Nonnull List<String> executionIds, @Nonnull String activityId,
                                                 @Nullable BpmnActivityStateUpdate activityStateUpdate) {
        ChangeActivityStateBuilder changeActivityStateBuilder = runtimeService.createChangeActivityStateBuilder();
        fillChangeActivityCondition(activityStateUpdate, changeActivityStateBuilder);
        changeActivityStateBuilder.moveExecutionsToSingleActivityId(executionIds, activityId);
        changeActivityStateBuilder.changeState();
    }
    
    @Override
    public void moveSingleExecutionToActivityIds(@Nonnull String executionId, @Nonnull List<String> activityId,
                                                 @Nullable BpmnActivityStateUpdate activityStateUpdate) {
        ChangeActivityStateBuilder changeActivityStateBuilder = runtimeService.createChangeActivityStateBuilder();
        fillChangeActivityCondition(activityStateUpdate, changeActivityStateBuilder);
        changeActivityStateBuilder.moveSingleExecutionToActivityIds(executionId, activityId);
        changeActivityStateBuilder.changeState();
    }
    
    @Override
    public void moveActivityIdTo(@Nonnull String currentActivityId, @Nonnull String newActivityId,
                                 @Nullable BpmnActivityStateUpdate activityStateUpdate) {
        ChangeActivityStateBuilder changeActivityStateBuilder = runtimeService.createChangeActivityStateBuilder();
        fillChangeActivityCondition(activityStateUpdate, changeActivityStateBuilder);
        changeActivityStateBuilder.moveActivityIdTo(currentActivityId, newActivityId);
        changeActivityStateBuilder.changeState();
    }
    
    @Override
    public void moveActivityIdsToSingleActivityId(@Nonnull List<String> currentActivityIds, @Nonnull String newActivityId,
                                                  @Nullable BpmnActivityStateUpdate activityStateUpdate) {
        ChangeActivityStateBuilder changeActivityStateBuilder = runtimeService.createChangeActivityStateBuilder();
        fillChangeActivityCondition(activityStateUpdate, changeActivityStateBuilder);
        changeActivityStateBuilder.moveActivityIdsToSingleActivityId(currentActivityIds, newActivityId);
        changeActivityStateBuilder.changeState();
    }
    
    @Override
    public void moveSingleActivityIdToActivityIds(@Nonnull String currentActivityId, @Nonnull List<String> newActivityIds,
                                                  @Nullable BpmnActivityStateUpdate activityStateUpdate) {
        ChangeActivityStateBuilder changeActivityStateBuilder = runtimeService.createChangeActivityStateBuilder();
        fillChangeActivityCondition(activityStateUpdate, changeActivityStateBuilder);
        changeActivityStateBuilder.moveSingleActivityIdToActivityIds(currentActivityId, newActivityIds);
        changeActivityStateBuilder.changeState();
    }
    
    @Override
    public void moveActivityIdToParentActivityId(@Nonnull String currentActivityId, @Nonnull String newActivityId,
                                                 @Nullable BpmnActivityStateUpdate activityStateUpdate) {
        ChangeActivityStateBuilder changeActivityStateBuilder = runtimeService.createChangeActivityStateBuilder();
        fillChangeActivityCondition(activityStateUpdate, changeActivityStateBuilder);
        changeActivityStateBuilder.moveActivityIdToParentActivityId(currentActivityId, newActivityId);
        changeActivityStateBuilder.changeState();
    }
    
    @Override
    public void moveActivityIdToSubProcessInstanceActivityId(@Nonnull String currentActivityId, @Nonnull String newActivityId,
                                                             @Nonnull String callActivityId, @Nullable BpmnActivityStateUpdate activityStateUpdate) {
        ChangeActivityStateBuilder changeActivityStateBuilder = runtimeService.createChangeActivityStateBuilder();
        fillChangeActivityCondition(activityStateUpdate, changeActivityStateBuilder);
        changeActivityStateBuilder.moveActivityIdToSubProcessInstanceActivityId(currentActivityId, newActivityId, callActivityId);
        changeActivityStateBuilder.changeState();
    }
    
    @Override
    public void moveActivityIdToSubProcessInstanceActivityId(@Nonnull String currentActivityId, @Nonnull String newActivityId,
                                                             @Nonnull String callActivityId, @Nonnull Integer subProcessDefinitionVersion,
                                                             @Nullable BpmnActivityStateUpdate activityStateUpdate) {
        ChangeActivityStateBuilder changeActivityStateBuilder = runtimeService.createChangeActivityStateBuilder();
        fillChangeActivityCondition(activityStateUpdate, changeActivityStateBuilder);
        changeActivityStateBuilder.moveActivityIdToSubProcessInstanceActivityId(currentActivityId, newActivityId, callActivityId, subProcessDefinitionVersion);
        changeActivityStateBuilder.changeState();
    }
    
    private static void fillChangeActivityCondition(@Nullable BpmnActivityStateUpdate activityStateUpdate,
                                                    ChangeActivityStateBuilder changeActivityStateBuilder) {
        if (activityStateUpdate == null) {
            return;
        }
        String processInstanceId = activityStateUpdate.getProcessInstanceId();
        Map<String, Object> processVariables = activityStateUpdate.getProcessVariables();
        if (StringUtils.isNotBlank(processInstanceId)) {
            changeActivityStateBuilder.processInstanceId(processInstanceId);
        }
        if (!CollectionUtils.isEmpty(processVariables)) {
            changeActivityStateBuilder.processVariables(processVariables);
        }
    }
}
