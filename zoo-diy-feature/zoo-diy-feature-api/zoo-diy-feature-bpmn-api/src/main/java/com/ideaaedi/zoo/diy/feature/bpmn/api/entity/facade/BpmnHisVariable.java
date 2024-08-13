package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

import com.ideaaedi.zoo.commonbase.zoo_support.MoreSupport;

import java.time.LocalDateTime;

/**
 * 相关变量
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnHisVariable extends BpmnHisDateTime, MoreSupport {
    
    /**
     * id
     *
     * @return id
     */
    String getId();
    
    /**
     * 变量名
     *
     * @return 变量名
     */
    String getVariableName();
    
    /**
     * 变量类型
     *
     * @return 变量类型
     */
    String getVariableTypeName();
    
    /**
     * 变量值
     *
     * @return 变量值
     */
    Object getValue();
    
    /**
     * 变量所属流程实例id
     *
     * @return 变量所属流程实例id
     */
    String getProcessInstanceId();
    
    /**
     * 变量所属执行实例id
     *
     * @return 变量所属执行实例id
     */
    String getExecutionId();
    
    /**
     * 变量所属任务id
     *
     * @return 变量所属任务id
     */
    String getTaskId();
    
    /**
     * 变量创建时间
     *
     * @return 变量创建时间
     */
    LocalDateTime getCreateTime();
    
    /**
     * 变量最后更新时间
     *
     * @return 变量最后更新时间
     */
    LocalDateTime getLastUpdatedTime();
}
