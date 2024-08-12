package com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.impl.form;

import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.form.BpmnFormData;
import com.ideaaedi.zoo.diy.feature.bpmn.api.service.form.BpmnFormRepositoryService;
import com.ideaaedi.zoo.diy.feature.bpmn.api.service.form.BpmnFormService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.form.StartFormData;
import org.flowable.task.api.history.HistoricTaskInstance;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class FlowableFormServiceImpl implements BpmnFormService {
    
    private final FormService formService;
    
    private final BpmnFormRepositoryService formRepositoryService;
    
    private final HistoryService historyService;
    
    public FlowableFormServiceImpl(FormService formService, HistoryService historyService,
                                   BpmnFormRepositoryService formRepositoryService) {
        this.formService = formService;
        this.historyService = historyService;
        this.formRepositoryService = formRepositoryService;
    }
    
    @Nonnull
    @Override
    public FormService getRaw() {
        return this.formService;
    }
    
    @Nullable
    @Override
    public BpmnFormData getStartFormData(@Nonnull String tenant, @Nonnull String processDefinitionId) {
        StartFormData startFormData = formService.getStartFormData(processDefinitionId);
        if (startFormData == null) {
            return null;
        }
        return formRepositoryService.detailByKey(tenant, startFormData.getFormKey());
    }
    
    @Nullable
    @Override
    public BpmnFormData getTaskFormData(@Nonnull String tenant, @Nonnull String taskId) {
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
                .taskId(taskId).singleResult();
        if (historicTaskInstance == null) {
            return null;
        }
        return formRepositoryService.detailByKey(tenant, historicTaskInstance.getFormKey());
    }
}
