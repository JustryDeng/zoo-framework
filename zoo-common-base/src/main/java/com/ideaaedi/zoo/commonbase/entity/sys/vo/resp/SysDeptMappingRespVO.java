package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

/**
 * <p>
 * 构建部门名称path resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class SysDeptMappingRespVO {
    
    /**
     * 【原部门id path】与【缩短后的部门id path] mapping
     */
    @Schema(description = "key-部门idPath；value-(权限控制后的)部门idPath. 注：key可能与value一样")
    private Map<String, String> pathAndShrinkPathMap;
    
    /**
     * 【缩短后的部门id path】与【部门名称path]  mapping
     */
    @Schema(description = " key-部门idPath；value-部门名称path")
    private Map<String, String> shrinkPathAndNamePathMap;
    
    /**
     * 获取（可见范围内的）部门路径
     *
     * @param originDeptPath 源部门路径
     *
     * @return （可见范围内的）部门路径
     */
    public String determineShrinkPath(String originDeptPath) {
        if (pathAndShrinkPathMap == null) {
            return null;
        }
        return pathAndShrinkPathMap.get(originDeptPath);
    }
    
    /**
     * 获取（可见范围内的）部门名称路径
     *
     * @param originDeptPath 源部门路径
     *
     * @return （可见范围内的）部门名称路径
     */
    public String determineDeptPathName(String originDeptPath) {
        if (shrinkPathAndNamePathMap == null) {
            return null;
        }
        return shrinkPathAndNamePathMap.get(determineShrinkPath(originDeptPath));
    }
}