package com.ideaaedi.zoo.commonbase.enums;

import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import lombok.Getter;

/**
 * 缓存枚举管理
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public enum CachePrefixEnum {
    
    AC_USER_TENANT("access-control:%s:tenant", "访问控制-用户所属租户. 占位符为userId"),
    
    AC_USER_API( "access-control:%s:apiinfo", "访问控制-用户关联的api. 占位符为userId"),
    
    AC_USER_DATA_SCOPE("access-control:%s:data-scope", "访问控制-用户关联的数据域. 占位符为userId"),
    
    ANTI_DUPLICATE("ant-duplicate:%s:%s", "防重前缀. 占位符依次为servletPath、userId"),
    ;
    
    /** 表达式 */
    private final String expression;
    
    /** 缓存说明 */
    private final String desc;
    
    CachePrefixEnum(String expression, String desc) {
        this.expression = expression;
        this.desc = desc;
    }
    
    /**
     * 获取缓存key
     *
     * @param placeholder CachePrefixEnum#expression中占位符的对应值
     *
     * @return 缓存键
     */
    public String key(Object... placeholder) {
        return BaseConstant.CACHE_NAMES + "::" + String.format(this.expression, placeholder);
    }
    
    /**
     * 获取(不带CacheName前缀的)缓存key
     *
     * @param placeholder CachePrefixEnum#expression中占位符的对应值
     *
     * @return (不带CacheName前缀的)缓存键
     */
    public String keyWithoutCacheName(Object... placeholder) {
        return String.format(this.expression, placeholder);
    }
}
