package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * 修改活动状态相关参数
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmnActivityStateUpdate {
    
    /**
     * 被移动元素所属流程实例id
     * <p>
     * 对于flowable对工作流的实现：
     *     如果被移动元素是执行实例的话，那么可以直接从执行实例中获取到对应的流程实例id；
     *     如果被移动元素是活动实例的话，那么需要主动提供流程实例id。
     * </p>
     */
    @Nullable
    private String processInstanceId;
    
    /**
     * 添加流程变量
     */
    @Nullable
    private Map<String, Object> processVariables;
    
}
