package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * 历史变量查询
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmnHisVariableQuery {
    
    /**
     * 所属执行实例id
     */
    private String executionId;
    
    /**
     * 所属执行实例ids
     */
    private Collection<String> executionIds;
    
    /**
     * 所属任务id
     */
    private String taskId;
    
    /**
     * 所属任务ids
     */
    private Collection<String> taskIds;
    
    /**
     * 变量名
     */
    private String name;
    
    /**
     * 查询存在指定键值对的变量
     * <ul>
     *     <li>左：键</li>
     *     <li>右：值</li>
     * </ul>
     */
    @Nullable
    private Collection<Pair<String, String>> variableValuePairColl;
}
