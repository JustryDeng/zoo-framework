package com.ideaaedi.zoo.diy.feature.bpmn.api.service.form;

import com.ideaaedi.zoo.commonbase.entity.PageInfo;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.form.BpmnFormCreate;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto.form.BpmnFormQuery;
import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.form.BpmnFormData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * form 持久化服务
 * <p>
 * 示例表单定义表结，以MYSQL为例：
 * </p>
 * <pre>
 * {@code
 * CREATE TABLE `flw_form_definition`  (
 *   `id` bigint(20)  NOT NULL COMMENT '主键ID',
 *   `form_key` varchar(255)  NOT NULL COMMENT '表单key，用于流程中引用',
 *   `name` varchar(255)  NOT NULL COMMENT '表单名称',
 *   `tenant` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户',
 *   `form_properties` json NULL COMMENT '表单中的字段',
 *   `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已删除(0-未删除；1-已删除)',
 *   `deleted_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '删除时间(时间戳，单位s；未删除时默认值为0)',
 *   `created_by` bigint(20) NOT NULL COMMENT '创建人',
 *   `created_at` datetime NOT NULL COMMENT '创建时间',
 *   `updated_by` bigint(20) NOT NULL COMMENT '修改人',
 *   `updated_at` datetime NOT NULL COMMENT '修改时间',
 *   `category` varchar(255)  NULL DEFAULT NULL COMMENT '表单类别',
 *   `description` varchar(255)  NULL DEFAULT NULL COMMENT '表单描述',
 *   PRIMARY KEY (`id`) USING BTREE,
 *   UNIQUE INDEX `uk_form_key`(`form_key` ASC, `deleted_time` ASC) USING BTREE COMMENT '表单key唯一索引'
 * ) ENGINE = InnoDB COMMENT = '表单定义表' ROW_FORMAT = Dynamic;
 * }
 * </pre>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnFormRepositoryService {
    
    /**
     * 新增表单
     *
     * @param tenant 租户
     * @param bpmnFormCreate 表单信息
     *
     * @return 表单信息
     */
    @Nonnull
    BpmnFormData create(@Nonnull String tenant, @Nonnull BpmnFormCreate bpmnFormCreate);
    
    /**
     * 删除表单
     *
     * @param formId 表单id
     *
     * @return 表单详情
     */
    @Nonnull
    BpmnFormData delete(@Nonnull String tenant, @Nonnull String formId);
    
    /**
     * 表单列表
     *
     * @param tenant 租户
     * @param bpmnFormQuery 查询条件
     *
     * @return 表单列表
     */
    @Nonnull
    List<? extends BpmnFormData> list(@Nonnull String tenant, @Nullable BpmnFormQuery bpmnFormQuery);
    
    /**
     * 表单分页列表
     *
     * @param tenant 租户
     * @param bpmnFormQuery 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     *
     * @return 表单分页列表
     */
    @Nonnull
    PageInfo<? extends BpmnFormData> page(@Nonnull String tenant, @Nullable BpmnFormQuery bpmnFormQuery,
                                          int pageNum, int pageSize);
    
    /**
     * 表单详情
     *
     * @param tenant 租户
     * @param formId 表单id
     *
     * @return 表单详情
     */
    @Nullable
    BpmnFormData detailById(@Nonnull String tenant, @Nonnull String formId);
    
    /**
     * 表单详情
     *
     * @param tenant 租户
     * @param formKey 表单key
     *
     * @return 表单详情
     */
    @Nullable
    BpmnFormData detailByKey(@Nonnull String tenant, @Nonnull String formKey);
}
