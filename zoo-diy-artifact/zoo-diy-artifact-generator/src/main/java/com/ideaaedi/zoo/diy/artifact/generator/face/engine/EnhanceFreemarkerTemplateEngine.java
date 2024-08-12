package com.ideaaedi.zoo.diy.artifact.generator.face.engine;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.ideaaedi.zoo.diy.artifact.generator.face.constant.PlaceHolderConstant;
import com.ideaaedi.zoo.diy.artifact.generator.properties.ZooGeneratorProperties;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 扩展FreemarkerTemplateEngine，生成更多相关文件
 */
public class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {
    
    private final ZooGeneratorProperties.JavaCurdCode javaCurdCode;
    
    public EnhanceFreemarkerTemplateEngine(ZooGeneratorProperties.JavaCurdCode javaCurdCode) {
        this.javaCurdCode = javaCurdCode;
    }
    
    /**
     * (non-javadoc)
     *
     * @param customFiles 自定义文件. CustomFile#fileName-生成的文件； CustomFile#templatePath-生成该文件使用的（classpath下的带模板文件后缀名的）模板文件
     * @param tableInfo 表信息
     * @param objectMap 模板可使用的占位符即对应变量值(即：objectMap里的key可以在模板文件中直接引用)
     */
    @Override
    protected void outputCustomFile(@Nonnull List<CustomFile> customFiles, @Nonnull TableInfo tableInfo,
                                    @Nonnull Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String entityPath = this.getPathInfo(OutputFile.entity);
        String originEntityName;
        if (entityName.endsWith("PO")) {
            originEntityName = entityName.substring(0, entityName.length() - 2);
        } else {
            originEntityName = entityName;
        }
        // 自定义一些key-value，以便在模板中获取对应的值
        String comment = tableInfo.getComment();
        String originEntityNameHyphenStyle = StrUtil.toSymbolCase(originEntityName, '-');
        objectMap.put("originEntityName", originEntityName);
        objectMap.put("customLowerServiceName", StrUtil.lowerFirst(tableInfo.getServiceName()));
        objectMap.put("originEntityNameHyphenStyle", originEntityNameHyphenStyle);
        objectMap.put("briefTableComment", StringUtils.isNotBlank(comment) ? comment.replace("表", "").trim() :
                originEntityNameHyphenStyle);
        objectMap.put("wrapControllerResult", javaCurdCode.isWrapControllerResult());
        objectMap.put("ifAddExcelPropertyAnno", javaCurdCode.isIfAddExcelPropertyAnno());
    
        objectMap.put(PlaceHolderConstant.RESULT_CLASS_NAME, javaCurdCode.getResultClassName());
        objectMap.put(PlaceHolderConstant.PAGE_INFO_CLASS_NAME, javaCurdCode.getPageInfoClassName());
        objectMap.put(PlaceHolderConstant.BASE_DTO_CLASS_NAME, javaCurdCode.getBaseDtoClassName());
        objectMap.put(PlaceHolderConstant.BASE_PAGE_DTO_CLASS_NAME, javaCurdCode.getBasePageDtoClassName());
        objectMap.put(PlaceHolderConstant.EXIST_MYBATIS_PLUS_EXT, BooleanUtils.isTrue(javaCurdCode.getExistMybatisPlusExt()));
        objectMap.put(PlaceHolderConstant.EXIST_KNIFE4J_EXT, BooleanUtils.isTrue(javaCurdCode.getExistKnife4jExt()));
        objectMap.put(PlaceHolderConstant.EXIST_EASY_EXCEL, BooleanUtils.isTrue(javaCurdCode.getExistEasyExcel()));
        
        Set<String> importPackages = tableInfo.getImportPackages();
        
        String superClassLongName;
        List<String> superClassImportList;
        try {
            Field entityField = TableInfo.class.getDeclaredField("entity");
            entityField.setAccessible(true);
            Entity entity = (Entity) entityField.get(tableInfo);
            superClassLongName = entity.getSuperClass();
            superClassImportList = Arrays.stream(Class.forName(superClassLongName).getDeclaredFields())
                    .map(field -> field.getType().getName())
                    .filter(x -> !x.startsWith("java.lang.")).collect(Collectors.toList());
        } catch (Exception e) {
            superClassLongName = null;
            superClassImportList = null;
        }
        String finalSuperClassImport = superClassLongName;
        LinkedHashSet<String> importPackageList = new LinkedHashSet<>(importPackages);
        if (!CollectionUtils.isEmpty(superClassImportList)) {
            importPackageList.addAll(superClassImportList);
        }
        Set<String> customImportPackages = importPackageList.stream()
                .filter(pack -> {
                    if ("java.io.Serializable".equals(pack)) {
                        return false;
                    }
                    if (pack.equals(finalSuperClassImport)) {
                        return false;
                    }
                    return !pack.startsWith("com.baomidou.");
                }).collect(Collectors.toCollection(TreeSet::new));
        objectMap.put("customImportPackages", customImportPackages);
        
        List<TableField> customAllFields = new ArrayList<>(32);
        List<TableField> commonFields = tableInfo.getCommonFields();
        if (!CollectionUtils.isEmpty(commonFields)) {
            customAllFields.addAll(commonFields);
        }
        List<TableField> fields = tableInfo.getFields();
        if (!CollectionUtils.isEmpty(fields)) {
            customAllFields.addAll(fields);
        }
        objectMap.put("customAllFields", customAllFields);
        String templateClasspathDir = javaCurdCode.getTemplateClasspathDir();
        // 生成xxxAddReqVO
        this.outputFile(new File(entityPath + File.separator + "req" + File.separator + originEntityName +
                "AddReqVO.java"), objectMap, templateClasspathDir + "PojoAddReqVO.ftl", true);
        this.outputFile(new File(entityPath + File.separator + "req" + File.separator + originEntityName +
                "UpdateReqVO.java"), objectMap, templateClasspathDir + "PojoUpdateReqVO.ftl", true);
        // 生成xxxListReqVO
        this.outputFile(new File(entityPath + File.separator + "req" + File.separator + originEntityName +
                "ListReqVO.java"), objectMap, templateClasspathDir + "PojoListReqVO.ftl", true);
        // 生成xxxBatchAddReqVO
        this.outputFile(new File(entityPath + File.separator + "req" + File.separator + originEntityName +
                        "BatchAddReqVO.java"), objectMap, templateClasspathDir + "PojoBatchAddReqVO.ftl",
                true);
        // 生成xxxListRespVO
        this.outputFile(new File(entityPath + File.separator + "resp" + File.separator + originEntityName +
                "ListRespVO.java"), objectMap, templateClasspathDir + "PojoListRespVO.ftl", true);
        // 生成xxxDetailRespVO
        this.outputFile(new File(entityPath + File.separator + "resp" + File.separator + originEntityName +
                        "DetailRespVO.java"), objectMap, templateClasspathDir + "PojoDetailRespVO.ftl",
                true);
        // 生成xxxBatchAddRespVO
        this.outputFile(new File(entityPath + File.separator + "resp" + File.separator + originEntityName +
                        "BatchAddRespVO.java"), objectMap, templateClasspathDir + "PojoBatchAddRespVO.ftl",
                true);
    }
}
