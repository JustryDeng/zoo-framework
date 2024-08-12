package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

import com.ideaaedi.zoo.commonbase.zoo_support.MoreSupport;

import java.time.LocalDateTime;

/**
 * 附件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnAttachment extends MoreSupport {
    
    /**
     * id
     */
    String getId();
    
    /**
     * 附件名
     */
    String getName();
    
    void setName(String name);
    
    /**
     * 附件描述
     */
    String getDescription();
    
    void setDescription(String description);
    
    /**
     * 附件类型
     */
    String getType();
    
    /**
     * 关联的任务
     */
    String getTaskId();
    
    /**
     * 关联的流程实例
     */
    String getProcessInstanceId();
    
    /**
     * 附件上传人
     */
    String getUserId();
    
    /**
     * 附件上传时间
     */
    LocalDateTime getDateTime();
    
    void setDateTime(LocalDateTime time);
    
    /**
     * 指向附件的url
     */
    String getContentUrl();
    
    /**
     * 指向附件的id
     */
    String getContentId();
}
