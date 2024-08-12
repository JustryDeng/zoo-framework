package com.ideaaedi.zoo.diy.feature.msg.sms.impl.sms4j;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.ideaaedi.zoo.diy.feature.msg.api.ZooMsgApiAutoConfiguration;
import com.ideaaedi.zoo.diy.feature.msg.sms.impl.sms4j.core.SmsBlendsInitializerExt;
import com.ideaaedi.zoo.diy.feature.msg.sms.impl.sms4j.properties.ZooSmsSms4jDIYGuideProperties;
import lombok.SneakyThrows;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.comm.constant.Constant;
import org.dromara.sms4j.comm.enums.ConfigType;
import org.dromara.sms4j.core.datainterface.SmsReadConfig;
import org.dromara.sms4j.provider.config.BaseConfig;
import org.dromara.sms4j.provider.config.SmsConfig;
import org.dromara.sms4j.provider.factory.BaseProviderFactory;
import org.dromara.sms4j.starter.config.AbstractMultiConfig;
import org.dromara.sms4j.starter.config.AlibabaMultiConfig;
import org.dromara.sms4j.starter.config.CloopenMultiConfig;
import org.dromara.sms4j.starter.config.CtyunMultiConfig;
import org.dromara.sms4j.starter.config.DingzhongMultiConfig;
import org.dromara.sms4j.starter.config.EmayMultiConfig;
import org.dromara.sms4j.starter.config.HuaweiMultiConfig;
import org.dromara.sms4j.starter.config.JdcloudMultiConfig;
import org.dromara.sms4j.starter.config.LianluMultiConfig;
import org.dromara.sms4j.starter.config.NeteaseMultiConfig;
import org.dromara.sms4j.starter.config.QiniuMultiConfig;
import org.dromara.sms4j.starter.config.TencentMultiConfig;
import org.dromara.sms4j.starter.config.UnismsMultiConfig;
import org.dromara.sms4j.starter.config.YunpianMultiConfig;
import org.dromara.sms4j.starter.config.ZhutongMultiConfig;
import org.dromara.sms4j.starter.configration.SmsMainConfiguration;
import org.dromara.sms4j.starter.configration.SupplierConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


/**
 * sms4j 自动配置
 *
 * <pre>
 * 增强{@link SupplierConfiguration#smsBlendsInitializer(List, SmsConfig, Map, ObjectProvider)}方法，增强点：
 * 1.替换初始化器为{@link SmsBlendsInitializerExt}
 * 2.在{@link ZooSmsSms4jAutoConfiguration#blends(List)}方法中，获取当前对应的Constant.SUPPLIER_KEY，并放入map中；在{@link SmsBlendsInitializerExt}中会用到这个值
 * </pre>
 */
@AutoConfigureAfter({ZooMsgApiAutoConfiguration.class, SmsMainConfiguration.class})
@EnableConfigurationProperties({ZooSmsSms4jDIYGuideProperties.class,
        AlibabaMultiConfig.class, CloopenMultiConfig.class, CtyunMultiConfig.class,
        DingzhongMultiConfig.class, EmayMultiConfig.class, HuaweiMultiConfig.class, JdcloudMultiConfig.class,
        LianluMultiConfig.class, NeteaseMultiConfig.class, QiniuMultiConfig.class, TencentMultiConfig.class,
        UnismsMultiConfig.class, YunpianMultiConfig.class, ZhutongMultiConfig.class})
public class ZooSmsSms4jAutoConfiguration {
    
    @Bean
    @SneakyThrows
    @SuppressWarnings({"unchecked", "rawtypes"})
    @ConditionalOnProperty(prefix = "sms", name = "config-type", havingValue = "yaml")
    private Map<String, Map<String, Object>> blends(List<AbstractMultiConfig<?>> blends) {
        Map<String, Map<String, Object>> blendsMap = new HashMap<>();
        for (AbstractMultiConfig<?> blend : blends) {
            Class<? extends AbstractMultiConfig> clazz = blend.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            Optional<Field> first = Arrays.stream(declaredFields).filter(field -> field.getType().isAssignableFrom(Map.class)).findFirst();
            if (first.isPresent()) {
                Field field = first.get();
                field.setAccessible(Boolean.TRUE);
                Map<String, ? extends BaseConfig> configMap = (Map) field.get(blend);
                if (Objects.isNull(configMap)) {
                    continue;
                }
                for (String configId : configMap.keySet()) {
                    BaseConfig config = configMap.get(configId);
                    //将实体转换为单独属性,兼容原逻辑,更改最小
                    Map<String, Object> fieldConfigMap = JSONUtil.parseObj(config).toBean(new TypeReference<Map<String, Object>>() {
                    });
                    if (!fieldConfigMap.containsKey(Constant.SUPPLIER_KEY)) {
                        fieldConfigMap.put(Constant.SUPPLIER_KEY, config.getSupplier());
                    }
                    blendsMap.put(configId, fieldConfigMap);
                }
                
            }
        }
        return blendsMap;
    }
    
    @Bean
    @SneakyThrows
    @ConditionalOnBean({SmsConfig.class})
    @SuppressWarnings({"unchecked", "deprecation"})
    protected List<BaseProviderFactory<? extends SmsBlend, ? extends org.dromara.sms4j.api.universal.SupplierConfig>> factoryList(Map<String, Map<String, Object>> blends, SmsConfig smsConfig) {
        //注入自定义实现工厂
        List<BaseProviderFactory<? extends SmsBlend, ? extends org.dromara.sms4j.api.universal.SupplierConfig>> factoryList = new ArrayList<>();
        if (ConfigType.YAML.equals(smsConfig.getConfigType())) {
            for (String configId : blends.keySet()) {
                Map<String, Object> configMap = blends.get(configId);
                Object factoryPath = configMap.get(Constant.FACTORY_PATH);
                if (ObjectUtil.isNotEmpty(factoryPath)) {
                    //反射创建实例
                    Class<BaseProviderFactory<? extends SmsBlend, ? extends org.dromara.sms4j.api.universal.SupplierConfig>> newClass = (Class<BaseProviderFactory<? extends SmsBlend, ? extends org.dromara.sms4j.api.universal.SupplierConfig>>) Class.forName(factoryPath.toString());
                    BaseProviderFactory<? extends SmsBlend, ? extends org.dromara.sms4j.api.universal.SupplierConfig> factory = newClass.newInstance();
                    factoryList.add(factory);
                }
            }
        }
        return factoryList;
    }
    
    @Bean
    protected SmsBlendsInitializerExt smsBlendsInitializer(List<BaseProviderFactory<? extends SmsBlend, ? extends org.dromara.sms4j.api.universal.SupplierConfig>> factoryList,
                                                        SmsConfig smsConfig,
                                                        Map<String, Map<String, Object>> blends,
                                                        ObjectProvider<SmsReadConfig> extendsSmsConfigs) {
        return new SmsBlendsInitializerExt(factoryList, smsConfig, blends, extendsSmsConfigs);
    }
    
}