package com.ideaaedi.zoo.diy.artifact.generator.properties;

import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.alibaba.excel.annotation.ExcelProperty;
import com.google.common.collect.Lists;
import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import com.ideaaedi.zoo.commonbase.entity.BasePageDTO;
import com.ideaaedi.zoo.commonbase.entity.PageInfo;
import com.ideaaedi.zoo.commonbase.entity.Result;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.ZooKnife4jAutoConfiguration;
import com.ideaaedi.zoo.diy.artifact.mybatisplus.injector.MybatisPlusSqlInjector;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Nullable;

/**
 * sse相关配置
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Validated
@ConfigurationProperties(prefix = "zoo.generator")
public class ZooGeneratorProperties {
    
    @Valid
    @NestedConfigurationProperty
    private DatabaseDoc databaseDoc = new DatabaseDoc();
    
    @Valid
    @NestedConfigurationProperty
    private JavaCurdCode javaCurdCode = new JavaCurdCode();
    
    /**
     * java curd代码生成器配置
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class JavaCurdCode extends GeneratorDatabaseProperties implements Cloneable {
        /**
         * 输出目录
         */
        @NotBlank
        private String outputDir = "./generator/java_code_curl";

        /**
         * 父包名（为空则自动取当前调用者的所在包）
         */
        @Nullable
        private String packagePath;
        
        /**
         * since
         * <pre>
         * 如果想获取动态日期，可以这么填，如：
         * yyyy-MM-dd
         * yyyy-MM-dd HH:mm:ss
         *
         * 如果想设置静态值，可以用引号引起来，如：
         * '1.0.0'
         * </pre>
         */
        @NotBlank
        private String javadocSince = "'1.0.0'";
        
        /**
         * 作者
         */
        @NotBlank
        private String javadocAuthor = """
                <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
                """;
    
        /**
         * true-使用自定义模板; false-使用默认模板
         */
        private boolean useCustomTemplate = true;
    
        /**
         * 模板所在目录
         * <pre>
         * 注：只有当{@link #useCustomTemplate}为true时，此参数才生效
         */
        private String templateClasspathDir = "/templates/default-java-curd/";
        
        /**
         * 请求体基类全类名
         * <pre>
         * 注：只有当{@link #useCustomTemplate}为true时，此参数才生效
         * </pre>
         */
        private String baseDtoClassName = BaseDTO.class.getName();
    
        /**
         * 分页请求体基类全类名
         * <pre>
         * 注：只有当{@link #useCustomTemplate}为true时，此参数才生效
         * </pre>
         */
        private String basePageDtoClassName = BasePageDTO.class.getName();
    
        /**
         * 分页响应体全类名
         * <pre>
         * 注：只有当{@link #useCustomTemplate}为true时，此参数才生效
         * </pre>
         */
        private String pageInfoClassName = PageInfo.class.getName();
    
        /**
         * 统一返回类 全类名
         * <pre>
         * 注：只有当{@link #useCustomTemplate}为true时，此参数才生效
         * </pre>
         */
        private String resultClassName = Result.class.getName();
        
        /**
         * true-controller的返回模型用Result.success()包装; false-不包装
         * <pre>
         * 注：只有当{@link #useCustomTemplate}为true时，此参数才生效
         * </pre>
         */
        private boolean wrapControllerResult = true;
    
        /**
         * 是否在PojoListRespVO模型的字段上添加@ExcelProperty注解
         * <pre>
         * 注：只有当{@link #useCustomTemplate}=true && {@link  #existEasyExcel}=true时，此参数才生效
         * </pre>
         */
        private boolean ifAddExcelPropertyAnno = true;
        
        /**
         * 是否存在excel
         * <p>
         * 若值为空，则自动以应用中是否存在{@link ExcelProperty}类作依据进行推断
         * </p>
         */
        private Boolean existEasyExcel;
        
        /**
         * 是否存在mybatis-plus-ext
         * <p>
         * 若值为空，则自动以spring中是否存在{@link MybatisPlusSqlInjector}作依据进行推断
         * </p>
         */
        private Boolean existMybatisPlusExt;
        
        /**
         * 是否存在Knife4j-ext
         * <p>
         * 若值为空，则自动以spring中是否存在{@link ZooKnife4jAutoConfiguration}作依据进行推断
         * </p>
         */
        private Boolean existKnife4jExt;
    
        @Override
        @SuppressWarnings("MethodDoesntCallSuperMethod")
        public JavaCurdCode clone() {
            JavaCurdCode clone = new JavaCurdCode();
            BeanUtils.copyProperties(this, clone);
             return clone;
        }
    }
    
    /**
     * 数据库文档生成器配置
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class DatabaseDoc extends GeneratorDatabaseProperties implements Cloneable {
        
        /**
         * 组织
         */
        private String organization;
        
        /**
         * url
         */
        private String organizationUrl;
        
        /**
         * 标题
         */
        private String title;
        
        /**
         * 版本号
         */
        @NotBlank
        private String version = "1.0.0";
        
        /**
         * 描述
         */
        @NotBlank
        private String description = "数据库设计文档";
        
        /**
         * 生成配置
         */
        @NotNull
        @NestedConfigurationProperty
        private ProcessConfig produceConfig = ProcessConfig.builder()
                // 指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
                // 根据名称指定表生成
                .designatedTableName(Lists.newArrayList())
                // 根据表前缀生成
                .designatedTablePrefix(Lists.newArrayList())
                // 根据表后缀生成
                .designatedTableSuffix(Lists.newArrayList())
                // 忽略指定名称的表
                .ignoreTableName(Lists.newArrayList())
                // 忽略指定名称前缀的表
                .ignoreTablePrefix(Lists.newArrayList())
                // 忽略指定名称后缀的表
                .ignoreTableSuffix(Lists.newArrayList())
                .build();
        
        /**
         * 引擎配置，关于数据库文档生成相关配置
         */
        @NotNull
        @NestedConfigurationProperty
        private EngineConfig engineConfig = EngineConfig.builder()
                // 生成文件存放的目录
                .fileOutputDir("./generator/database_doc")
                // 生成完成是否打开输出目录
                .openOutputDir(false)
                // 生成文件类型：HTML-> .html，WORD-> .doc、MD-> .md
                .fileType(EngineFileType.HTML)
                // 生成模板实现
                .produceType(EngineTemplateType.freemarker)
                // 自定义文件名称
                .fileName("库表说明")
                .build();
    
        @Override
        @SuppressWarnings("MethodDoesntCallSuperMethod")
        public DatabaseDoc clone() {
            EngineConfig cloneEngineConfig = EngineConfig.builder().build();
            BeanUtils.copyProperties(this.engineConfig, cloneEngineConfig);
            
            ProcessConfig cloneProcessConfig = ProcessConfig.builder().build();
            BeanUtils.copyProperties(this.produceConfig, cloneProcessConfig);
    
            DatabaseDoc clone = new DatabaseDoc();
            BeanUtils.copyProperties(this, clone, "engineConfig", "produceConfig");
            clone.setEngineConfig(cloneEngineConfig);
            clone.setProduceConfig(cloneProcessConfig);
            return clone;
        }
    }
}
