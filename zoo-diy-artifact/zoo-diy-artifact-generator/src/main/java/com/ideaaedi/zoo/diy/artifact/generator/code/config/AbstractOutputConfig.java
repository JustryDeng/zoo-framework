package com.ideaaedi.zoo.diy.artifact.generator.code.config;

import cn.hutool.core.util.StrUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.annotation.ApiTag;
import com.ideaaedi.zoo.diy.artifact.generator.code.entity.FieldInfo;
import com.ideaaedi.zoo.diy.artifact.generator.code.entity.PojoInfo;
import com.ideaaedi.zoo.diy.artifact.generator.code.entity.TemplatePlaceholder;
import com.ideaaedi.zoo.diy.artifact.generator.face.constant.PlaceHolderConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


/**
 * 具体的文件输出配置
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public abstract class AbstractOutputConfig implements OutputConfig {
    
    /** 内置输出类型 */
    public static final String OUTPUT_TYPE_ENTITY = "entity";
    public static final String OUTPUT_TYPE_ENTITY_REQ_VO = "entity_req_vo";
    public static final String OUTPUT_TYPE_ENTITY_RESP_VO = "entity_resp_vo";
    public static final String OUTPUT_TYPE_ENTITY_LIST_REQ_VO = "entity_list_req_vo";
    public static final String OUTPUT_TYPE_ENTITY_LIST_RESP_VO = "entity_list_resp_vo";
    public static final String OUTPUT_TYPE_CONTROLLER = "controller";
    public static final String OUTPUT_TYPE_SERVICE = "service";
    public static final String OUTPUT_TYPE_SERVICE_IMPL = "service_impl";
    public static final String OUTPUT_TYPE_MAPPER = "mapper";
    public static final String OUTPUT_TYPE_MAPPER_XML = "mapper_xml";
    
    /**
     * 相关类型配置
     * <pre>
     * key- 输出类型
     * key- 输出类型相关配置
     * </pre>
     */
    protected final Map<String, BasicOutputConfig> relatedConfig;
    
    public AbstractOutputConfig(@Nonnull Map<String, BasicOutputConfig> relatedConfig) {
        this.relatedConfig = relatedConfig;
    }
    
    @Nonnull
    @Override
    public Map<String, BasicOutputConfig> relatedConfig() {
        return this.relatedConfig;
    }
    
    @Nonnull
    @Override
    public Map<String, Object> context(@Nonnull PojoInfo pojoInfo) {
        PojoConfig pojoConfig = pojoInfo.getPojoConfig();
        String outputType = outputType();
        Map<String, Object> context = new HashMap<>(32);
        TemplatePlaceholder params;
        switch (outputType) {
            case OUTPUT_TYPE_MAPPER -> {
                params = buildMapperTemplate(pojoInfo);
            }
            case OUTPUT_TYPE_MAPPER_XML -> {
                params = buildMapperXml(pojoInfo);
            }
            case OUTPUT_TYPE_CONTROLLER -> {
                params = buildControllerTemplatePlaceholder(pojoInfo);
            }
            case OUTPUT_TYPE_SERVICE -> {
                params = buildServiceTemplatePlaceholder(pojoInfo);
            }
            case OUTPUT_TYPE_SERVICE_IMPL -> {
                params = buildServiceImplTemplatePlaceholder(pojoInfo);
            }
            case OUTPUT_TYPE_ENTITY -> {
                params = buildEntityTemplatePlaceholder(pojoInfo);
            }
            case OUTPUT_TYPE_ENTITY_REQ_VO, OUTPUT_TYPE_ENTITY_RESP_VO -> {
                params = buildReqRespVoTemplatePlaceholder(pojoInfo);
            }
            case OUTPUT_TYPE_ENTITY_LIST_REQ_VO, OUTPUT_TYPE_ENTITY_LIST_RESP_VO -> {
                params = buildListReqRespVoTemplatePlaceholder(pojoInfo);
            }
            default -> throw new IllegalArgumentException("unsupported DefaultOutputEnum: " + this);
        }
        // 将个性化配置也添加到上下文中
        Map<String, Object> personalizedProperties = pojoConfig.getPersonalizedConfigMap().getOrDefault(outputType(), new HashMap<>(4));
        params.setProperties(personalizedProperties);
        context.put(this.outputType(), params);
        return context;
    }
    
    /**
     * 处理导入的包
     *
     * @param importClasses  要导入的类全类名
     * @param currPkg  当前类所在的包名 （如：com.ideaaedi.demo.entity）
     *
     * @return 处理后的要导入的包
     */
    @Nonnull
    protected LinkedHashSet<String> handleImportClasses(@Nullable List<String> importClasses, @Nonnull String currPkg) {
        if (CollectionUtils.isEmpty(importClasses)) {
            return new LinkedHashSet<>(1);
        }
        // 过滤掉默认包 并排序
        importClasses = importClasses.stream().filter(classLongName -> {
            // java默认导入的包
            if (StringUtils.startsWithAny(classLongName, "java.lang.", "java.time.")) {
                return false;
            }
            if (StringUtils.equalsAny(classLongName, "java.math.BigDecimal", "java.math.BigInteger")) {
                return false;
            }
            int lastIdx = classLongName.lastIndexOf(".");
            // 同一个包下的类不需要导包
            return !currPkg.equals(classLongName.substring(0, lastIdx));
        }).sorted(String::compareTo).toList();
        
        // java. 开头的包与也分包空一行分开
        List<String> bizImports = new ArrayList<>(32);
        List<String> javaImports = new ArrayList<>(16);
        for (String importClass : importClasses) {
            if (importClass.startsWith("java.")) {
                javaImports.add(importClass);
                continue;
            }
            bizImports.add(importClass);
        }
        LinkedHashSet<String> allImport = new LinkedHashSet<>(bizImports);
        if (!CollectionUtils.isEmpty(javaImports)) {
            // 空一行
            allImport.add(BaseConstant.EMPTY);
            allImport.addAll(javaImports);
        }
        return allImport;
    }
    
    /**
     * 构建全类名
     *
     * @param pkgRoot 根包名
     * @param outputConfig 输出配置
     * @param pojoName 模型名
     */
    @Nonnull
    protected String buildClassLongName(String pkgRoot, BasicOutputConfig outputConfig, String pojoName) {
        return StringUtils.strip(pkgRoot, ".") + "." + StringUtils.strip(outputConfig.relativePkg(), ".") + "." + outputConfig.outputName(pojoName);
    }
    
    /**
     * 构建（配置对应的）输出类的包名
     * <pre>
     *  即：{@link OutputConfig#outputName(String)} 文件的包名
     * </pre>
     *
     * @param pkgRoot 根包名
     * @param outputConfig 输出配置
     */
    @Nonnull
    protected String buildPkg(@Nonnull String pkgRoot, @Nonnull BasicOutputConfig outputConfig) {
        return StringUtils.strip(pkgRoot, ".") + "." + StringUtils.strip(outputConfig.relativePkg(), ".");
    }
    
    /**
     * 构建{@link AbstractOutputConfig#OUTPUT_TYPE_MAPPER}对应的占位符模板参数
     */
    protected TemplatePlaceholder buildMapperTemplate(@Nonnull PojoInfo pojoInfo) {
        String comment = pojoInfo.getComment();
        PojoConfig pojoConfig = pojoInfo.getPojoConfig();
        String pkgRoot = pojoConfig.getPkgRoot();
        String author = pojoConfig.getAuthor();
        String since = pojoConfig.getSince();
        String entityName = pojoInfo.getName();
        String currPkg = StringUtils.strip(pkgRoot, ".") + "." + StringUtils.strip(relativePkg(), ".");
        return TemplatePlaceholder.builder()
                .name(outputName(entityName))
                .comment(comment)
                .pkg(currPkg)
                .author(author)
                .since(since)
                .build();
    }
    
    /**
     * 构建{@link AbstractOutputConfig#OUTPUT_TYPE_MAPPER_XML}对应的占位符模板参数
     */
    protected TemplatePlaceholder buildMapperXml(@Nonnull PojoInfo pojoInfo) {
        String comment = pojoInfo.getComment();
        PojoConfig pojoConfig = pojoInfo.getPojoConfig();
        String pkgRoot = pojoConfig.getPkgRoot();
        String author = pojoConfig.getAuthor();
        String since = pojoConfig.getSince();
        String pojoName = pojoInfo.getName();
        String currPkg = buildPkg(pkgRoot, this);
        TemplatePlaceholder params;
        BasicOutputConfig mapperOutputConfig = relatedConfig().get(OUTPUT_TYPE_MAPPER);
        String mapperName = determineOutputName(OUTPUT_TYPE_MAPPER_XML, pojoName, new ArrayList<>(1), pkgRoot);
        String mapperPkg = mapperOutputConfig == null ? pkgRoot : buildPkg(pkgRoot, mapperOutputConfig);
        params = TemplatePlaceholder.MapperXml.builder()
                .name(outputName(pojoName))
                .comment(comment)
                .pkg(currPkg)
                .author(author)
                .since(since)
                .mapperName(mapperName)
                .mapperPkg(mapperPkg)
                .build();
        return params;
    }
    
    /**
     * 构建{@link AbstractOutputConfig#OUTPUT_TYPE_CONTROLLER}对应的占位符模板参数
     */
    protected TemplatePlaceholder buildControllerTemplatePlaceholder(@Nonnull PojoInfo pojoInfo) {
        String comment = pojoInfo.getComment();
        PojoConfig pojoConfig = pojoInfo.getPojoConfig();
        String pkgRoot = pojoConfig.getPkgRoot();
        String author = pojoConfig.getAuthor();
        String since = pojoConfig.getSince();
        String pojoName = pojoInfo.getName();
        boolean enableSwagger = pojoConfig.isEnableSwagger();
        String currPkg = buildPkg(pkgRoot, this);
        List<String> importClasses = new ArrayList<>(32);
        Boolean hasZooKnife4jExt = pojoConfig.getHasZooKnife4jExtL();
        if (hasZooKnife4jExt == null) {
            hasZooKnife4jExt = PojoConfig.DEFAULT_HAS_ZOO_KNIFE4J_EXT;
        }
        BasicOutputConfig serviceOutputConfig = relatedConfig().get(OUTPUT_TYPE_SERVICE);
        String serviceName = null;
        if (serviceOutputConfig != null) {
            serviceName = serviceOutputConfig.outputName(pojoName);
            if (StringUtils.isNotBlank(serviceName)) {
                importClasses.add(buildClassLongName(pkgRoot, serviceOutputConfig, pojoName));
                importClasses.add(Resource.class.getName());
            }
        }
        String entityReqVoName = determineOutputName(OUTPUT_TYPE_ENTITY_REQ_VO, pojoName, importClasses, pkgRoot);
        String entityRespVoName = determineOutputName(OUTPUT_TYPE_ENTITY_RESP_VO, pojoName, importClasses, pkgRoot);
        String entityListReqVoName = determineOutputName(OUTPUT_TYPE_ENTITY_LIST_REQ_VO, pojoName, importClasses, pkgRoot);
        String entityListRespVoName = determineOutputName(OUTPUT_TYPE_ENTITY_LIST_RESP_VO, pojoName, importClasses, pkgRoot);
    
        // 个性化配置
        Map<String, Object> personalizedProperties = pojoConfig.getPersonalizedConfigMap().getOrDefault(outputType(), new HashMap<>(4));
        Object resultClassName = personalizedProperties.get(PlaceHolderConstant.UNIFIED_CLASS_NAME_4_Result);
        Object resultClassLongName = personalizedProperties.get(PlaceHolderConstant.UNIFIED_CLASS_LONG_NAME_4_Result);
        boolean couldGenerateMethod = entityListReqVoName != null || entityReqVoName != null;
        if (resultClassName != null && resultClassLongName != null && couldGenerateMethod) {
            importClasses.add(resultClassLongName.toString());
        } else {
            personalizedProperties.remove(PlaceHolderConstant.UNIFIED_CLASS_NAME_4_Result);
            personalizedProperties.remove(PlaceHolderConstant.UNIFIED_CLASS_LONG_NAME_4_Result);
        }
        importClasses4PageInfo(importClasses, entityListReqVoName != null, personalizedProperties);
        //noinspection unchecked
        Set<String> importControllerClassesSet = (Set<String>)personalizedProperties.get(PlaceHolderConstant.IMPORT_CONTROLLER_CLASSES);
        if (importControllerClassesSet != null) {
            importClasses.addAll(importControllerClassesSet);
        }
        
        //  lombok 涉及的包
        boolean enableLombok = pojoConfig.isEnableLombok();
        if (enableLombok) {
            importClasses.add(Slf4j.class.getName());
        }
        //  swagger 涉及的包
        if (enableSwagger) {
            importClasses.add(Operation.class.getName());
            if (hasZooKnife4jExt) {
                importClasses.add(ApiTag.class.getName());
                importClasses.add(ApiOperationSupport.class.getName());
            }  else {
                importClasses.add(Tag.class.getName());
            }
        }
        
        return TemplatePlaceholder.Controller.builder()
                .name(outputName(pojoName))
                .comment(comment)
                .pkg(currPkg)
                .author(author)
                .since(since)
                .enableSwagger(enableSwagger)
                .hasZooKnife4jExt(hasZooKnife4jExt)
                .entityReqVoName(entityReqVoName)
                .entityRespVoName(entityRespVoName)
                .entityListReqVoName(entityListReqVoName)
                .entityListRespVoName(entityListRespVoName)
                .serviceName(serviceName)
                .unCapitalServiceName(StringUtils.uncapitalize(serviceName))
                .importClasses(handleImportClasses(importClasses, currPkg))
                .pojoNameHyphen(StrUtil.toSymbolCase(pojoName, '-'))
                .build();
    }
    
    /**
     * 构建{@link AbstractOutputConfig#OUTPUT_TYPE_SERVICE}对应的占位符模板参数
     */
    protected TemplatePlaceholder buildServiceTemplatePlaceholder(@Nonnull PojoInfo pojoInfo) {
        String comment = pojoInfo.getComment();
        PojoConfig pojoConfig = pojoInfo.getPojoConfig();
        String pkgRoot = pojoConfig.getPkgRoot();
        String author = pojoConfig.getAuthor();
        String since = pojoConfig.getSince();
        String pojoName = pojoInfo.getName();
        String currPkg = buildPkg(pkgRoot, this);
        List<String> importClasses = new ArrayList<>(32);
        String entityReqVoName = determineOutputName(OUTPUT_TYPE_ENTITY_REQ_VO, pojoName, importClasses, pkgRoot);
        String entityRespVoName = determineOutputName(OUTPUT_TYPE_ENTITY_RESP_VO, pojoName, importClasses, pkgRoot);
        String entityListReqVoName = determineOutputName(OUTPUT_TYPE_ENTITY_LIST_REQ_VO, pojoName, importClasses,
                pkgRoot);
        String entityListRespVoName = determineOutputName(OUTPUT_TYPE_ENTITY_LIST_RESP_VO, pojoName, importClasses,
                pkgRoot);
        // 个性化信息
        Map<String, Object> personalizedProperties = pojoConfig.getPersonalizedConfigMap().getOrDefault(outputType(), new HashMap<>(4));
        importClasses4PageInfo(importClasses, entityListReqVoName != null, personalizedProperties);
        return TemplatePlaceholder.Service.builder()
                .name(outputName(pojoName))
                .comment(comment)
                .pkg(currPkg)
                .author(author)
                .since(since)
                .entityReqVoName(entityReqVoName)
                .entityRespVoName(entityRespVoName)
                .entityListReqVoName(entityListReqVoName)
                .entityListRespVoName(entityListRespVoName)
                .importClasses(handleImportClasses(importClasses, currPkg))
                .build();
    }
    
    /**
     * 构建{@link AbstractOutputConfig#OUTPUT_TYPE_SERVICE_IMPL}对应的占位符模板参数
     */
    protected TemplatePlaceholder buildServiceImplTemplatePlaceholder(@Nonnull PojoInfo pojoInfo) {
        String comment = pojoInfo.getComment();
        PojoConfig pojoConfig = pojoInfo.getPojoConfig();
        String pkgRoot = pojoConfig.getPkgRoot();
        String author = pojoConfig.getAuthor();
        String since = pojoConfig.getSince();
        String pojoName = pojoInfo.getName();
        String currPkg = buildPkg(pkgRoot, this);
        List<String> importClasses = new ArrayList<>(32);
        String entityReqVoName = determineOutputName(OUTPUT_TYPE_ENTITY_REQ_VO, pojoName, importClasses, pkgRoot);
        String entityRespVoName = determineOutputName(OUTPUT_TYPE_ENTITY_RESP_VO, pojoName, importClasses, pkgRoot);
        String entityListReqVoName = determineOutputName(OUTPUT_TYPE_ENTITY_LIST_REQ_VO, pojoName, importClasses,
                pkgRoot);
        String entityListRespVoName = determineOutputName(OUTPUT_TYPE_ENTITY_LIST_RESP_VO, pojoName, importClasses,
                pkgRoot);
        String serviceName = determineOutputName(OUTPUT_TYPE_SERVICE, pojoName, importClasses, pkgRoot);
        // 个性化信息
        Map<String, Object> personalizedProperties = pojoConfig.getPersonalizedConfigMap().getOrDefault(outputType(), new HashMap<>(4));
        importClasses4PageInfo(importClasses, entityListReqVoName != null, personalizedProperties);
        //noinspection unchecked
        Set<String> importServiceImplClassesSet = (Set<String>)personalizedProperties.get(PlaceHolderConstant.IMPORT_SERVICE_IMPL_CLASSES);
        if (importServiceImplClassesSet != null) {
            importClasses.addAll(importServiceImplClassesSet);
        }
        String mapperName = determineOutputName(OUTPUT_TYPE_MAPPER, pojoName, importClasses, pkgRoot);
        if (mapperName != null) {
            importClasses.add(Resource.class.getName());
        }
        return TemplatePlaceholder.ServiceImpl.builder()
                .name(outputName(pojoName))
                .comment(comment)
                .pkg(currPkg)
                .author(author)
                .since(since)
                .entityReqVoName(entityReqVoName)
                .entityRespVoName(entityRespVoName)
                .entityListReqVoName(entityListReqVoName)
                .entityListRespVoName(entityListRespVoName)
                .importClasses(handleImportClasses(importClasses, currPkg))
                .serviceName(serviceName)
                .mapperName(mapperName)
                .unCapitalMapperName(StringUtils.uncapitalize(mapperName))
                .build();
    }
    
    /**
     * 获得指定的文件名（即：不带后缀的简易类名），如果不为空的话，同时将其往要导入的包中添加
     *
     * @param outputType 文件类型
     * @param importClasses 要导入的包容器
     * @param pojoName 模型名称
     * @param pkgRoot 文件根包
     *
     * @return 简易类名
     */
    @Nullable
    private String determineOutputName(String outputType, String pojoName,
                                       List<String> importClasses,
                                       String pkgRoot) {
        String entityReqVoName = null;
        BasicOutputConfig entityReqVoOutputConfig = relatedConfig().get(outputType);
        if (entityReqVoOutputConfig != null) {
            entityReqVoName = entityReqVoOutputConfig.outputName(pojoName);
            importClasses.add(buildClassLongName(pkgRoot, entityReqVoOutputConfig, pojoName));
        }
        return entityReqVoName;
    }
    
    /**
     * 从个性化配置中查询分页类，存在的话将其加入要导入的包中
     *
     * @param importClasses 要导入的包容器
     * @param couldGeneratePageMethod 类中是否会有方法生成
     * @param personalizedProperties 个性化配置
     */
    protected void importClasses4PageInfo(List<String> importClasses, boolean couldGeneratePageMethod, Map<String, Object> personalizedProperties) {
        Object pageInfoClassName = personalizedProperties.get(PlaceHolderConstant.UNIFIED_CLASS_NAME_4_PageInfo);
        Object pageInfoClassLongName = personalizedProperties.get(PlaceHolderConstant.UNIFIED_CLASS_LONG_NAME_4_PageInfo);
        if (pageInfoClassName != null && pageInfoClassLongName != null && couldGeneratePageMethod) {
            importClasses.add(pageInfoClassLongName.toString());
        } else {
            personalizedProperties.remove(PlaceHolderConstant.UNIFIED_CLASS_NAME_4_PageInfo);
            personalizedProperties.remove(PlaceHolderConstant.UNIFIED_CLASS_LONG_NAME_4_PageInfo);
        }
    }
    
    /**
     * 构建{@link AbstractOutputConfig#OUTPUT_TYPE_ENTITY_REQ_VO}、{@link AbstractOutputConfig#OUTPUT_TYPE_ENTITY_RESP_VO}对应的占位符模板参数
     */
    protected TemplatePlaceholder buildReqRespVoTemplatePlaceholder(@Nonnull PojoInfo pojoInfo) {
        return buildReqRespVoTemplatePlaceholder(
                pojoInfo,
                Objects.equals(outputType(), OUTPUT_TYPE_ENTITY_REQ_VO),
                PlaceHolderConstant.UNIFIED_CLASS_NAME_4_BaseDTO,
                PlaceHolderConstant.UNIFIED_CLASS_LONG_NAME_4_BaseDTO
        );
    }
    
    /**
     * 构建{@link AbstractOutputConfig#OUTPUT_TYPE_ENTITY_LIST_REQ_VO}、{@link AbstractOutputConfig#OUTPUT_TYPE_ENTITY_LIST_RESP_VO}对应的占位符模板参数
     */
    protected TemplatePlaceholder buildListReqRespVoTemplatePlaceholder(@Nonnull PojoInfo pojoInfo) {
        return buildReqRespVoTemplatePlaceholder(
                pojoInfo,
                Objects.equals(outputType(), OUTPUT_TYPE_ENTITY_LIST_REQ_VO),
                PlaceHolderConstant.UNIFIED_CLASS_NAME_4_BasePageDTO,
                PlaceHolderConstant.UNIFIED_CLASS_LONG_NAME_4_BasePageDTO
        );
    }
    
    /**
     * 构建请求、响应 vo模型
     *
     * @param pojoInfo 模型信息
     * @param isReq 是否是请求模型（true-请求模型；false-响应模型）
     * @param superClassClassName 个性化字段中指定的父类类名
     * @param superClassClassLongName 个性化字段中指定的父类全类名
     *
     * @return  模板变量信息
     */
    protected TemplatePlaceholder.EntityReqRespVO buildReqRespVoTemplatePlaceholder(@Nonnull PojoInfo pojoInfo,
                                                                                    boolean isReq,
                                                                                    @Nullable String superClassClassName,
                                                                                    @Nullable String superClassClassLongName) {
        PojoConfig pojoConfig = pojoInfo.getPojoConfig();
        String pkgRoot = pojoConfig.getPkgRoot();
        String author = pojoConfig.getAuthor();
        String since = pojoConfig.getSince();
        String entityName = pojoInfo.getName();
        boolean enableSwagger = pojoConfig.isEnableSwagger();
        String currPkg = buildPkg(pkgRoot, this);
        // 个性化配置
        Map<String, Object> personalizedProperties = pojoConfig.getPersonalizedConfigMap().getOrDefault(outputType(), new HashMap<>(4));
        List<String> importClasses = new ArrayList<>(32);
        String superClassType = pojoInfo.getSuperClassType();
        if (isReq && StringUtils.isBlank(superClassType)) {
            Object personalizedSuperClassClassName = personalizedProperties.get(superClassClassName);
            Object personalizedSuperClassClassLongName = personalizedProperties.get(superClassClassLongName);
            if (personalizedSuperClassClassName != null && personalizedSuperClassClassLongName != null) {
                superClassType = personalizedSuperClassClassName.toString();
                importClasses.add(personalizedSuperClassClassLongName.toString());
            } else {
                personalizedProperties.remove(superClassClassName);
                personalizedProperties.remove(superClassClassLongName);
            }
        }
        //  lombok 涉及的包
        boolean enableLombok = isEnableLombok(pojoConfig, superClassType, importClasses);
        //  swagger 涉及的包
        if (enableSwagger) {
            importClasses.add(Schema.class.getName());
        }
        
        //  字段及类上涉及的包
        List<FieldInfo> fieldList = pojoInfo.getFieldList();
        List<TemplatePlaceholder.EntityField> entityFields = fieldList.stream()
                .map(x ->
                        (TemplatePlaceholder.EntityField) TemplatePlaceholder.EntityField.builder()
                                .name(x.getName())
                                .capitalName(StringUtils.capitalize(x.getName()))
                                .type(x.getType())
                                .comment(x.getComment())
                                .build()
                ).toList();
        fieldList.forEach(x -> importClasses.addAll(x.getImportClasses()));
        importClasses.addAll(pojoInfo.getImportClasses());
        
        return TemplatePlaceholder.EntityReqRespVO.builder()
                .name(outputName(entityName))
                .comment(pojoInfo.getComment())
                .pkg(currPkg)
                .author(author)
                .since(since)
                .enableLombok(enableLombok)
                .enableSwagger(enableSwagger)
                .superClassType(superClassType)
                .fields(entityFields)
                .importClasses(handleImportClasses(importClasses, currPkg))
                .build();
    }
    
    /**
     * 构建{@link AbstractOutputConfig#OUTPUT_TYPE_ENTITY}对应的占位符模板参数
     */
    protected TemplatePlaceholder buildEntityTemplatePlaceholder(@Nonnull PojoInfo pojoInfo) {
        PojoConfig pojoConfig = pojoInfo.getPojoConfig();
        String pkgRoot = pojoConfig.getPkgRoot();
        String author = pojoConfig.getAuthor();
        String since = pojoConfig.getSince();
        String entityName = pojoInfo.getName();
        boolean enableSwagger = pojoConfig.isEnableSwagger();
        String currPkg = buildPkg(pkgRoot, this);
        // 个性化配置
        String superClassType = pojoInfo.getSuperClassType();
        boolean enableSerializable = pojoConfig.isEnableSerializable();
        Set<String> superInterfaceTypes = pojoInfo.getSuperInterfaceTypes();
        List<String> importClasses = new ArrayList<>(32);
        //  序列化 涉及的包
        if(enableSerializable) {
            superInterfaceTypes.add(Serializable.class.getSimpleName());
            importClasses.add(Serializable.class.getName());
            importClasses.add(Serial.class.getName());
        }
        boolean enableLombok = isEnableLombok(pojoConfig, superClassType, importClasses);
        //  swagger 涉及的包
        if (enableSwagger) {
            importClasses.add(Schema.class.getName());
        }
        
        boolean noSuperInterface = CollectionUtils.isEmpty(superInterfaceTypes);
        String superInterfaceTypesStr = noSuperInterface ? null : String.join(", ", superInterfaceTypes);
        
        //  字段及类上涉及的包
        List<FieldInfo> fieldList = pojoInfo.getFieldList();
        List<TemplatePlaceholder.EntityField> entityFields = fieldList.stream()
                .map(x ->
                        (TemplatePlaceholder.EntityField) TemplatePlaceholder.EntityField.builder()
                                .name(x.getName())
                                .capitalName(StringUtils.capitalize(x.getName()))
                                .type(x.getType())
                                .comment(x.getComment())
                                .build()
                ).toList();
        fieldList.forEach(x -> importClasses.addAll(x.getImportClasses()));
        importClasses.addAll(pojoInfo.getImportClasses());
        
        return TemplatePlaceholder.Entity.builder()
                .name(outputName(entityName))
                .comment(pojoInfo.getComment())
                .pkg(currPkg)
                .author(author)
                .since(since)
                .enableLombok(enableLombok)
                .enableSerializable(enableSerializable)
                .enableSwagger(enableSwagger)
                .superClassType(superClassType)
                .superInterfaceTypes(superInterfaceTypesStr)
                .fields(entityFields)
                .importClasses(handleImportClasses(importClasses, currPkg))
                .build();
    }
    
    /**
     * 是否启用了lombok （如果为true, 那么久将相应包加入importClasses中）
     *
     * @param pojoConfig 模型信息
     * @param superClassType 父类
     * @param importClasses 要导入的包容器
     *
     * @return  是否启用了lombok
     */
    public static boolean isEnableLombok(PojoConfig pojoConfig, String superClassType, List<String> importClasses) {
        //  lombok 涉及的包
        boolean enableLombok = pojoConfig.isEnableLombok();
        if (enableLombok) {
            importClasses.add(Data.class.getName());
            if (StringUtils.isNotBlank(superClassType)) {
                importClasses.add(EqualsAndHashCode.class.getName());
            }
        }
        return enableLombok;
    }
    
}
