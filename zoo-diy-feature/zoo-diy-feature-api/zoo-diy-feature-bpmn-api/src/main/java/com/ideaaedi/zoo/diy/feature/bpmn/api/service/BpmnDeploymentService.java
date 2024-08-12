package com.ideaaedi.zoo.diy.feature.bpmn.api.service;

import com.ideaaedi.zoo.commonbase.entity.PageInfo;
import com.ideaaedi.zoo.commonbase.zoo_support.RawSupport;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnDeployCreate;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnDeployment;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 部署管理
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnDeploymentService extends RawSupport {
    
    /**
     * 部署
     *
     * @param tenant 租户
     * @param bpmnDeployCreate 部署信息
     *
     * @return 部署信息
     */
    @Nonnull
    BpmnDeployment deploy(@Nonnull String tenant, @Nonnull BpmnDeployCreate bpmnDeployCreate);
    
    /**
     * 获取process部署资源
     * <p>
     *     注：若resourceNames为空，则全查
     * </p>
     *
     * @param deploymentId process部署id
     * @param resourceNames process资源名称
     *
     * @return process部署信息
     */
    @Nonnull
    Map<String, InputStream> resources(@Nonnull String tenant, @Nonnull String deploymentId, @Nullable String... resourceNames);
    
    /**
     * 部署列表
     *
     * @param tenant 租户
     * @param deployName 部署名
     *
     * @return 部署表
     */
    @Nonnull
    List<? extends BpmnDeployment> list(@Nonnull String tenant, @Nullable String deployName);
    
    /**
     * 部署分页列表
     *
     * @param tenant 租户
     * @param deployName 部署名
     * @param pageNum 页码
     * @param pageSize 每页条数
     *
     * @return 部署分页列表
     */
    @Nonnull
    PageInfo<? extends BpmnDeployment> page(@Nonnull String tenant, @Nullable String deployName, int pageNum, int pageSize);
    
    /**
     * 部署详情
     *
     * @param tenant 租户
     * @param deploymentId 部署id
     *
     * @return 部署信息
     */
    @Nullable
    BpmnDeployment detail(@Nonnull String tenant, @Nonnull String deploymentId);
    
    /**
     * 卸载
     *
     * @param deploymentId 部署id
     *
     * @return 部署信息
     */
    @Nonnull
    default BpmnDeployment undeploy(@Nonnull String tenant, @Nonnull String deploymentId) {
        return undeploy(tenant, deploymentId, false);
    }
    
    /**
     * 卸载
     *
     * @param deploymentId 部署id
     * @param cascade 是否级联卸载相关流程示例、历史流程、任务等
     *
     * @return 部署信息
     */
    @Nonnull
    BpmnDeployment undeploy(@Nonnull String tenant, @Nonnull String deploymentId, boolean cascade);
}
