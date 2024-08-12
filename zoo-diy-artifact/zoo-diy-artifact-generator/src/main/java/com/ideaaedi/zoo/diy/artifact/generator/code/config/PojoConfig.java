package com.ideaaedi.zoo.diy.artifact.generator.code.config;

import com.ideaaedi.commonds.path.PathUtil;
import com.ideaaedi.commonds.time.DateTimeConverter;
import com.ideaaedi.zoo.commonbase.constant.AutoConfigurationConstant;
import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import com.ideaaedi.zoo.commonbase.entity.BasePageDTO;
import com.ideaaedi.zoo.commonbase.entity.PageInfo;
import com.ideaaedi.zoo.commonbase.entity.Result;
import com.ideaaedi.zoo.diy.artifact.generator.code.entity.PojoInfo;
import com.ideaaedi.zoo.diy.artifact.generator.code.template.TemplateProvider;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ideaaedi.zoo.diy.artifact.generator.face.constant.PlaceHolderConstant.IMPORT_CONTROLLER_CLASSES;
import static com.ideaaedi.zoo.diy.artifact.generator.face.constant.PlaceHolderConstant.IMPORT_SERVICE_IMPL_CLASSES;
import static com.ideaaedi.zoo.diy.artifact.generator.face.constant.PlaceHolderConstant.UNIFIED_CLASS_LONG_NAME_4_BaseDTO;
import static com.ideaaedi.zoo.diy.artifact.generator.face.constant.PlaceHolderConstant.UNIFIED_CLASS_LONG_NAME_4_BasePageDTO;
import static com.ideaaedi.zoo.diy.artifact.generator.face.constant.PlaceHolderConstant.UNIFIED_CLASS_LONG_NAME_4_PageInfo;
import static com.ideaaedi.zoo.diy.artifact.generator.face.constant.PlaceHolderConstant.UNIFIED_CLASS_LONG_NAME_4_Result;
import static com.ideaaedi.zoo.diy.artifact.generator.face.constant.PlaceHolderConstant.UNIFIED_CLASS_NAME_4_BaseDTO;
import static com.ideaaedi.zoo.diy.artifact.generator.face.constant.PlaceHolderConstant.UNIFIED_CLASS_NAME_4_BasePageDTO;
import static com.ideaaedi.zoo.diy.artifact.generator.face.constant.PlaceHolderConstant.UNIFIED_CLASS_NAME_4_PageInfo;
import static com.ideaaedi.zoo.diy.artifact.generator.face.constant.PlaceHolderConstant.UNIFIED_CLASS_NAME_4_Result;

/**
 * {@link PojoInfo}配置
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class PojoConfig implements TemplateProvider {
    
    private static final String DEFAULT_SINCE;
    
    private static final String DEFAULT_OUTPUT_ROOT_DIR;
    
    public static boolean DEFAULT_HAS_ZOO_KNIFE4J_EXT;
    
    static {
        try {
            Class.forName(AutoConfigurationConstant.ZOO_KNIFE4J_AUTO_CONFIGURATION_CLASS_LONG_NAME);
            DEFAULT_HAS_ZOO_KNIFE4J_EXT = true;
        } catch (ClassNotFoundException e) {
            DEFAULT_HAS_ZOO_KNIFE4J_EXT = false;
        }
        DEFAULT_SINCE =  System.getProperty("user.name", "code generator");;
        DEFAULT_OUTPUT_ROOT_DIR = PathUtil.getProjectRootDir(PojoConfig.class);
    }
    
    /**
     * 作者
     */
    private String author = DEFAULT_SINCE;
    
    /**
     * since
     */
    private String since = DateTimeConverter.REGULAR_DATE_TIME.now();
    
    /**
     * 是否启用swagger注解
     */
    private boolean enableSwagger = true;
    
    /**
     * 是否启用lombok注解
     */
    private boolean enableLombok = true;
    
    /**
     * 是否可jdk序列化
     */
    private boolean enableSerializable = true;
    
    /**
     * 当前应用中，是否用到了zoo-diy-artifact-generator组件对swagger进行增强
     * <pre>
     * 若为null,则取{@link PojoConfig#DEFAULT_HAS_ZOO_KNIFE4J_EXT}值
     * </pre>
     */
    private Boolean hasZooKnife4jExtL;
    
    /**
     * 输出根目录
     * <pre>
     * 注：
     * 文件的最终位置 = {@code outputRootDir}
     *                 + {@link PojoConfig#pkgRoot}
     *                 + {@link OutputConfig#relativePkg()}
     *                 + {@link OutputConfig#outputName(String)}
     *                 + {@link OutputConfig#outputSuffix()}
     * </pre>
     */
    private String outputRootDir = DEFAULT_OUTPUT_ROOT_DIR;
    
    /**
     * 包名根
     * <pre>
     * 注：
     * 文件的最终位置 = {@link PojoConfig#outputRootDir}
     *                 + {@code pkgRoot}
     *                 + {@link OutputConfig#relativePkg()}
     *                 + {@link OutputConfig#outputName(String)}
     *                 + {@link OutputConfig#outputSuffix()}
     * </pre>
     */
    private String pkgRoot = "com";
    
    /**
     * 默认输出项(含对应配置)
     * <pre>
     * 可自行创建，也可使用这里提供的方法{@link PojoConfig#buildOutputConfigs(BasicOutputConfig...)}
     * </pre>
     */
    private Set<OutputConfig> outputConfigs;
    
    /**
     * 个性化配置
     * <p>
     *  注：如果对各个{@link OutputConfig#outputType()}需要个性化配置，可使用此字段，在
     *      {@link OutputConfig#context(PojoInfo)} 构建模板上下文参数时，将个性化字段添加进去即可
     * </p>
     * <pre>
     *  key-输出类型{@link OutputConfig#outputType()}
     *  value-个性化配置
     * </pre>
     */
    private Map<String, Map<String, Object>> personalizedConfigMap = new HashMap<>(
            Map.of(
                    // controller 默认个性化配置
                    AbstractOutputConfig.OUTPUT_TYPE_CONTROLLER, new HashMap<>(){{
                        // controller 统一响应类
                        put(UNIFIED_CLASS_NAME_4_Result, Result.class.getSimpleName());
                        put(UNIFIED_CLASS_LONG_NAME_4_Result, Result.class.getName());
                        // controller 统一分页包装类
                        put(UNIFIED_CLASS_NAME_4_PageInfo, PageInfo.class.getSimpleName());
                        put(UNIFIED_CLASS_LONG_NAME_4_PageInfo, PageInfo.class.getName());
                        put(IMPORT_CONTROLLER_CLASSES, Set.of(
                                Validated.class.getName(),
                                PostMapping.class.getName(),
                                RequestBody.class.getName(),
                                RequestMapping.class.getName(),
                                RestController.class.getName()
                        ));
                    }},
                    
                    // entity 默认个性化配置
                    AbstractOutputConfig.OUTPUT_TYPE_ENTITY, new HashMap<>() {{
                        // controller 请求体基类
                        put(UNIFIED_CLASS_NAME_4_BaseDTO, BaseDTO.class.getSimpleName());
                        put(UNIFIED_CLASS_LONG_NAME_4_BaseDTO, BaseDTO.class.getName());
                    }},
                    
                    // entity_req_vo 默认个性化配置
                    AbstractOutputConfig.OUTPUT_TYPE_ENTITY_REQ_VO, new HashMap<>() {{
                        // controller 请求体基类
                        put(UNIFIED_CLASS_NAME_4_BaseDTO, BaseDTO.class.getSimpleName());
                        put(UNIFIED_CLASS_LONG_NAME_4_BaseDTO, BaseDTO.class.getName());
                    }},
                    
                    // controller 分页请求请求体基类
                    AbstractOutputConfig.OUTPUT_TYPE_ENTITY_LIST_REQ_VO, new HashMap<>() {{
                        put(UNIFIED_CLASS_NAME_4_BasePageDTO, BasePageDTO.class.getSimpleName());
                        put(UNIFIED_CLASS_LONG_NAME_4_BasePageDTO, BasePageDTO.class.getName());
                    }},
                    
                    // service 分页请求请求体基类
                    AbstractOutputConfig.OUTPUT_TYPE_SERVICE, new HashMap<>() {{
                        put(UNIFIED_CLASS_NAME_4_PageInfo, PageInfo.class.getSimpleName());
                        put(UNIFIED_CLASS_LONG_NAME_4_PageInfo, PageInfo.class.getName());
                    }},
                    
                    // service 分页请求请求体基类
                    AbstractOutputConfig.OUTPUT_TYPE_SERVICE_IMPL, new HashMap<>() {{
                        put(UNIFIED_CLASS_NAME_4_PageInfo, PageInfo.class.getSimpleName());
                        put(UNIFIED_CLASS_LONG_NAME_4_PageInfo, PageInfo.class.getName());
                        put(IMPORT_SERVICE_IMPL_CLASSES, Set.of(Service.class.getName()));
                    }}
            )
    );
    
    /**
     * 代码模板
     * <pre>
     *  key-输出类型{@link OutputConfig#outputType()}
     *  value-模板信息
     * </pre>
     */
    private Map<String, String> outputTemplateMap = new HashMap<>(16);
    
    @Override
    public String provide(@Nonnull String outputType) {
        return outputTemplateMap.get(outputType);
    }
    
    /**
     * 默认输出项(含对应配置)
     */
    public static Set<OutputConfig> buildOutputConfigs(BasicOutputConfig... basicOutputConfigs) {
        Map<String, BasicOutputConfig> relatedConfigMap = Arrays.stream(basicOutputConfigs)
                .collect(
                        Collectors.toMap(BasicOutputConfig::outputType, Function.identity())
                );
        return new LinkedHashSet<>(Arrays.stream(basicOutputConfigs)
                        .map(x -> new DefaultOutputConfig(x, relatedConfigMap))
                        .toList()
        );
    }
    
}
