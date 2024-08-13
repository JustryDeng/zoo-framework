package com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.ideaaedi.zoo.commonbase.entity.PageInfo;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.BpmnDeployCreate;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnDeployment;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.DefaultBpmnDeployment;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.DefaultBpmnResource;
import com.ideaaedi.zoo.diy.feature.bpmn.api.exception.BpmnElementNotFoundException;
import com.ideaaedi.zoo.diy.feature.bpmn.api.service.BpmnDeploymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentQuery;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class FlowableDeploymentServiceImpl implements BpmnDeploymentService {
    
    private final RepositoryService repositoryService;
    
    public FlowableDeploymentServiceImpl(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }
    
    @Nonnull
    @Override
    public RepositoryService getRaw() {
        return this.repositoryService;
    }
    
    @Nonnull
    @Override
    public BpmnDeployment deploy(@Nonnull String tenant, @Nonnull BpmnDeployCreate bpmnDeployCreate) {
        Deployment deploy = repositoryService.createDeployment().tenantId(tenant)
                .addBytes(bpmnDeployCreate.getResourceName(), bpmnDeployCreate.getResourceBytes())
                .name(bpmnDeployCreate.getName())
                .category(bpmnDeployCreate.getCategory())
                .deploy();
        return DefaultBpmnDeployment.builder()
                .id(deploy.getId())
                .name(deploy.getName())
                .tenant(deploy.getTenantId())
                .category(deploy.getCategory())
                .deploymentTime(convertDeploymentTime(deploy))
                .resources(convertDefaultBpnResource(deploy))
                .raw(deploy)
                .build();
    }
    
    @Nonnull
    @Override
    public Map<String, InputStream> resources(@Nonnull String tenant, @Nonnull String deploymentId, @Nullable String... resourceNames) {
        BpmnDeployment detail = detail(tenant, deploymentId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-deployment.");
        }
        Map<String, InputStream> map = new HashMap<>();
        List<String> resourceNameList = repositoryService.getDeploymentResourceNames(deploymentId);
        if (CollectionUtils.isEmpty(resourceNameList)) {
            return map;
        }
        Set<String> resourceNameSet;
        if (resourceNames == null || resourceNames.length == 0) {
            resourceNameSet = new HashSet<>(resourceNameList);
        } else {
            resourceNameSet = Arrays.stream(resourceNames).collect(Collectors.toSet());
        }
        for (String resourceName : resourceNameSet) {
            InputStream resourceStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
            map.put(resourceName, resourceStream);
        }
        return map;
    }
    
    @Nonnull
    @Override
    public List<? extends BpmnDeployment> list(@Nonnull String tenant, @Nullable String deployName) {
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        if (StringUtils.isNotBlank(deployName)) {
            deploymentQuery.deploymentNameLike(deployName + "%");
        }
        List<Deployment> list = deploymentQuery
                .deploymentTenantIdLike(tenant + "%")
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(FlowableDeploymentServiceImpl::convertBpmnDeployment).toList();
    }
    
    @Nonnull
    @Override
    public PageInfo<? extends BpmnDeployment> page(@Nonnull String tenant, @Nullable String deployName,
                                                   int pageNum, int pageSize) {
        Assert.isTrue(pageNum >= 0, "pageNum must be >= 0");
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        if (StringUtils.isNotBlank(deployName)) {
            deploymentQuery.deploymentNameLike(deployName + "%");
        }
        deploymentQuery.deploymentTenantIdLike(tenant + "%");
        long total = deploymentQuery.count();
        if (total == 0) {
            return PageInfo.of(0, pageNum, pageSize, Collections.emptyList());
        }
        List<Deployment> list = deploymentQuery
                .listPage(pageNum * pageSize, pageSize);
        return PageInfo.of(
                total, pageNum, pageSize,
                    list.stream().map(FlowableDeploymentServiceImpl::convertBpmnDeployment).toList()
        );
    }
    
    @Nullable
    @Override
    public BpmnDeployment detail(@Nonnull String tenant, @Nonnull String deploymentId) {
        Deployment deployment = repositoryService.createDeploymentQuery()
                .deploymentId(deploymentId)
                .deploymentTenantIdLike(tenant + "%")
                .singleResult();
        return deployment == null ? null : convertBpmnDeployment(deployment);
    }
    
    @Nonnull
    @Override
    public BpmnDeployment undeploy(@Nonnull String tenant, @Nonnull String deploymentId, boolean cascade) {
        BpmnDeployment detail = detail(tenant, deploymentId);
        if (detail == null) {
            throw new BpmnElementNotFoundException("not found bpn-deployment.");
        }
        repositoryService.deleteDeployment(deploymentId, cascade);
        return detail;
    }
    
    private static LocalDateTime convertDeploymentTime(Deployment deployment) {
        return Optional.ofNullable(deployment.getDeploymentTime())
                .map(LocalDateTimeUtil::of)
                .orElse(null);
    }
    
    private static DefaultBpmnDeployment convertBpmnDeployment(Deployment deployment) {
        if (deployment == null) {
            return null;
        }
        return DefaultBpmnDeployment
                .builder()
                .id(deployment.getId())
                .name(deployment.getName())
                .tenant(deployment.getTenantId())
                .category(deployment.getCategory())
                .deploymentTime(convertDeploymentTime(deployment))
                .raw(deployment)
                .build();
    }
    
    private static Map<String, DefaultBpmnResource> convertDefaultBpnResource(Deployment deployment) {
        return Optional.ofNullable(deployment.getResources())
                .map(map -> {
                    Map<String, DefaultBpmnResource> result = new HashMap<>(map.size());
                    map.forEach((key, value) ->
                            result.put(
                                    key,
                                    DefaultBpmnResource.builder()
                                            .id(value.getName())
                                            .name(value.getName())
                                            .bytes(value.getBytes())
                                            .build()
                            )
                    );
                    return result;
                })
                .orElse(Collections.emptyMap());
    }
}
