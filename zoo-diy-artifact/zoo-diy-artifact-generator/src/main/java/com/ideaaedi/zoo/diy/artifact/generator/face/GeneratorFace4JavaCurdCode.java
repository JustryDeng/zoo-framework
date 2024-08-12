package com.ideaaedi.zoo.diy.artifact.generator.face;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.ideaaedi.commonds.constants.SymbolConstant;
import com.ideaaedi.commonds.env.Env;
import com.ideaaedi.commonds.env.RequiredEnv;
import com.ideaaedi.commonds.env.Unit;
import com.ideaaedi.zoo.commonbase.zoo_face.Face;
import com.ideaaedi.zoo.diy.artifact.generator.code.Generator;
import com.ideaaedi.zoo.diy.artifact.generator.face.engine.EnhanceFreemarkerTemplateEngine;
import com.ideaaedi.zoo.diy.artifact.generator.properties.ZooGeneratorProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * java 增删改查代码生成器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class GeneratorFace4JavaCurdCode implements Generator, Face {
    
    static ZooGeneratorProperties.JavaCurdCode javaCurdCode;
    
    @Override
    public String generateDesc() {
        return "this is a generator for java curd database table code";
    }
    
    /**
     * 生成java代码
     *
     * @param tables 要生成的表名（不传则全部生成）
     *
     * @see #generate(ZooGeneratorProperties.JavaCurdCode, Consumer, Function, Collection)
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static void generate(@Nullable Collection<String> tables) {
        ZooGeneratorProperties.JavaCurdCode clone = GeneratorFace4JavaCurdCode.javaCurdCode.clone();
        Objects.requireNonNull(clone.getDbJdbcUrl(),
                "miss config 'zoo.generator.java-curd-code.db-jdbc-url'");
        Objects.requireNonNull(clone.getDbUsername(),
                "miss config 'zoo.generator.java-curd-code.db-password'");
        Objects.requireNonNull(clone.getDbPassword(),
                "miss config 'zoo.generator.java-curd-code.db-username'");
        generate(clone, null, null, tables);
    }
    
    /**
     * 生成java代码
     *
     * @param targetFileOutputDir 指定输出位置
     * @param tables 要生成的表名（不传则全部生成）
     *
     * @see #generate(ZooGeneratorProperties.JavaCurdCode, Consumer, Function, Collection)
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static void generate(String targetFileOutputDir, @Nullable Collection<String> tables) {
        ZooGeneratorProperties.JavaCurdCode clone = GeneratorFace4JavaCurdCode.javaCurdCode.clone();
        Objects.requireNonNull(clone.getDbJdbcUrl(),
                "miss config 'zoo.generator.java-curd-code.db-jdbc-url'");
        Objects.requireNonNull(clone.getDbUsername(),
                "miss config 'zoo.generator.java-curd-code.db-password'");
        Objects.requireNonNull(clone.getDbPassword(),
                "miss config 'zoo.generator.java-curd-code.db-username'");
        generate(clone, (javaCurdCode) -> {
            javaCurdCode.setOutputDir(targetFileOutputDir);
        }, null, tables);
    }
    
    /**
     * 生成java代码
     *
     * @param targetFileOutputDir 指定输出位置
     * @param packagePath 指定包名
     * @param tables 要生成的表名（不传则全部生成）
     *
     * @see #generate(ZooGeneratorProperties.JavaCurdCode, Consumer, Function, Collection)
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static void generate(String targetFileOutputDir, String packagePath, @Nullable Collection<String> tables) {
        ZooGeneratorProperties.JavaCurdCode clone = GeneratorFace4JavaCurdCode.javaCurdCode.clone();
        Objects.requireNonNull(clone.getDbJdbcUrl(),
                "miss config 'zoo.generator.java-curd-code.db-jdbc-url'");
        Objects.requireNonNull(clone.getDbUsername(),
                "miss config 'zoo.generator.java-curd-code.db-password'");
        Objects.requireNonNull(clone.getDbPassword(),
                "miss config 'zoo.generator.java-curd-code.db-username'");
        generate(clone, (javaCurdCode) -> {
            javaCurdCode.setOutputDir(targetFileOutputDir);
            javaCurdCode.setPackagePath(packagePath);
        }, null, tables);
    }
    
    /**
     * 生成java代码
     *
     * @see #generate(ZooGeneratorProperties.JavaCurdCode, Consumer, Function, Collection)
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static void generate(@Nonnull Consumer<ZooGeneratorProperties.JavaCurdCode> consumer, @Nullable Collection<String> tables) {
        ZooGeneratorProperties.JavaCurdCode clone = GeneratorFace4JavaCurdCode.javaCurdCode.clone();
        Objects.requireNonNull(clone.getDbJdbcUrl(),
                "miss config 'zoo.generator.java-curd-code.db-jdbc-url'");
        Objects.requireNonNull(clone.getDbUsername(),
                "miss config 'zoo.generator.java-curd-code.db-password'");
        Objects.requireNonNull(clone.getDbPassword(),
                "miss config 'zoo.generator.java-curd-code.db-username'");
        generate(clone, consumer, null, tables);
    }
    
    /**
     * 生成java curd代码
     *
     * @see #generate(ZooGeneratorProperties.JavaCurdCode, Consumer, Function, Collection)
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static void generate(@Nonnull ZooGeneratorProperties.JavaCurdCode javaCurdCode,
                                @Nullable Collection<String> tables) {
        generate(javaCurdCode, null, null, tables);
    }
    
    /**
     * 生成java curd代码
     *
     * @param javaCurdCode 相关配置
     * @param consumer 配置定制器
     * @param function 工厂定制器
     * @param tables 要生成的表名（不传则全部生成）
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static void generate(@Nonnull ZooGeneratorProperties.JavaCurdCode javaCurdCode,
                                @Nullable Consumer<ZooGeneratorProperties.JavaCurdCode> consumer,
                                @Nullable Function<FastAutoGenerator, FastAutoGenerator> function,
                                @Nullable Collection<String> tables) {
        if (consumer != null) {
            consumer.accept(javaCurdCode);
        }
        FastAutoGenerator fastAutoGenerator = defaultFastAutoGenerator(javaCurdCode, tables);
        if (function != null) {
            fastAutoGenerator = function.apply(fastAutoGenerator);
        }
        fastAutoGenerator.execute();
    }
    
    @Nonnull
    private static FastAutoGenerator defaultFastAutoGenerator(@Nonnull ZooGeneratorProperties.JavaCurdCode javaCurdCode, @Nullable Collection<String> tables) {
        String dbJdbcUrl = Objects.requireNonNull(javaCurdCode.getDbJdbcUrl(), "dbJdbcUrl cannot be null.");
        String dbUsername = Objects.requireNonNull(javaCurdCode.getDbUsername(), "dbUsername cannot be null.");
        String dbPassword = Objects.requireNonNull(javaCurdCode.getDbPassword(), "dbPassword cannot be null.");
        String packagePath = javaCurdCode.getPackagePath();
        if (StrUtil.isBlank(packagePath)) {
            packagePath = obtainCallerPackageName();
            if (packagePath != null) {
                // 生成到调用者下面的generator包下
                packagePath = packagePath + "." + "generator";
            }
        }
        Objects.requireNonNull(packagePath, "packagePath cannot be null.");
        String finalPackagePath = packagePath;
        boolean useCustomTemplate = javaCurdCode.isUseCustomTemplate();
        if (useCustomTemplate) {
            String templateClasspathDir = javaCurdCode.getTemplateClasspathDir();
            Objects.requireNonNull(templateClasspathDir, "templateClasspathDir cannot be null if "
                    + "useCustomTemplate=true");
            if (!templateClasspathDir.startsWith(SymbolConstant.SLASH)) {
                templateClasspathDir = SymbolConstant.SLASH + templateClasspathDir;
            }
            if (!templateClasspathDir.endsWith(SymbolConstant.SLASH)) {
                templateClasspathDir = templateClasspathDir + SymbolConstant.SLASH;
            }
            javaCurdCode.setTemplateClasspathDir(templateClasspathDir);
        }
        // basic settings
        final FastAutoGenerator fastAutoGenerator = FastAutoGenerator
                .create(
                        new DataSourceConfig
                                .Builder(dbJdbcUrl, dbUsername, dbPassword)
                                .typeConvert((globalConfig, fieldType) -> {
                                    String columnType = fieldType.toLowerCase();
                                    // columnType形如 tinyint(2)  bit(1)
                                    if (columnType.startsWith("tinyint") || columnType.startsWith("bit")) {
                                        return DbColumnType.INTEGER;
                                    }
                                    // 其它字段采用默认转换
                                    return new MySqlTypeConvert().processTypeConvert(globalConfig, fieldType);
                                })
                )
                .globalConfig(builder -> builder
                        // 设置作者
                        .author(javaCurdCode.getJavadocAuthor())
                        .commentDate(javaCurdCode.getJavadocSince())
                        // 生成完成后，不打开对应目录
                        .disableOpenDir()
                        // 指定输出目录
                        .outputDir(javaCurdCode.getOutputDir())
                        // 开启swagger模式
                        .enableSwagger()
                )
                // 包配置
                .packageConfig(builder -> builder
                        // 指定生成的类的所属包
                        .parent(finalPackagePath)
                        .entity("entity.po")
                        .service("service")
                        .serviceImpl("service.impl")
                        .mapper("mapper")
                        .xml("mapper")
                        .controller("controller"))
                // 策略配置
                .strategyConfig(builder -> builder
                        // => 表配置
                        // 设置需要生成代码的表名（不设置则全库生成）
                        .addInclude(
                                tables == null ? new String[]{} : tables.toArray(new String[0])
                        )
                        // 设置过滤表前缀
                        // .addTablePrefix("tmp_")
                        // 设置过滤表后缀
                        // .addTableSuffix("_tmp")
                        // => entity配置
                        .entityBuilder()
                        .convertFileName((entityName) -> entityName + "PO")
                        /// 给po设置公共父类
                        ///.superClass(PoSupperEntity.class)
                        ///设置生成的PO里面的对应字段有填充标识或者逻辑删除标识
                        // 逻辑删除字段名
                        .logicDeletePropertyName("deleted")
                        // 自动填充
                        .addTableFills(new Property("createdBy", FieldFill.INSERT))
                        .addTableFills(new Property("createdAt", FieldFill.INSERT))
                        .addTableFills(new Property("updatedBy", FieldFill.INSERT_UPDATE))
                        .addTableFills(new Property("updatedAt", FieldFill.INSERT_UPDATE))
                        // 启用TableField等相关注解
                        .enableTableFieldAnnotation()
                        .enableLombok()
                        
                        // => controller配置
                        .controllerBuilder()
                        .enableRestStyle()
                        .convertFileName((entityName) -> entityName + "Controller")
                        // .superClass(BaseController.class)
                        
                        // => service配置
                        .serviceBuilder()
                        .convertServiceFileName((entityName) -> entityName + "Service")
                        .convertServiceImplFileName((entityName) -> entityName + "ServiceImpl")
                        
                        // => mapper配置
                        .mapperBuilder()
                        .mapperAnnotation(org.apache.ibatis.annotations.Mapper.class)
                        .convertMapperFileName((entityName) -> entityName + "Mapper")
                        .convertXmlFileName((entityName) -> entityName + "Mapper"))
                .templateEngine(useCustomTemplate ? new EnhanceFreemarkerTemplateEngine(javaCurdCode) :
                        new FreemarkerTemplateEngine());
        
        String templateClasspathDir = javaCurdCode.getTemplateClasspathDir();
        
        // entity、permission、serviceImpl、fallback、mapper、xml，使用指定模板生成
        fastAutoGenerator.templateConfig((TemplateConfig.Builder builder) -> {
            if (useCustomTemplate) {
                /*
                 * 使用自定义的模板
                 * 注：参考mybatis-plus-generator包下的默认模板进行编写即可
                 * 注：自定义的模板放在classpath下的对应位置即可
                 * 注：指定模板时，无需带对应的模板后缀名（如：这里不带.ftl）
                 */
                builder
                        .entity(templateClasspathDir+ "custom_entity")
                        .service(templateClasspathDir + "custom_service")
                        .serviceImpl(templateClasspathDir + "custom_serviceImpl")
                        .controller(templateClasspathDir + "custom_controller")
                        .mapper(templateClasspathDir + "custom_mapper")
                        // 不改的话，用默认的即可
                        .xml(ConstVal.TEMPLATE_XML);
            } else {
                // 使用mybatis-plus-generator包下的默认模板
                builder
                        .entity(ConstVal.TEMPLATE_ENTITY_JAVA)
                        .mapper(ConstVal.TEMPLATE_MAPPER)
                        .xml(ConstVal.TEMPLATE_XML)
                        .service(ConstVal.TEMPLATE_SERVICE)
                        .serviceImpl(ConstVal.TEMPLATE_SERVICE_IMPL)
                        .controller(ConstVal.TEMPLATE_CONTROLLER);
            }
        });
        return fastAutoGenerator;
    }
    
    /**
     * 获取调用当前方法的类的包名
     */
    private static String obtainCallerPackageName() {
        String packageName = null;
        try {
            String thisClassName = GeneratorFace4JavaCurdCode.class.getName();
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            Class<?> callerClazz = null;
            for (StackTraceElement stackTraceElement : stackTrace) {
                String className = stackTraceElement.getClassName();
                boolean notBizClass = StringUtils.startsWithAny(className, "java.", "javax.", "sun.", "org.");
                if (notBizClass) {
                    continue;
                }
                if (!thisClassName.equals(className)) {
                    callerClazz = Class.forName(className);
                    break;
                }
            }
            if (callerClazz != null) {
                packageName = callerClazz.getPackageName();
            }
        } catch (Exception ignore) {
        }
        return packageName;
    }
    
}
