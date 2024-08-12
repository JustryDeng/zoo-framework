package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

import com.ideaaedi.zoo.commonbase.zoo_support.MoreSupport;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 任务
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnTask extends MoreSupport {
    
    /**
     * 任务id（注：也是历史任务id）
     *
     * @return 任务id
     */
    String getId();
    
    /**
     * 任务名称
     *
     * @return 任务名称
     */
    String getName();
    
    /**
     * 任务描述
     *
     * @return 任务描述
     */
    String getDescription();
    
    /**
     * 父id
     *
     * @return 父id
     */
    String getPid();
    
    /**
     * 任务分类
     *
     * @return 任务分类
     */
    String getCategory();
    
    /**
     * 所属租户
     *
     * @return 所属租户
     */
    String getTenant();
    
    /**
     * 任务状态
     *
     * @return 任务状态
     */
    String getState();
    
    /**
     * 任务截止时间
     *
     * @return 任务截止时间
     */
    LocalDateTime getDueDate();
    
    /**
     * 任务创建时间
     *
     * @return 任务创建时间
     */
    LocalDateTime getCreateTime();
    
    /**
     * 任务负责人
     *
     * @return 任务负责人
     */
    String getOwner();
    
    /**
     * 任务委托人
     *
     * @return 任务委托人
     */
    String getAssignee();
    
    /**
     * 任务所属流程实例id
     *
     * @return 任务所属流程实例id
     */
    String getProcessInstanceId();
    
    /**
     * 任务所属执行实例id
     *
     * @return 任务所属执行实例id
     */
    String getExecutionId();
    
    /**
     * 任务认领时间
     *
     * @return 任务认领时间
     */
    LocalDateTime getClaimTime();
    
    /**
     * 任务认领人
     *
     * @return 任务认领人
     */
    String getClaimedBy();
    
    /**
     * 任务暂停时间
     *
     * @return 任务暂停时间
     */
    LocalDateTime getSuspendedTime();
    
    /**
     * 任务暂停人
     *
     * @return 任务暂停人
     */
    String getSuspendedBy();
    
    /**
     * 获取任务本地变量
     * <p>
     *  注：这些变量只存在于该任务的上下文中，不影响同一流程实例中的其他任务，也不传递给后续任务。
     *      当任务完成时，除非特别配置，这些本地变量通常会被清除。
     * </p>
     *            
     * @return 任务本地变量
     */
    Map<String, Object> getTaskLocalVariables();
    
    /**
     * 获取流程变量（即：获取全局变量）
     * <p>
     *  注：这些变量在整个流程实例的生命周期内都是可见的，对于流程中的所有任务都可访问。
     *     修改这些变量会影响流程中的后续所有相关任务。
     * </p>
     *
     * @return 获取流程变量
     */
    Map<String, Object> getProcessVariables();

    /**
     * 在bpmn设计时，任务定义的唯一键
     * <p>
     *  即：在xxx.bpmn20.xml中，相关Task元素的id属性值，示例：
     *  {@code
     *  <userTask id="sid-10F6E1F2-4C30-475E-9BDF-43C313F1F66A" name=...
     *  }
     *  中的id值
     * </p>
     *
     * @return 任务定义的唯一键
     */
    String getTaskDefinitionKey();
    
}