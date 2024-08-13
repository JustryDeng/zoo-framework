package com.ideaaedi.zoo.diy.feature.bpmn.api.service;

import com.ideaaedi.zoo.commonbase.entity.PageInfo;
import com.ideaaedi.zoo.commonbase.zoo_support.RawSupport;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnTaskQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnAttachment;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnComment;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnTask;
import com.ideaaedi.zoo.diy.feature.bpmn.api.exception.BpmnTaskAlreadyClaimedException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 资源
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnTaskService extends RawSupport {
    
    /**
     * 创建一个新的任务（，但尚未保存到数据库）
     *
     * @param tenant 租户
     * @param taskId 任务id
     *
     * @return 任务信息
     */
    @Nonnull
    BpmnTask create(@Nonnull String tenant, @Nonnull String taskId);
    
    /**
     * 保存任务实例到数据库 <br /> 注：参数bpnTask中也有租户值字段，不过这里以参数tenant的值为准
     *
     * @param tenant 租户
     * @param bpmnTask 任务
     *
     * @return 任务信息
     */
    @Nonnull
    BpmnTask save(@Nonnull String tenant, @Nonnull BpmnTask bpmnTask);
    
    /**
     * 暂停任务
     *
     * @param tenant 租户
     * @param taskId 任务id
     * @param userId 暂停任务的用户
     *
     * @return 任务信息
     */
    @Nonnull
    BpmnTask suspend(@Nonnull String tenant, @Nonnull String taskId, @Nullable String userId);
    
    /**
     * 激活任务（将暂停的任务激活）
     *
     * @param tenant 租户
     * @param taskId 任务id
     * @param userId 激活任务的用户
     *
     * @return 任务信息
     */
    @Nonnull
    BpmnTask activate(@Nonnull String tenant, @Nonnull String taskId, @Nullable String userId);
    
    /**
     * 删除任务
     *
     * @param tenant 租户
     * @param taskId 任务id
     *
     * @return 任务信息
     */
    @Nonnull
    default BpmnTask delete(@Nonnull String tenant, @Nonnull String taskId) {
        return delete(tenant, taskId, false);
    }
    
    /**
     * 删除任务
     *
     * @param tenant 租户
     * @param taskId 任务id
     * @param cascade 是否级联删除与任务相关的数据
     *
     * @return 任务信息
     */
    BpmnTask delete(@Nonnull String tenant, @Nonnull String taskId, boolean cascade);
    
    /**
     * 删除任务
     *
     * @param tenant 租户
     * @param taskIds 任务id集合
     *
     * @return 任务信息
     */
    default List<BpmnTask> delete(@Nonnull String tenant, @Nonnull Collection<String> taskIds) {
        return delete(tenant, taskIds, false);
    }
    
    /**
     * 删除任务
     *
     * @param tenant 租户
     * @param taskIds 任务id集合
     * @param cascade 是否级联删除与任务相关的数据
     *
     * @return 任务信息
     */
    List<BpmnTask> delete(@Nonnull String tenant, @Nonnull Collection<String> taskIds, boolean cascade);
    
    /**
     * 将任务委托给另一个用户
     *
     * @param tenant 租户
     * @param taskId 任务id
     * @param userId 用户id
     *
     * @return 任务信息
     */
    @Nonnull
    BpmnTask delegate(@Nonnull String tenant, @Nonnull String taskId, @Nonnull String userId);
    
    /**
     * 取消委托
     *
     * @param tenant 租户
     * @param taskId 任务id
     *
     * @return 任务信息
     */
    @Nonnull
    BpmnTask resolve(@Nonnull String tenant, @Nonnull String taskId);
    
    
    /**
     * 完成指定任务（，并传递变量更新流程实例的状态）
     *
     * @param tenant 租户
     * @param taskId 任务id
     * @param userId 完成人
     * @param processVariables 流程变量（全局变量）
     *
     * @return 任务信息
     */
    @Nonnull
    BpmnTask complete(@Nonnull String tenant, @Nonnull String taskId, @Nullable String userId,
                      @Nullable Map<String, Object> processVariables);
    
    /**
     * 认领并完成任务
     * <p>
     * 若该任务已被认领，则抛出{@link BpmnTaskAlreadyClaimedException}异常
     * </p>
     *
     * @param tenant 租户
     * @param taskId 任务id
     * @param userId 用户id
     * @param processVariables 流程变量（全局变量）
     *
     * @return 任务信息
     *
     * @throws BpmnTaskAlreadyClaimedException 当任务被认领时
     */
    @Nonnull
    default BpmnTask claimAncComplete(@Nonnull String tenant, @Nonnull String taskId, @Nonnull String userId,
                                      @Nullable Map<String, Object> processVariables) {
        claim(tenant, taskId, userId);
        return complete(tenant, taskId, userId, processVariables);
    }
    
    /**
     * 使指定用户认领任务
     * <p>
     * 1. 对于多个候选人或者候选者的情况，某人决定处理任务前，最好先认领（锁定）任务，避免并发冲突。
     *    因为完成任务需要时间，所以处理任务前最好先认领，认领后再处理任务，处理完成后再调用完成任务接口
     * 2. 若该任务已被认领，则抛出{@link BpmnTaskAlreadyClaimedException}异常
     * </p>
     *
     * @param tenant 租户
     * @param taskId 任务id
     * @param userId 用户id
     *
     * @return 任务信息
     *
     * @throws BpmnTaskAlreadyClaimedException 当任务被认领时
     */
    @Nonnull
    BpmnTask claim(@Nonnull String tenant, @Nonnull String taskId, @Nonnull String userId);
    
    /**
     * 将指定任务的被认领状态设置为未认领
     *
     * @param tenant 租户
     * @param taskId 任务id
     *
     * @return 任务信息
     */
    @Nonnull
    BpmnTask unClaim(@Nonnull String tenant, @Nonnull String taskId);
    
    /**
     * 直接设置任务的办理人（，不论该任务是否已经有办理人）
     *
     * @param tenant 租户
     * @param taskId 任务id
     * @param userId 用户id
     *
     * @return 任务信息
     */
    @Nonnull
    BpmnTask setAssignee(@Nonnull String tenant, @Nonnull String taskId, @Nonnull String userId);
    
    /**
     * 任务列表
     *
     * @param tenant 租户
     * @param bpmnTaskQuery 查询条件
     *
     * @return 任务列表
     */
    @Nonnull
    List<? extends BpmnTask> list(@Nonnull String tenant, @Nullable BpmnTaskQuery bpmnTaskQuery);
    
    /**
     * 任务分页列表
     *
     * @param tenant 租户
     * @param bpmnTaskQuery 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     *
     * @return 任务分页列表
     */
    @Nonnull
    PageInfo<? extends BpmnTask> page(@Nonnull String tenant, @Nullable BpmnTaskQuery bpmnTaskQuery, int pageNum,
                                      int pageSize);
    
    /**
     * 任务详情
     *
     * @param tenant 租户
     * @param taskId 流程id
     *
     * @return 任务
     */
    @Nullable
    BpmnTask detail(@Nonnull String tenant, @Nonnull String taskId);
    
    /**
     * 设置流程变量（全局变量）
     *
     * @param taskId （运行时的）任务id
     * @param variableName 流程变量名
     * @param value 流程变量值
     */
    void setVariableProcess(@Nonnull String taskId, @Nonnull String variableName, @Nullable Object value);
    
    /**
     * 设置流程变量（全局变量）
     *
     * @param taskId （运行时的）任务id
     * @param variables 流程变量
     */
    void setVariablesProcess(@Nonnull String taskId, @Nonnull Map<String, ?> variables);
    
    /**
     * 设置任务本地变量
     *
     * @param taskId （运行时的）任务id
     * @param variableName 本地变量名
     * @param value 本地变量值
     */
    void setVariableLocal(@Nonnull String taskId, @Nonnull String variableName, @Nullable Object value);
    
    /**
     * 设置任务本地变量
     *
     * @param taskId （运行时的）任务id
     * @param variables 本地变量
     */
    void setVariablesLocal(@Nonnull String taskId, @Nonnull Map<String, ?> variables);
    
    /**
     * 获取任务指定本地变量
     *
     * @param taskId （运行时的）任务id
     * @param variableName 本地变量名
     *
     * @return 本地变量值
     */
    @Nullable
    Object getVariableLocal(@Nonnull String taskId, @Nonnull String variableName);
    
    /**
     * 获取任务指定本地变量
     *
     * @param taskId （运行时的）任务id
     * @param variableName 本地变量名
     * @param variableClass 本地变量值类型
     *
     * @return 本地变量值
     */
    @Nullable
    <T> T getVariableLocal(@Nonnull String taskId, @Nonnull String variableName, @Nonnull Class<T> variableClass);
    
    /**
     * 获取任务本地变量
     *
     * @param taskId （运行时的）任务id
     *
     * @return 本地变量
     */
    @Nonnull
    Map<String, Object> getVariablesLocal(@Nonnull String taskId);
    
    /**
     * 获取指定流程变量
     * <pre>
     * 流程变量请尽量在查询任务时包含查询{@link BpmnTaskQuery#getQueryProcessVariables()}，然后通过{@link BpmnTask#getProcessVariables()}获取
     * </pre>
     *
     * @param taskId （运行时的）任务id
     * @param variableName 流程变量名
     *
     * @return 流程变量
     */
    @Nullable
    Object getVariableProcess(@Nonnull String taskId, @Nonnull String variableName);
    
    /**
     * 获取指定流程变量
     * <pre>
     * 流程变量请尽量在查询任务时包含查询{@link BpmnTaskQuery#getQueryProcessVariables()}，然后通过{@link BpmnTask#getProcessVariables()}获取
     * </pre>
     *
     * @param taskId （运行时的）任务id
     * @param variableName 流程变量名
     * @param variableClass 流程变量值类型
     *
     * @return 流程变量
     */
    @Nullable
    <T> T getVariableProcess(@Nonnull String taskId, @Nonnull String variableName, @Nonnull Class<T> variableClass);
    
    /**
     * 获取流程变量
     * <pre>
     * 流程变量请尽量在查询任务时包含查询{@link BpmnTaskQuery#getQueryProcessVariables()}，然后通过{@link BpmnTask#getProcessVariables()}获取
     * </pre>
     *
     * @param taskId （运行时的）任务id
     *
     * @return 流程变量
     */
    @Nonnull
    Map<String, Object> getVariablesProcess(@Nonnull String taskId);
    
    /**
     * 获取指定变量
     * <pre>
     * 注：此方法优先从本地变量中获取，本地变量中不存在则再从流程变量中获取
     * </pre>
     *
     * @param taskId （运行时的）任务id
     * @param variableName 变量名
     *
     * @return 变量值
     */
    @Nullable
    Object getVariable(@Nonnull String taskId, @Nonnull String variableName);
    
    /**
     * 获取指定流程变量
     * <pre>
     * 注：此方法优先从本地变量中获取，本地变量中不存在则再从流程变量中获取
     * </pre>
     *
     * @param taskId （运行时的）任务id
     * @param variableName 变量名
     * @param variableClass 变量值类型
     *
     * @return 流程变量值
     */
    @Nullable
    <T> T getVariable(@Nonnull String taskId, @Nonnull String variableName, @Nonnull Class<T> variableClass);
    
    /**
     * 获取变量（流程变量 + 本地变量）
     * <pre>
     * 注：若本地变量中存在与流程变量中相同的key，那么这里返回的是本地变量中的值。
     * </pre>
     *
     * @param taskId （运行时的）任务id
     *
     * @return 流程变量
     */
    @Nonnull
    Map<String, Object> getVariables(@Nonnull String taskId);
    
    /**
     * 添加（关联指定任务、指定流程实例的）评论
     *
     * @param taskId 任务id
     * @param processInstanceId 流程实例id
     * @param message 评论
     *
     * @return 评论
     */
    @Nonnull
    BpmnComment addComment(@Nullable String taskId, @Nullable String processInstanceId, @Nonnull String message);
    
    /**
     * 添加（关联指定任务、指定流程实例的）评论
     *
     * @param taskId 任务id
     * @param processInstanceId 流程实例id
     * @param type 评论类型
     * @param message 评论
     *
     * @return 评论
     */
    @Nonnull
    BpmnComment addComment(@Nullable String taskId, @Nullable String processInstanceId, @Nullable String type, @Nonnull String message);
    
    /**
     * 删除（关联指定任务、指定流程实例的）评论
     *
     * @param taskId 任务id
     * @param processInstanceId 流程实例id
     */
    void deleteComments(@Nullable String taskId, @Nullable String processInstanceId);
    
    /**
     * 删除指定评论
     *
     * @param commentId 评论id
     */
    void deleteComment(@Nonnull String commentId);

    /**
     * 根据评论id，修改评论
     *
     * @param comment 任务评论
     */
    void updateCommentById(@Nonnull BpmnComment comment);
    
    /**
     * 获取评论
     *
     * @param commentId 评论id
     */
    @Nullable
    BpmnComment getComment(@Nonnull String commentId);
    
    /**
     * 获取任务关联的评论
     *
     * @param taskId 任务id
     *
     * @return 任务关联的评论
     */
    @Nonnull
    List<? extends BpmnComment> getTaskComments(String taskId);
    
    /**
     * 获取任务关联的指定类型的评论
     *
     * @param taskId 任务id
     * @param type 评论类型
     *
     * @return 任务关联的指定类型的评论
     */
    @Nonnull
    List<? extends BpmnComment> getTaskComments(@Nonnull String taskId, @Nonnull String type);
    
    /**
     * 获取指定类型的评论
     *
     * @param type 评论类型
     *
     * @return 指定类型的评论
     */
    @Nonnull
    List<? extends BpmnComment> getCommentsByType(@Nonnull String type);
    
    /**
     * 获取流程实例关联的评论
     *
     * @param processInstanceId 流程实例id
     *
     * @return 流程实例关联的评论
     */
    @Nonnull
    List<? extends BpmnComment> getProcessInstanceComments(@Nonnull String processInstanceId);
    
    /**
     * 获取流程实例关联的指定类型的评论
     *
     * @param processInstanceId 流程实例id
     * @param type 评论类型
     *
     * @return 流程实例关联的指定类型的评论
     */
    @Nonnull
    List<? extends BpmnComment> getProcessInstanceComments(@Nonnull String processInstanceId, @Nonnull String type);
    
    /**
     * 以附件内容的形式，添加（关联指定任务、指定流程实例的）附件
     *
     * @param attachmentType 附件类型
     * @param taskId 关联的任务id
     * @param processInstanceId 关联的流程实例id
     * @param attachmentName 附件名
     * @param attachmentDescription 附件描述
     * @param contentBytes 附件内容
     *
     * @return 添加的附件
     */
    @Nonnull
    BpmnAttachment addAttachment(@Nonnull String attachmentName, @Nullable String attachmentType,
                                 @Nullable String attachmentDescription,
                                 @Nullable String taskId, @Nullable String processInstanceId,
                                 @Nonnull byte[] contentBytes);
    
    /**
     * 以附件url的形式，添加（关联指定任务、指定流程实例的）附件
     *
     * @param attachmentType 附件类型
     * @param taskId 关联的任务id
     * @param processInstanceId 关联的流程实例id
     * @param attachmentName 附件名
     * @param attachmentDescription 附件描述
     * @param url 指向附件内容的url
     *
     * @return 添加的附件
     */
    @Nonnull
    BpmnAttachment addAttachment(@Nonnull String attachmentName, @Nullable String attachmentType,
                                 @Nullable String attachmentDescription,
                                 @Nullable String taskId, @Nullable String processInstanceId,
                                 @Nonnull String url);
    
    /**
     * 删除指定附件
     *
     * @param attachmentId 附件id
     */
    void deleteAttachment(@Nonnull String attachmentId);
    
    /**
     * 修改附件名称、附件描述
     *
     * @param attachment 附件id
     */
    void updateAttachmentById(@Nonnull BpmnAttachment attachment);
    
    /**
     * 获取附件
     *
     * @param attachmentId 附件id
     *
     * @return 附件
     */
    @Nullable
    BpmnAttachment getAttachment(@Nonnull String attachmentId);
    
    /**
     * 获取附件内容
     *
     * @param attachmentId 附件id
     *
     * @return 附件内容
     */
    @Nullable
    InputStream getAttachmentContent(@Nonnull String attachmentId);
    
    /**
     * 获取任务关联的附件
     *
     * @param taskId 任务id
     *
     * @return 任务关联的附件
     */
    @Nonnull
    List<? extends BpmnAttachment> getTaskAttachments(@Nonnull String taskId);
    
    /**
     * 获取流程示例关联的附件
     *
     * @param processInstanceId 流程实例id
     *
     * @return 任务关联的附件
     */
    @Nonnull
    List<? extends BpmnAttachment> getProcessInstanceAttachments(@Nonnull String processInstanceId);
    
}
