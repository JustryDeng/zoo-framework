package com.ideaaedi.zoo.commonbase.entity;

import com.ideaaedi.commonds.tuple.Locator;

/**
 * entity包路径定位器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class EntityPackageLocator implements Locator<String> {
    
    private static final String packagePath = EntityPackageLocator.class.getPackage().getName();
    
    public static final EntityPackageLocator INSTANCE = new EntityPackageLocator();
    
    @Override
    public String locate() {
        return EntityPackageLocator.packagePath;
    }
}
