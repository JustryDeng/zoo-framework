package com.ideaaedi.zoo.diy.feature.bpmn.api.service.form;

import com.ideaaedi.zoo.commonbase.zoo_support.RawSupport;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.form.BpmnFormData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * form管理
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnFormService extends RawSupport {
    
    /**
     * 获取流程启动的表单信息
     *
     * @param tenant 租户
     * @param processDefinitionId 流程定义id
     *
     * @return 流程启动的表单信息
     */
    @Nullable
    BpmnFormData getStartFormData(@Nonnull String tenant, @Nonnull String processDefinitionId);
    
    
    /**
     * 获取任务的表单信息
     *
     * @param tenant 租户
     * @param taskId 任务id
     *
     * @return 任务的表单信息
     */
    @Nullable
    BpmnFormData getTaskFormData(@Nonnull String tenant, @Nonnull String taskId);
}
