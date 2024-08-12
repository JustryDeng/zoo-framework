package com.ideaaedi.zoo.diy.artifact.generator.face;

import com.ideaaedi.commonds.env.Env;
import com.ideaaedi.commonds.env.RequiredEnv;
import com.ideaaedi.commonds.env.Unit;
import com.ideaaedi.zoo.commonbase.zoo_face.Face;
import com.ideaaedi.zoo.diy.artifact.generator.code.Generator;
import com.ideaaedi.zoo.diy.artifact.generator.code.config.BasicOutputConfig;
import com.ideaaedi.zoo.diy.artifact.generator.code.config.OutputConfig;
import com.ideaaedi.zoo.diy.artifact.generator.code.config.PojoConfig;
import com.ideaaedi.zoo.diy.artifact.generator.code.config.ProcessConfig;
import com.ideaaedi.zoo.diy.artifact.generator.code.entity.PojoInfo;
import com.ideaaedi.zoo.diy.artifact.generator.code.enums.DefaultOutputEnum;
import com.ideaaedi.zoo.diy.artifact.generator.code.process.FreemarkerJavaCodeGenerator;
import com.ideaaedi.zoo.diy.artifact.generator.code.process.JavaCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

/**
 * java 增删改查代码生成器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class GeneratorFace4JavaCode implements Generator, Face {
    
    private static final JavaCodeGenerator generator = new FreemarkerJavaCodeGenerator();
    
    @Override
    public String generateDesc() {
        return "this is a generator for java code (Do not operate the database, If you need to operate database, you could use GeneratorFace4JavaCurdCode)";
    }
    
    /**
     * 生成java代码
     *
     * @see #generate(Consumer, Consumer, PojoInfo...)
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static void generate(@Nonnull PojoInfo... pojoInfos) {
        GeneratorFace4JavaCode.generate(null, (Consumer<ProcessConfig>)null, pojoInfos);
    }
    
    /**
     * 生成java代码
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static void generate(@Nullable Collection<DefaultOutputEnum> outputConfigs,
                                @Nonnull PojoInfo... pojoInfos) {
        GeneratorFace4JavaCode.generate(null, null, outputConfigs, pojoInfos);
    }
    
    /**
     * 生成java代码
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static void generate(@Nullable String outputRootDir,
                                @Nonnull PojoInfo... pojoInfos) {
        GeneratorFace4JavaCode.generate(outputRootDir, null, null, pojoInfos);
    }
    
    /**
     * 生成java代码
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static void generate(@Nullable String outputRootDir,
                                @Nullable Collection<DefaultOutputEnum> outputConfigs,
                                @Nonnull PojoInfo... pojoInfos) {
        GeneratorFace4JavaCode.generate(outputRootDir, null, outputConfigs, pojoInfos);
    }
    
    /**
     * 生成java代码
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static void generate(@Nullable String outputRootDir,
                                @Nullable String packageRoot,
                                @Nonnull PojoInfo... pojoInfos) {
        GeneratorFace4JavaCode.generate(outputRootDir, packageRoot, null, pojoInfos);
    }
    
    /**
     * 生成java代码
     *
     * @see #generate(Consumer, Consumer, PojoInfo...)
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static void generate(@Nullable String outputRootDir,
                                @Nullable String packageRoot,
                                @Nullable Collection<DefaultOutputEnum> outputConfigs,
                                @Nonnull PojoInfo... pojoInfos) {
        boolean outputRootDirIsNotBlank = StringUtils.isNotBlank(outputRootDir);
        boolean packageRootIsNotBlank = StringUtils.isNotBlank(packageRoot);
        Set<OutputConfig> finalOutputConfigs = CollectionUtils.isEmpty(outputConfigs) ? null : PojoConfig.buildOutputConfigs(outputConfigs.toArray(new BasicOutputConfig[0]));
        boolean finalOutputConfigsIsNotNull = finalOutputConfigs != null;
        GeneratorFace4JavaCode.generate(new Consumer<PojoConfig>() {
            @Override
            public void accept(PojoConfig pojoConfig) {
                if (outputRootDirIsNotBlank) {
                    pojoConfig.setOutputRootDir(outputRootDir);
                }
                if (packageRootIsNotBlank) {
                    pojoConfig.setPkgRoot(packageRoot);
                }
                if (finalOutputConfigsIsNotNull) {
                    pojoConfig.setOutputConfigs(finalOutputConfigs);
                }
            }
        }, null, pojoInfos);
    }
    
    /**
     * 生成java代码
     *
     * @param  pojoConfigConsumer 模型配置定制器
     * @param  processConfigConsumer 流程配置定制器
     * @param pojoInfos 模型
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static void generate(@Nullable Consumer<PojoConfig> pojoConfigConsumer,
                                @Nullable Consumer<ProcessConfig> processConfigConsumer,
                                @Nonnull PojoInfo... pojoInfos) {
        for (PojoInfo pojoInfo : pojoInfos) {
            PojoConfig pojoConfig = pojoInfo.getPojoConfig();
            if (pojoConfigConsumer != null) {
                pojoConfigConsumer.accept(pojoConfig);
            }
        }
        if (processConfigConsumer != null) {
            generator.configProcessConfig(processConfigConsumer);
        }
        generator.generate(pojoInfos);
    }
}
