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
public class DefaultBpmnTask implements BpmnTask, RawSupport {
    
    private String id;
    
    private String name;
    
    private String description;
    
    private String pid;
    
    private String category;
    
    private String tenant;
    
    private String state;
    
    private LocalDateTime dueDate;
    
    private LocalDateTime createTime;
    
    private String owner;
    
    private String assignee;
    
    private String processInstanceId;
    
    private String executionId;
    
    private LocalDateTime claimTime;
    
    private String claimedBy;
    
    private LocalDateTime suspendedTime;
    
    private String suspendedBy;
    
    private Map<String, Object> taskLocalVariables;
    
    private Map<String, Object> processVariables;
    
    private String taskDefinitionKey;
    
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
