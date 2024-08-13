package com.ideaaedi.zoo.diy.feature.bpmn.api.service;

import com.ideaaedi.zoo.commonbase.entity.PageInfo;
import com.ideaaedi.zoo.commonbase.zoo_support.RawSupport;
import com.ideaaedi.zoo.diy.feature.bpmn.api.bpmn20.model.Bpmn20UserTask;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnProcessDefinitionQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnProcessInstanceQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnProcessDefinition;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnProcessInstance;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnTask;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * 流程管理
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnProcessService extends RawSupport {
    
    /**
     * 启动流程实例
     *
     * @param tenant 租户信息
     * @param processDefinitionKey 流程定义key (一般为：bpmn文件中的process元素的id属性值)
     * @param businessKey 唯一业务标识
     * @param processVariables 流程变量（全局变量）
     *
     * @return 流程信息
     */
    @Nonnull
    default BpmnProcessInstance startInstanceByKey(@Nonnull String tenant, @Nonnull String processDefinitionKey,
                                                   @Nullable String businessKey, @Nullable Map<String, Object> processVariables) {
        return startInstanceByKey(tenant, processDefinitionKey, businessKey, processVariables, null);
    }
    
    /**
     * 启动流程实例
     *
     * @param tenant 租户信息
     * @param processDefinitionKey 流程定义key (一般为：bpmn文件中的process元素的id属性值)
     * @param businessKey 业务键标识
     * @param variables 参数
     * @param userId 流程发起人
     *
     * @return 流程信息
     */
    @Nonnull
    BpmnProcessInstance startInstanceByKey(@Nonnull String tenant, @Nonnull String processDefinitionKey,
                                           @Nullable String businessKey, @Nullable Map<String, Object> variables,
                                           @Nullable String userId);
    
    /**
     * 暂停流程实例
     *
     * @param tenant 租户
     * @param processInstanceId 流程实例id
     *
     * @return 流程信息
     */
    @Nonnull
    BpmnProcessInstance suspendInstance(@Nonnull String tenant, @Nonnull String processInstanceId);
    
    /**
     * 激活流程实例（将暂停的流程激活）
     *
     * @param tenant 租户
     * @param processInstanceId 流程实例id
     *
     * @return 流程信息
     */
    @Nonnull
    BpmnProcessInstance activateInstance(@Nonnull String tenant, @Nonnull String processInstanceId);
    
    /**
     * 删除流程实例
     *
     * @param tenant 租户
     * @param processInstanceId 流程实例id
     *
     * @return 流程信息
     */
    default BpmnProcessInstance deleteInstance(@Nonnull String tenant, @Nonnull String processInstanceId) {
        return deleteInstance(tenant, processInstanceId, null);
    }
    
    /**
     * 删除流程实例
     *
     * @param tenant 租户
     * @param processInstanceId 流程实例id
     * @param deleteReason 删除原因
     *
     * @return 流程信息
     */
    @Nonnull
    BpmnProcessInstance deleteInstance(@Nonnull String tenant, @Nonnull String processInstanceId, @Nullable String deleteReason);
    
    /**
     * 删除流程实例
     *
     * @param tenant 租户
     * @param processInstanceId 流程实例id
     * @param deleteReason 删除原因
     */
    void deleteInstanceIfExist(@Nonnull String tenant, @Nonnull String processInstanceId, @Nullable String deleteReason);
    
    /**
     * 更新流程实例业务状态
     *
     * @param processInstanceId 流程实例id
     * @param businessStatus 流程业务状态
     */
    void updateInstanceBusinessStatus(@Nonnull String tenant, @Nonnull String processInstanceId, @Nullable String businessStatus);
    
    /**
     * 流程实例列表
     *
     * @param tenant 租户
     * @param bpmnProcessQuery 查询参数
     *
     * @return 流程实例列表
     */
    @Nonnull
    List<? extends BpmnProcessInstance> listInstance(@Nonnull String tenant, @Nullable BpmnProcessInstanceQuery bpmnProcessQuery);
    
    /**
     * 流程实例分页列表
     *
     * @param tenant 租户
     * @param bpmnProcessQuery 查询参数
     * @param pageNum 页码
     * @param pageSize 每页条数
     *
     * @return 流程实例分页列表
     */
    @Nonnull
    PageInfo<? extends BpmnProcessInstance> pageInstance(@Nonnull String tenant, @Nullable BpmnProcessInstanceQuery bpmnProcessQuery, int pageNum, int pageSize);
    
    /**
     * 流程实例详情
     *
     * @param tenant 租户
     * @param processInstanceId 流程实例id
     *
     * @return 流程实例
     */
    @Nullable
    BpmnProcessInstance detailInstance(@Nonnull String tenant, @Nonnull String processInstanceId);
    
    /**
     * 流程定义列表
     *
     * @param tenant 租户
     * @param bpmnProcessQuery 查询参数
     *
     * @return 流程定义列表
     */
    @Nonnull
    List<? extends BpmnProcessDefinition> listDefinition(@Nonnull String tenant, @Nullable BpmnProcessDefinitionQuery bpmnProcessQuery);
    
    /**
     * 流程定义分页列表
     *
     * @param tenant 租户
     * @param bpmnProcessQuery 查询参数
     * @param pageNum 页码
     * @param pageSize 每页条数
     *
     * @return 流程定义分页列表
     */
    @Nonnull
    PageInfo<? extends BpmnProcessDefinition> pageDefinition(@Nonnull String tenant, @Nullable BpmnProcessDefinitionQuery bpmnProcessQuery, int pageNum, int pageSize);
    
    /**
     * 流程定义详情
     *
     * @param tenant 租户
     * @param processDefId 流程定义id
     *
     * @return 流程定义
     */
    @Nullable
    BpmnProcessDefinition detailDefinition(@Nonnull String tenant, @Nonnull String processDefId);
    
    /**
     * 从bpmn模型中查询流程定义的用户任务
     *
     * @param tenant 租户
     * @param processDefId 流程定义id
     * @param xmlProcessId bpmn模型中的流程id <br />注：bpmn xml文件中，Process元素的id属性值
     *
     * @return 流程定义的用户任务
     */
    @Nonnull
    List<Bpmn20UserTask> queryUserTaskFromBpmnModel(@Nonnull String tenant, @Nonnull String processDefId,
                                                    @Nullable String xmlProcessId);
    
    /**
     * 从bpmn模型中查询流程定义的用户任务
     *
     * @param tenant 租户
     * @param processDefId 流程定义id
     *
     * @return 流程定义的用户任务
     */
    @Nonnull
    default List<Bpmn20UserTask> queryUserTaskFromBpmnModel(@Nonnull String tenant, @Nonnull String processDefId) {
        return queryUserTaskFromBpmnModel(tenant, processDefId, null);
    }
    
    /**
     * 从bpmn模型中判断，source元素是否可达target元素
     *
     * @param tenant 租户
     * @param processDefId 流程定义id
     * @param sourceElementId 源流程元素id
     *                        <br />注：bpmn xml文件中，元素的id属性值.
     *                                 如果是{@link BpmnTask}对象的话，对应取{@link BpmnTask#getTaskDefinitionKey()}值
     * @param targetElementId 目标流程元素id
     *                        <br />注：bpmn xml文件中，元素的id属性值.
     *                                 如果是{@link BpmnTask}对象的话，对应取{@link BpmnTask#getTaskDefinitionKey()}值
     *
     * @return source元素是否可达target元素
     */
    default boolean isReachableByBpmnModel(@Nonnull String tenant, @Nonnull String processDefId,
                                            @Nonnull String sourceElementId, @Nonnull String targetElementId) {
        return isReachableByBpmnModel(tenant, processDefId, sourceElementId, targetElementId, null);
    }
    
    /**
     * 从bpmn模型中判断，source元素是否可达target元素
     *
     * @param tenant 租户
     * @param processDefId 流程定义id
     * @param sourceElementId 源流程元素id
     *                        <br />注：bpmn xml文件中，元素的id属性值.
     *                                 如果是{@link BpmnTask}对象的话，对应取{@link BpmnTask#getTaskDefinitionKey()}值
     * @param targetElementId 目标流程元素id
     *                        <br />注：bpmn xml文件中，元素的id属性值.
     *                                 如果是{@link BpmnTask}对象的话，对应取{@link BpmnTask#getTaskDefinitionKey()}值
     * @param xmlProcessId bpmn模型中的流程id
     *                        <br />注：bpmn xml文件中，Process元素的id属性值
     *
     * @return source元素是否可达target元素
     */
    boolean isReachableByBpmnModel(@Nonnull String tenant, @Nonnull String processDefId,
                                   @Nonnull String sourceElementId, @Nonnull String targetElementId,
                                   @Nullable String xmlProcessId);
}
