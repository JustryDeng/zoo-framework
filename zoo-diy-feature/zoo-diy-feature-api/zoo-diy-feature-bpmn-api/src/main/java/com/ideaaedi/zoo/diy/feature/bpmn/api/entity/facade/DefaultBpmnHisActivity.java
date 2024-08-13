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
public class DefaultBpmnHisActivity implements BpmnHisActivity, RawSupport {
    
    private LocalDateTime time;
    
    private String id;
    
    private String activityId;
    
    private String activityName;
    
    private String activityType;
    
    private String processDefinitionId;
    
    private String processInstanceId;
    
    private String executionId;
    
    private String taskId;
    
    private String calledProcessInstanceId;
    
    private String assignee;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private Long durationInMillis;
    
    private Integer transactionOrder;
    
    private String deleteReason;
    
    private String tenant;
    
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