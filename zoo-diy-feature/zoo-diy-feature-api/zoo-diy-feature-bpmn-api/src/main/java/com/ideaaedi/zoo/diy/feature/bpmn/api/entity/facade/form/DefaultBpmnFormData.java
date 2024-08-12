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
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultBpmnFormData implements BpmnFormData, RawSupport, MoreSupport {
    
    private String id;
    
    private String formKey;
    
    private String formName;
    
    private String formDescription;
    
    private String tenant;
    
    private String category;
    
    private List<? extends BpmnFormProperty> formProperties;
    
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
