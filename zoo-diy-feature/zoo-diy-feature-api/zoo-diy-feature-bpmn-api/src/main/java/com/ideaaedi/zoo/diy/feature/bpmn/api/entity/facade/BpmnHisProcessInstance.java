package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

import com.ideaaedi.zoo.commonbase.zoo_support.MoreSupport;

import java.time.LocalDateTime;
import java.util.Map;

public interface BpmnHisProcessInstance extends MoreSupport {
    
    /**
     * 流程实例id
     *
     * @return 流程实例id
     */
    String getId();
    
    /**
     * 与流程实例关联的唯一业务键
     *
     * @return 与流程实例关联的唯一业务键
     */
    String getBusinessKey();
    
    /**
     * 流程实例业务状态
     *
     * @return 流程实例业务状态
     */
    String getBusinessStatus();
    
    /**
     * 流程定义id
     *
     * @return 流程定义id
     */
    String getProcessDefinitionId();
    
    /**
     * 流程定义名称
     *
     * @return 流程定义名称
     */
    String getProcessDefinitionName();
    
    /**
     * 流程定义key
     *
     * @return 流程定义key
     */
    String getProcessDefinitionKey();
    
    /**
     * 流程定义版本号
     *
     * @return 流程定义版本号
     */
    Integer getProcessDefinitionVersion();
    
    /**
     * 流程定义类别
     *
     * @return 流程定义类别
     */
    String getProcessDefinitionCategory();
    
    /**
     * 所属部署id
     *
     * @return 所属部署id
     */
    String getDeploymentId();
    
    /**
     * 流程实例开始时间
     *
     * @return 流程实例开始时间
     */
    LocalDateTime getStartTime();
    
    /**
     * 流程实例结束时间
     * <p>
     * 可通过判断此字段是否为空，来判断流程是否结束
     * </p>
     *
     * @return 流程实例结束时间
     */
    LocalDateTime getEndTime();
    
    /**
     * 流程持续时间
     * <p>
     * The difference between {@link #getEndTime()} and {@link #getStartTime()}
     * </p>
     *
     * @return 流程持续时间
     */
    Long getDurationInMillis();
    
    /**
     * 流程发起人
     * <p>
     * 发起流程前使用以下方式设置当前登录用户，流程就会默认以该用户作为流程发起人
     * </p>
     * <pre>
     * {@code
     *   Authentication.setAuthenticatedUserId(userId);
     * }
     * </pre>
     *
     * @return 流程发起人
     */
    String getStartUserId();
    
    /**
     * 流程实例删除原因
     *
     * @return 流程实例删除原因
     */
    String getDeleteReason();
    
    /**
     * 父级流程实例id
     *
     * @return 父级流程实例id
     */
    String getSuperProcessInstanceId();
    
    /**
     * 流程实例所属租户
     *
     * @return 流程实例所属租户
     */
    String getTenant();
    
    /**
     * 流程实例名称
     *
     * @return 流程实例名称
     */
    String getName();
    
    /**
     * 流程实例描述
     *
     * @return 流程实例描述
     */
    String getDescription();
    
    /**
     * 流程实例相关变量
     *
     * @return 流程实例相关变量
     */
    Map<String, Object> getProcessVariables();
}
