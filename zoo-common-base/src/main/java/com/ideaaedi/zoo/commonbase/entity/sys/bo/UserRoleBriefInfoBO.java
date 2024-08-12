package com.ideaaedi.zoo.commonbase.entity.sys.bo;

import lombok.Data;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class UserRoleBriefInfoBO {
    
    /**
     * 用户id
     */
    private Long userId;
    
    /**
     * 用户拥有的角色id集合
     */
    private String roleIds;
    
    /**
     * 用户拥有的角色名称集合
     */
    private String roleNames;
}
