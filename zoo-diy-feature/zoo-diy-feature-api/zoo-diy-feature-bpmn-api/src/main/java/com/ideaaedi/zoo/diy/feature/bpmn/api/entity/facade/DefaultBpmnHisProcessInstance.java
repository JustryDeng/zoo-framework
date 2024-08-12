package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;


import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideaaedi.zoo.commonbase.zoo_support.RawSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultBpmnHisProcessInstance implements BpmnHisProcessInstance, RawSupport {
    
    private String id;
    
    private String businessKey;
    
    private String businessStatus;
    
    private String processDefinitionId;
    
    private String processDefinitionName;
    
    private String processDefinitionKey;
    
    private Integer processDefinitionVersion;
    
    private String processDefinitionCategory;
    
    private String deploymentId;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private Long durationInMillis;
    
    private String startUserId;
    
    private String deleteReason;
    
    private String superProcessInstanceId;
    
    private String tenant;
    
    private String name;
    
    private String description;
    
    private Map<String, Object> processVariables;
    
    private Map<String, Object> more;
    
    @JsonIgnore
    @JSONField(serialize = false)
    private Object raw;
    
    @Nullable
    @Override
    public Map<String, Object> more() {
        return this.more;
    }
}