package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * 流程实例查询
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmnProcessInstanceQuery {
    
    /** 流程实例的id集合 */
    @Nullable
    private Collection<String> instanceIds;
    
    /** 流程定义的id集合 */
    @Nullable
    private Collection<String> definitionIds;
    
    /** 流程名称 */
    @Nullable
    private String name;
    
    /** 流程业务状态 */
    @Nullable
    private String businessKey;
    
    /** 流程业务状态 */
    @Nullable
    private String businessStatus;
    
    /** 部署id */
    @Nullable
    private String deploymentId;
    
    /** 部署id集合 */
    @Nullable
    private Collection<String> deploymentIds;
}
