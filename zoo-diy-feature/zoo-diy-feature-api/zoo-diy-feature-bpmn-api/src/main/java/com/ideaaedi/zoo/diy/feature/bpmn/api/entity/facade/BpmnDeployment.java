package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

import com.ideaaedi.zoo.commonbase.zoo_support.MoreSupport;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 部署
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnDeployment extends MoreSupport {
    /**
     * 部署id
     *
     * @return 部署id
     */
    String getId();
    
    /**
     * 部署名称
     *
     * @return 部署名称
     */
    String getName();
    
    /**
     * 租户
     *
     * @return 租户
     */
    String getTenant();
    
    /**
     * 分类
     *
     * @return 分类
     */
    String getCategory();
    
    /**
     * 部署时间
     *
     * @return 部署时间
     */
    LocalDateTime getDeploymentTime();
    
    /**
     * 相关资源
     *
     * @return 相关资源
     */
    Map<String, ? extends BpmnResource> getResources();
}
