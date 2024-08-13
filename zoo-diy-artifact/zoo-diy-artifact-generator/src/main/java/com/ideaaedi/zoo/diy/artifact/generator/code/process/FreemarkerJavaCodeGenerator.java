package com.ideaaedi.zoo.diy.artifact.generator.code.process;

import com.ideaaedi.commonds.constants.SymbolConstant;
import com.ideaaedi.zoo.diy.artifact.generator.code.config.OutputConfig;
import com.ideaaedi.zoo.diy.artifact.generator.code.config.PojoConfig;
import com.ideaaedi.zoo.diy.artifact.generator.code.entity.PojoInfo;
import com.ideaaedi.zoo.diy.artifact.generator.code.enums.DefaultOutputEnum;
import com.ideaaedi.zoo.diy.artifact.generator.code.template.TemplateProvider;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class FreemarkerJavaCodeGenerator extends JavaCodeGenerator implements TemplateProvider {
    
    /**
     * 从指定freemarker模板所在classpath根位置
     */
    private static final String DEFAULT_POSITION = "/";
    
    /**
     * 模板
     * <pre>
     *  key-输出类型{@link OutputConfig#outputType()}
     *  value-模板信息
     * </pre>
     */
    private static final Map<String, String> outputTemplateMap = new ConcurrentHashMap<>(
            Map.of(
                    DefaultOutputEnum.controller.outputType(), "templates/default-java/controller.ftl",
                    DefaultOutputEnum.service.outputType(), "templates/default-java/service.ftl",
                    DefaultOutputEnum.service_impl.outputType(), "templates/default-java/service_impl.ftl",
                    DefaultOutputEnum.mapper.outputType(), "templates/default-java/mapper.ftl",
                    DefaultOutputEnum.mapper_xml.outputType(), "templates/default-java/mapper_xml.ftl",
                    DefaultOutputEnum.entity.outputType(), "templates/default-java/entity.ftl",
                    DefaultOutputEnum.entity_req_vo.outputType(), "templates/default-java/entity_req_vo.ftl",
                    DefaultOutputEnum.entity_resp_vo.outputType(), "templates/default-java/entity_resp_vo.ftl",
                    DefaultOutputEnum.entity_list_req_vo.outputType(), "templates/default-java/entity_list_req_vo.ftl",
                    DefaultOutputEnum.entity_list_resp_vo.outputType(), "templates/default-java/entity_list_resp_vo.ftl"
            )
    );
    
    private final Configuration configuration;
    
    public FreemarkerJavaCodeGenerator() {
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding(ENCODING);
        configuration.setClassForTemplateLoading(FreemarkerJavaCodeGenerator.class, DEFAULT_POSITION);
    }
    
    @Override
    @SneakyThrows
    protected List<String> doGenerate(@Nonnull PojoInfo... pojoInfos) {
        List<String> list = new ArrayList<>(64);
        for (PojoInfo pojoInfo : pojoInfos) {
            PojoConfig pojoConfig = pojoInfo.getPojoConfig();
            Objects.requireNonNull(pojoConfig, "pojoConfig cannot be null.");
            Set<OutputConfig> outputConfigs = pojoConfig.getOutputConfigs();
            Objects.requireNonNull(outputConfigs, "outputConfigs cannot be null.");
            String outputRootDir = pojoConfig.getOutputRootDir();
            String pkgRoot = pojoConfig.getPkgRoot();
            String pkgRootDir = pkgRoot.replaceAll("\\.", SymbolConstant.SLASH);
            if (!outputRootDir.endsWith(SymbolConstant.SLASH)) {
                outputRootDir += SymbolConstant.SLASH;
            }
            if (pkgRootDir.startsWith(SymbolConstant.SLASH)) {
                pkgRootDir = pkgRootDir.substring(1);
            }
            String outputWithPkgRootDir = outputRootDir + pkgRootDir;
            File rootDir = new File(outputWithPkgRootDir);
            if (!rootDir.exists()) {
                //noinspection ResultOfMethodCallIgnored
                rootDir.mkdirs();
            }
            String entityName = pojoInfo.getName();
            Objects.requireNonNull(entityName, "entityName cannot be null.");
            if (!CollectionUtils.isEmpty(outputConfigs)) {
                for (OutputConfig outputConfig : outputConfigs) {
                    String templateFilePath = obtainTemplatePath(pojoConfig, outputConfig);
                    String outputName = outputConfig.outputName(entityName);
                    String suffix = outputConfig.outputSuffix();
                    String relativePkg = outputConfig.relativePkg();
                    String relativePath = relativePkg.replaceAll("\\.", SymbolConstant.SLASH);
                    if (!relativePkg.endsWith(SymbolConstant.SLASH)) {
                        relativePath += SymbolConstant.SLASH;
                    }
                    File javaFile = new File(rootDir, relativePath + outputName + suffix);
                    if (javaFile.exists()) {
                        log.warn("Ignore generate. because file '{}' already exist.", javaFile.getAbsolutePath());
                        continue;
                    }
                    File parentFile = javaFile.getParentFile();
                    if (!parentFile.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        parentFile.mkdirs();
                    }
                    //noinspection ResultOfMethodCallIgnored
                    javaFile.createNewFile();
                    Template template = configuration.getTemplate(templateFilePath, ENCODING);
                    Map<String, Object> context = outputConfig.context(pojoInfo);
                    contextPostProcess(context);
                    try (FileOutputStream fileOutputStream = new FileOutputStream(javaFile)) {
                        template.process(context, new OutputStreamWriter(fileOutputStream, ENCODING));
                    }
                    list.add(javaFile.getAbsolutePath());
                }
            }
        }
        return list;
    }
    
    /**
     * 获取模板文件路径
     */
    @Nonnull
    private String obtainTemplatePath(PojoConfig pojoConfig, OutputConfig outputConfig) {
        String outputType = outputConfig.outputType();
        String templateFilePath = pojoConfig.provide(outputType);
        if (StringUtils.isBlank(templateFilePath)) {
            templateFilePath = this.provide(outputType);
        }
        Assert.isTrue(StringUtils.isNotBlank(templateFilePath), "obtain template fail. curr outputType '" + outputType + "'");
        return templateFilePath;
    }
    
    /**
     * 模板变量后处理
     */
    protected void contextPostProcess(@Nonnull Map<String, Object> context) {
    }
    
    @Override
    public String provide(@Nonnull String outputType) {
        return Objects.requireNonNull(
                outputTemplateMap.get(outputType),
                "obtain template info fail. curr outputType '" + outputType + "'"
        );
    }
}
