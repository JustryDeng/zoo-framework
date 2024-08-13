package com.ideaaedi.zoo.diy.artifact.dynamic.datasource;

import com.ideaaedi.zoo.diy.artifact.dynamic.datasource.properties.ZooDynamicDatasourceDIYGuideProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 动态数据源 自动配置
 */
@EnableConfigurationProperties({ZooDynamicDatasourceDIYGuideProperties.class})
public class ZooDynamicDatasourceAutoConfiguration {
}