package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideaaedi.zoo.commonbase.zoo_support.RawSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultBpmnProcessDefinition implements BpmnProcessDefinition, RawSupport {
    
    private String id;
    
    private String key;
    
    private String name;
    
    private String category;
    
    private String description;
    
    private String version;
    
    private String resourceName;
    
    private String deploymentId;
    
    private String tenant;
    
    private Boolean ifSuspended;
    
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
