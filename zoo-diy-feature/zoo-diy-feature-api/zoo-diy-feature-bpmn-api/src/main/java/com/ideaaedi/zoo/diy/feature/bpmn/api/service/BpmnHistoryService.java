package com.ideaaedi.zoo.diy.feature.bpmn.api.service;

import com.ideaaedi.zoo.commonbase.entity.PageInfo;
import com.ideaaedi.zoo.commonbase.zoo_support.RawSupport;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnHisActivityQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnHisProcessInstanceQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnHisTaskQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnHisVariableQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnHisActivity;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnHisProcessInstance;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnHisTask;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnHisVariable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * 历史信息服务
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnHistoryService extends RawSupport {
    
    /**
     * 历史流程实例列表
     *
     * @param tenant 租户
     * @param hisProcessInstanceQuery 查询参数
     *
     * @return 历史流程实例列表
     */
    @Nonnull
    List<? extends BpmnHisProcessInstance> listHisProcessInstance(@Nonnull String tenant,
                                                                  @Nullable BpmnHisProcessInstanceQuery hisProcessInstanceQuery);
    
    /**
     * 历史流程实例分页列表
     *
     * @param tenant 租户
     * @param hisProcessInstanceQuery 查询参数
     * @param pageNum 页码
     * @param pageSize 每页条数
     *
     * @return 历史流程实例分页列表
     */
    @Nonnull
    PageInfo<? extends BpmnHisProcessInstance> pageHisProcessInstance(@Nonnull String tenant,
                                                                      @Nullable BpmnHisProcessInstanceQuery hisProcessInstanceQuery,
                                                                      int pageNum, int pageSize);
    
    /**
     * 历史流程实例详情
     *
     * @param tenant 租户
     * @param hisProcessInstanceId 历史流程实例id
     *
     * @return 历史流程实例
     */
    @Nullable
    BpmnHisProcessInstance detailHisProcessInstance(@Nonnull String tenant, @Nonnull String hisProcessInstanceId);
    
    /**
     * 历史活动列表
     *
     * @param tenant 租户
     * @param bpmnHisActivityQuery 查询参数
     *
     * @return 历史活动列表
     */
    @Nonnull
    List<? extends BpmnHisActivity> listHisActivity(@Nonnull String tenant,
                                                    @Nullable BpmnHisActivityQuery bpmnHisActivityQuery);
    
    /**
     * 历史活动分页列表
     *
     * @param tenant 租户
     * @param bpmnHisActivityQuery 查询参数
     * @param pageNum 页码
     * @param pageSize 每页条数
     *
     * @return 历史活动分页列表
     */
    @Nonnull
    PageInfo<? extends BpmnHisActivity> pageHisActivity(@Nonnull String tenant,
                                                        @Nullable BpmnHisActivityQuery bpmnHisActivityQuery,
                                                        int pageNum, int pageSize);
    
    /**
     * 历史活动详情
     *
     * @param tenant 租户
     * @param activityId 活动(实例)id. 注：这里不是历史活动的id，而是活动的id
     *
     * @return 历史活动
     */
    @Nullable
    BpmnHisActivity detailHisActivity(@Nonnull String tenant, @Nonnull String activityId);
    
    /**
     * 历史任务列表
     *
     * @param tenant 租户
     * @param bpmnHisTaskQuery 查询参数
     *
     * @return 历史任务列表
     */
    @Nonnull
    List<? extends BpmnHisTask> listHisTask(@Nonnull String tenant,
                                            @Nullable BpmnHisTaskQuery bpmnHisTaskQuery);
    
    /**
     * 历史任务分页列表
     *
     * @param tenant 租户
     * @param bpmnHisTaskQuery 查询参数
     * @param pageNum 页码
     * @param pageSize 每页条数
     *
     * @return 历史任务分页列表
     */
    @Nonnull
    PageInfo<? extends BpmnHisTask> pageHisTask(@Nonnull String tenant,
                                                @Nullable BpmnHisTaskQuery bpmnHisTaskQuery,
                                                int pageNum, int pageSize);
    
    /**
     * 历史任务详情
     *
     * @param tenant 租户
     * @param hisTaskId 历史任务id
     *
     * @return 历史任务
     */
    @Nullable
    BpmnHisTask detailHisTask(@Nonnull String tenant, @Nonnull String hisTaskId);
    
    /**
     * 历史变量列表
     * <p>
     * 注：获取此processInstanceId时就已经租户过滤了，所以这里不再过滤租户值
     * </p>
     *
     * @param processInstanceId 流程实例id
     * @param bpmnHisVariableQuery 查询参数
     *
     * @return 历史变量列表
     */
    @Nonnull
    List<? extends BpmnHisVariable> listHisVariable(@Nonnull String processInstanceId,
                                                    @Nullable BpmnHisVariableQuery bpmnHisVariableQuery);
    
    /**
     * 历史变量分页列表
     * <p>
     * 注：获取此processInstanceId时就已经租户过滤了，所以这里不再过滤租户值
     * </p>
     *
     * @param processInstanceId 流程实例id
     * @param bpmnHisVariableQuery 查询参数
     * @param pageNum 页码
     * @param pageSize 每页条数
     *
     * @return 历史变量分页列表
     */
    @Nonnull
    PageInfo<? extends BpmnHisVariable> pageHisVariable(@Nonnull String processInstanceId,
                                                        @Nullable BpmnHisVariableQuery bpmnHisVariableQuery,
                                                        int pageNum, int pageSize);
    
    /**
     * 历史变量详情
     * <p>
     * 注：获取此processInstanceId时就已经租户过滤了，所以这里不再过滤租户值
     * </p>
     *
     * @param processInstanceId 流程实例id
     * @param hisVariableId 历史变量id
     *
     * @return 历史变量
     */
    @Nullable
    BpmnHisVariable detailHisVariable(@Nonnull String processInstanceId, @Nonnull String hisVariableId);
}
