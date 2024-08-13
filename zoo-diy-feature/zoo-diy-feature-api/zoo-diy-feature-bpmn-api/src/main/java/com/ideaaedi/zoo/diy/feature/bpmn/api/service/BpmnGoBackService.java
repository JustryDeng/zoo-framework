package com.ideaaedi.zoo.diy.feature.bpmn.api.service;

import com.ideaaedi.zoo.commonbase.zoo_support.RawSupport;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnActivityStateUpdate;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnTask;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * 回退、撤回
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnGoBackService extends RawSupport {
    
    /**
     * 由指定的执行实例id，转移到指定的活动id
     * <p>
     * 注：如果是任务的话，活动id对应任务的{@link BpmnTask#getTaskDefinitionKey()}
     * </p>
     *
     * @param executionId 当前执行实例id
     * @param activityId 目标活动id
     * @param activityStateUpdate 其它参数
     */
    void moveExecutionToActivityId(@Nonnull String executionId, @Nonnull String activityId,
                                   @Nullable BpmnActivityStateUpdate activityStateUpdate);
    
    /**
     * 由指定的执行实例ids，转移到指定的活动id
     * <p>
     * 注：常用于并行执行的场景（如：并行网关、包含网关、多实例、事件子流程等等）
     * </p>
     * <p>
     * 注：如果是任务的话，活动id对应任务的{@link BpmnTask#getTaskDefinitionKey()}
     * </p>
     *
     * @param executionIds 执行实例ids
     * @param activityId 目标活动id
     * @param activityStateUpdate 其它参数
     */
    void moveExecutionsToSingleActivityId(@Nonnull List<String> executionIds, @Nonnull String activityId,
                                          @Nullable BpmnActivityStateUpdate activityStateUpdate);
    
    /**
     * 由指定的执行实例id，转移到指定的活动ids
     * <p>
     * 注：常用于并行执行的场景（如：并行网关、包含网关、多实例、事件子流程等等）
     * </p>
     * <p>
     * 注：如果是任务的话，活动id对应任务的{@link BpmnTask#getTaskDefinitionKey()}
     * </p>
     *
     * @param executionId 执行实例id
     * @param activityId 目标活动ids
     * @param activityStateUpdate 其它参数
     */
    void moveSingleExecutionToActivityIds(@Nonnull String executionId, @Nonnull List<String> activityId,
                                          @Nullable BpmnActivityStateUpdate activityStateUpdate);
    
    /**
     * 由一个活动id，转移到另一个活动id
     * <p>
     * 注：如果是任务的话，活动id对应任务的{@link BpmnTask#getTaskDefinitionKey()}
     * </p>
     *
     * @param currentActivityId 当前活动id
     * @param newActivityId 目标活动id
     * @param activityStateUpdate 其它参数
     */
    void moveActivityIdTo(@Nonnull String currentActivityId, @Nonnull String newActivityId,
                          @Nullable BpmnActivityStateUpdate activityStateUpdate);
    
    /**
     * 将当前活动ids，转移到指定的活动id
     * <p>
     * 注：常用于并行执行的场景（如：并行网关、包含网关、多实例、事件子流程等等）
     * </p>
     * <p>
     * 注：如果是任务的话，活动id对应任务的{@link BpmnTask#getTaskDefinitionKey()}
     * </p>
     *
     * @param currentActivityIds 当前活动ids
     * @param newActivityId 目标活动id
     * @param activityStateUpdate 其它参数
     */
    void moveActivityIdsToSingleActivityId(@Nonnull List<String> currentActivityIds, @Nonnull String newActivityId,
                                           @Nullable BpmnActivityStateUpdate activityStateUpdate);
    
    /**
     * 将当前活动id，转移到指定的活动ids
     * <p>
     * 注：常用于并行执行的场景（如：并行网关、包含网关、多实例、事件子流程等等）
     * </p>
     * <p>
     * 注：如果是任务的话，活动id对应任务的{@link BpmnTask#getTaskDefinitionKey()}
     * </p>
     *
     * @param currentActivityId 当前活动id
     * @param newActivityIds 目标活动ids
     * @param activityStateUpdate 其它参数
     */
    void moveSingleActivityIdToActivityIds(@Nonnull String currentActivityId, @Nonnull List<String> newActivityIds,
                                           @Nullable BpmnActivityStateUpdate activityStateUpdate);
    
    /**
     * 将当前活动id，转移到其指定的父流程活动id
     * <p>
     * 注： 子流程实例将被终止，因此需要移动所有子流程活动实例
     * </p>
     * <p>
     * 注：如果是任务的话，活动id对应任务的{@link BpmnTask#getTaskDefinitionKey()}
     * </p>
     *
     * @param currentActivityId 当前活动id
     * @param newActivityId 父流程的目标活动id
     * @param activityStateUpdate 其它参数
     */
    void moveActivityIdToParentActivityId(@Nonnull String currentActivityId, @Nonnull String newActivityId,
                                          @Nullable BpmnActivityStateUpdate activityStateUpdate);
    
    /**
     * 将当前活动id，转移到指定子流程中的指定的活动id
     * <p>
     * 注：如果是任务的话，活动id对应任务的{@link BpmnTask#getTaskDefinitionKey()}
     * </p>
     *
     * @param currentActivityId 当前活动id
     * @param newActivityId 子流程中的目标活动id
     * @param callActivityId 拉起子流程的活动id
     *                        <br />注：CallActivity用于在一个流程中调用另一个流程
     * @param activityStateUpdate 其它参数
     */
    void moveActivityIdToSubProcessInstanceActivityId(@Nonnull String currentActivityId,
                                                      @Nonnull String newActivityId,
                                                      @Nonnull String callActivityId,
                                                      @Nullable BpmnActivityStateUpdate activityStateUpdate);
    
    /**
     * 将当前活动id，转移到指定子流程中的指定的活动id
     * <p>
     * 注：如果是任务的话，活动id对应任务的{@link BpmnTask#getTaskDefinitionKey()}
     * </p>
     *
     * @param currentActivityId 当前活动id
     * @param newActivityId 子流程中的目标活动id
     * @param callActivityId 拉起子流程的活动id
     *                        <br />注：CallActivity用于在一个流程中调用另一个流程
     * @param subProcessDefinitionVersion 子流程的流程定义版本
     * @param activityStateUpdate 其它参数
     */
    void moveActivityIdToSubProcessInstanceActivityId(@Nonnull String currentActivityId,
                                                      @Nonnull String newActivityId,
                                                      @Nonnull String callActivityId,
                                                      @Nonnull Integer subProcessDefinitionVersion,
                                                      @Nullable BpmnActivityStateUpdate activityStateUpdate);
}
