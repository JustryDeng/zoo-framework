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
public class DefaultBpmnProcessInstance implements BpmnProcessInstance, RawSupport {
    
    private String id;
    
    private String pid;
    
    private String businessKey;
    
    private String businessStatus;
    
    private Boolean suspended;
    
    private String startUserId;
    
    private LocalDateTime startTime;
    
    private Boolean ended;
    
    private String tenant;
    
    private String name;
    
    private String description;
    
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
