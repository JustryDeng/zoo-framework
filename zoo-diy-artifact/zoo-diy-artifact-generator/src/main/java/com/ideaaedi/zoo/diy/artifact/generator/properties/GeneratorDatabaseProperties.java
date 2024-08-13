package com.ideaaedi.zoo.diy.artifact.generator.properties;

import lombok.Data;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class GeneratorDatabaseProperties {
    
    /**
     * jdbcUrl链接
     */
    private String dbJdbcUrl;
    
    /**
     * driverClassName驱动
     */
    private String dbDriverClassName;
    
    /**
     * username用户名
     */
    private String dbUsername;
    
    /**
     * password密码
     */
    private String dbPassword;
}
