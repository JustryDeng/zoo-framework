package com.ideaaedi.zoo.commonbase.zoo_component.tenantlike;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * 数据域租户值
 * <pre>
 * 数据域层级如图所示：
 *         1
 *       /   \
 *      2     3
 *     / \   / \
 *    4   5 6   7
 *    .....
 *
 *  1. 数据域值以,结尾，形如：
 *      1,
 *      1,2,
 *      1,2,4,
 *      1,2,5,
 *      1,3,
 *      1,3,6,
 *      1,3,7,
 *  2. 处在父节点的用户，能看到自己以及子孙节点的数据。距离说明：
 *      数据域'1,'能看到的数据有： 全部
 *      数据域'1,2,'能看到的数据有： 1,2, 和 1,2,4, 和 1,2,5,
 * </pre>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface TenantScope {
    
    /**
     * 特殊标识， 当为CURD中的UPDATE READ DELETE时，忽略租户及数据隔离
     */
      String IGNORE_URD_SCOPE = "<IGNORE_TENANT-DATA-SCOPE_WHILE_URD_SCOPE>";
    
    /**
     * 用户插入（insert）时的租户值（一般为：用户所属租户）
     *
     * @return 插入（insert）时的租户值（一般为：用户所属租户）
     */
    @Nullable
    String insertTenant();
    
    /**
     * 用户可读（select）租户范围
     *
     * @return 可读（select）租户范围
     */
    @Nullable
    Collection<String> readableTenants();
    
    /**
     * 用户可写（update、delete）租户范围
     *
     * @return 可写（update、delete）租户范围
     */
    @Nullable
    Collection<String> modifiableTenants();
    
}
