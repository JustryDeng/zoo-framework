package com.ideaaedi.zoo.diy.artifact.generator.code.entity;

import com.ideaaedi.zoo.diy.artifact.generator.code.config.BasicOutputConfig;
import com.ideaaedi.zoo.diy.artifact.generator.code.config.OutputConfig;
import com.ideaaedi.zoo.diy.artifact.generator.code.config.PojoConfig;
import com.ideaaedi.zoo.diy.artifact.generator.code.enums.DefaultOutputEnum;
import lombok.Data;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class PojoInfo {
    
    /**
     * 注释
     */
    private String comment;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 要import的全类名
     */
    private Set<String> importClasses = new LinkedHashSet<>(32);
    
    /**
     * 字段信息
     */
    private List<FieldInfo> fieldList = new ArrayList<>(16);
    
    /**
     * 配置
     */
    private PojoConfig pojoConfig = new PojoConfig();
    
    /**
     * 父类的类型
     * <pre>
     * {@code
     *  如：
     *    BaseDTO
     *    Holder<BaseDTO>
     * }
     * </pre>
     */
    private String superClassType;
    
    /**
     * 父接口的类型
     * <pre>
     * 如：{@code Lists.newArrayList("Holder<BaseDTO>", "BaseDTO") }
     * </pre>
     */
    private Set<String> superInterfaceTypes = new LinkedHashSet<>(4);
    
    /**
     * (non-javadoc)
     *
     * @param name 实体名
     * @param comment 注释
     * @param fieldInfos 字段
     */
    public static PojoInfo of(String name, String comment, FieldInfo... fieldInfos) {
        return of(name, comment, PojoConfig.buildOutputConfigs(DefaultOutputEnum.values()), fieldInfos);
    }
    
    /**
     * (non-javadoc)
     *
     * @param name 实体名
     * @param comment 注释
     * @param outputConfigs 输出配置
     * @param fieldInfos 字段
     */
    public static PojoInfo of(String name, String comment, @Nonnull Set<OutputConfig> outputConfigs, FieldInfo... fieldInfos) {
        PojoInfo pojoInfo = new PojoInfo();
        pojoInfo.setComment(comment);
        pojoInfo.setName(name);
        if (fieldInfos != null) {
            pojoInfo.setFieldList(List.of(fieldInfos));
        }
        PojoConfig pojoConfig = pojoInfo.getPojoConfig();
        pojoConfig.setOutputConfigs(outputConfigs);
        return pojoInfo;
    }
    
    /**
     * (non-javadoc)
     *
     * @param name 实体名
     * @param comment 注释
     * @param basicOutputConfigs 输出配置
     * @param fieldInfos 字段
     */
    public static PojoInfo of(String name, String comment, Collection<BasicOutputConfig> basicOutputConfigs, FieldInfo... fieldInfos) {
        PojoInfo pojoInfo = new PojoInfo();
        pojoInfo.setComment(comment);
        pojoInfo.setName(name);
        if (fieldInfos != null) {
            pojoInfo.setFieldList(List.of(fieldInfos));
        }
        PojoConfig pojoConfig = pojoInfo.getPojoConfig();
        pojoConfig.setOutputConfigs(PojoConfig.buildOutputConfigs(basicOutputConfigs.toArray(new BasicOutputConfig[0])));
        return pojoInfo;
    }
    
    /**
     * (non-javadoc)
     *
     * @param name 实体名
     * @param comment 注释
     * @param pojoConfig 整体配置
     * @param fieldInfos 字段
     */
    public static PojoInfo of(String name, String comment, PojoConfig pojoConfig, FieldInfo... fieldInfos) {
        PojoInfo pojoInfo = new PojoInfo();
        pojoInfo.setComment(comment);
        pojoInfo.setName(name);
        if (fieldInfos != null) {
            pojoInfo.setFieldList(List.of(fieldInfos));
        }
        Set<OutputConfig> outputConfigs = pojoConfig.getOutputConfigs();
        if (outputConfigs == null) {
            pojoConfig.setOutputConfigs(PojoConfig.buildOutputConfigs(DefaultOutputEnum.values()));
        }
        pojoInfo.setPojoConfig(pojoConfig);
        return pojoInfo;
    }
    
    /**
     * 添加父接口
     */
    public void addSupperInterfaceType(@Nonnull String... supperInterfaceTypes) {
        this.superInterfaceTypes.addAll(Arrays.stream(supperInterfaceTypes).toList());
    }
    
    /**
     * 添加导包
     */
    public void addImportClass(@Nonnull String... importClasses) {
        this.importClasses.addAll(Arrays.stream(importClasses).toList());
    }
    
    /**
     * 添加导包
     */
    public void addFields(@Nonnull FieldInfo... fields) {
        this.fieldList.addAll(Arrays.stream(fields).toList());
    }
}
