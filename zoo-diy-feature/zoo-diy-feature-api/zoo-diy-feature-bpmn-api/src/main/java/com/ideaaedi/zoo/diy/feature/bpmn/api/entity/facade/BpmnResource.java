package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

import com.ideaaedi.zoo.commonbase.zoo_support.MoreSupport;

/**
 * 相关资源
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnResource extends MoreSupport {
    
    /**
     * 资源id
     *
     * @return 资源id
     */
    String getId();
    
    /**
     * 资源名称
     *
     * @return 资源名称
     */
    String getName();
    
    /**
     * 资源内容
     *
     * @return 资源内容
     */
    byte[] getBytes();
    
    /**
     * 租户
     *
     * @return 租户
     */
    String getTenant();
}
