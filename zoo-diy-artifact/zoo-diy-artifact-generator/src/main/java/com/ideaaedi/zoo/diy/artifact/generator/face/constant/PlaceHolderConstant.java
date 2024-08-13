package com.ideaaedi.zoo.diy.artifact.generator.face.constant;

/**
 * 模板占位符信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface PlaceHolderConstant {
    
    /**
     * 统一响应类 占位符
     * <pre>
     * @see com.ideaaedi.zoo.commonbase.entity.Result
     * </pre>
     */
    String RESULT_CLASS_NAME = "Placeholder_Result";
    
    /**
     * 分页信息类 占位符
     * <pre>
     * @see com.ideaaedi.zoo.commonbase.entity.PageInfo
     * </pre>
     */
    String PAGE_INFO_CLASS_NAME = "Placeholder_PageInfo";
    
    /**
     * 请求体基类 占位符
     * <pre>
     * @see com.ideaaedi.zoo.commonbase.entity.BaseDTO
     * </pre>
     */
    String BASE_DTO_CLASS_NAME = "Placeholder_BaseDTO";
    
    /**
     * 请求体分页基类 占位符
     * <pre>
     * @see com.ideaaedi.zoo.commonbase.entity.BasePageDTO
     * </pre>
     */
    String BASE_PAGE_DTO_CLASS_NAME = "Placeholder_BasePageDTO";
    
    /**
     * spring中是否存在mybatis plus ext
     */
    String EXIST_MYBATIS_PLUS_EXT = "Placeholder_ExistMybatisPlusExt";
    
    /**
     * spring中是否存在knife4j ext
     */
    String EXIST_KNIFE4J_EXT = "Placeholder_ExistKnife4jExt";
    
    /**
     * app中是否存在easy-excel类
     */
    String EXIST_EASY_EXCEL = "Placeholder_ExistEasyExcel";
    
    /** controller 统一响应类 */
    String UNIFIED_CLASS_NAME_4_Result = "unified_result_class_name";
    String UNIFIED_CLASS_LONG_NAME_4_Result = "unified_result_class_long_name";
    
    /** controller 统一分页包装类 */
    String UNIFIED_CLASS_NAME_4_PageInfo = "unified_pageInfo_class_name";
    String UNIFIED_CLASS_LONG_NAME_4_PageInfo = "unified_pageInfo_class_long_name";
    
    /** controller 请求体基类 */
    String UNIFIED_CLASS_NAME_4_BaseDTO = "unified_baseDto_class_name";
    String UNIFIED_CLASS_LONG_NAME_4_BaseDTO = "unified_baseDto_class_long_name";
    
    /** controller 分页请求请求体基类 */
    String UNIFIED_CLASS_NAME_4_BasePageDTO = "unified_basePageDto_class_name";
    String UNIFIED_CLASS_LONG_NAME_4_BasePageDTO = "unified_basePageDto_class_long_name";
    String IMPORT_CONTROLLER_CLASSES = "default_import_controller_classes";
    String IMPORT_SERVICE_IMPL_CLASSES = "default_import_serviceImpl_classes";
    
}
