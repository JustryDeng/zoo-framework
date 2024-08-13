package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.form;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideaaedi.zoo.commonbase.zoo_support.MoreSupport;
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
public class DefaultBpmnFormProperty implements BpmnFormProperty, RawSupport, MoreSupport {

    private String key;
    
    private String name;
    
    private String description;
    
    private String type;
    
    private String value;
    
    private Boolean ifReadable;
    
    private Boolean ifWritable;
    
    private Boolean ifRequired;
    
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
