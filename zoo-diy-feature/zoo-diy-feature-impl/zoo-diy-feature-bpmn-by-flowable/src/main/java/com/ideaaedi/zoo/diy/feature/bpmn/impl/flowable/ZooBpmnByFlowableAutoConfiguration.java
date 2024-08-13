package com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable;

import com.ideaaedi.zoo.diy.feature.bpmn.api.service.BpmnDeploymentService;
import com.ideaaedi.zoo.diy.feature.bpmn.api.service.BpmnGoBackService;
import com.ideaaedi.zoo.diy.feature.bpmn.api.service.BpmnHistoryService;
import com.ideaaedi.zoo.diy.feature.bpmn.api.service.BpmnProcessService;
import com.ideaaedi.zoo.diy.feature.bpmn.api.service.BpmnTaskService;
import com.ideaaedi.zoo.diy.feature.bpmn.api.service.form.BpmnFormRepositoryService;
import com.ideaaedi.zoo.diy.feature.bpmn.api.service.form.BpmnFormService;
import com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.config.ZooEngineConfigurationConfigurer;
import com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.impl.BpmnGoBackServiceImpl;
import com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.impl.FlowableDeploymentServiceImpl;
import com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.impl.FlowableHistoryServiceImpl;
import com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.impl.FlowableProcessServiceImpl;
import com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.impl.FlowableTaskServiceImpl;
import com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.impl.form.FlowableFormServiceImpl;
import com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.listener.FlowableTaskListenerWrapper;
import com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.properties.ZooBpmnByFlowableDIYGuideProperties;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * 基于flowable的工作流引擎 自动配置
 */
@Slf4j
@EnableConfigurationProperties({ZooBpmnByFlowableDIYGuideProperties.class})
public class ZooBpmnByFlowableAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public BpmnDeploymentService bpmnDeploymentService(RepositoryService repositoryService) {
        return new FlowableDeploymentServiceImpl(repositoryService);
    }
    
    /**
     * 如果你项目中需要使用{@link BpmnFormService}，那么
     * 你需要实现{@link BpmnFormRepositoryService}来增删改查表单
     */
    @Bean
    @Lazy
    @ConditionalOnMissingBean
    public BpmnFormService bpmnFormService(FormService formService, HistoryService historyService,
                                           BpmnFormRepositoryService formRepositoryService) {
        return new FlowableFormServiceImpl(formService, historyService, formRepositoryService);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public BpmnProcessService bpmnProcessService(RuntimeService runtimeService,
                                                 RepositoryService repositoryService) {
        return new FlowableProcessServiceImpl(runtimeService, repositoryService);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public BpmnTaskService bpmnTaskService(TaskService taskService) {
        return new FlowableTaskServiceImpl(taskService);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public BpmnGoBackService bpmnGoBackService(RuntimeService runtimeService) {
        return new BpmnGoBackServiceImpl(runtimeService);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public BpmnHistoryService bpmnHistoryService(HistoryService historyService) {
        return new FlowableHistoryServiceImpl(historyService);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public FlowableTaskListenerWrapper flowableBpmnTaskListenerWrapper() {
        return new FlowableTaskListenerWrapper();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ZooEngineConfigurationConfigurer zooEngineConfigurationConfigurer() {
        return new ZooEngineConfigurationConfigurer();
    }
}
