package com.ideaaedi.zoo.diy.artifact.generator.code.entity;

import com.ideaaedi.zoo.diy.artifact.generator.code.enums.DefaultOutputEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 模板参数占位符
 * <p>
 * 模板中所有的变量在这里都能找到
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TemplatePlaceholder {
    
    /**
     * 作者
     */
    private String author;
    
    /**
     * since
     */
    private String since;
    
    /**
     * 类名
     */
    private String name;
    
    /**
     * 注释
     */
    private String comment;
    
    /**
     * 包名
     */
    private String pkg;
    
    /**
     * 导包
     */
    private Set<String> importClasses = new LinkedHashSet<>(32);
    
    /**
     * 是否启用lombok注解
     */
    private boolean enableLombok = true;
    
    /**
     * 个性化字段
     */
    private Map<String, Object> properties = new HashMap<>(8);
    
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Entity extends TemplatePlaceholder {
    
        /**
         * 是否启用swagger注解
         */
        private boolean enableSwagger = true;
        
        /**
         * 是否可jdk序列化
         */
        private boolean enableSerializable = true;
    
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
         * 如：{@code BaseDTO }
         * 如：{@code Holder<BaseDTO>, BaseDTO}
         * </pre>
         */
        private String superInterfaceTypes;
        
        /**
         * 是否可jdk序列化
         */
        private List<EntityField> fields = new ArrayList<>(32);
    }
    
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class EntityReqRespVO extends TemplatePlaceholder {
    
        /**
         * 是否启用swagger注解
         */
        private boolean enableSwagger = true;
    
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
         * 是否可jdk序列化
         */
        private List<EntityField> fields = new ArrayList<>(32);
    }
    
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class EntityRespVO extends TemplatePlaceholder {
    
        /**
         * 是否启用swagger注解
         */
        private boolean enableSwagger = true;
        
        /**
         * 是否可jdk序列化
         */
        private List<EntityField> fields = new ArrayList<>(32);
    }
    
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Controller extends TemplatePlaceholder {
    
        /**
         * 是否启用swagger注解
         */
        private boolean enableSwagger = true;
        
        /**
         * 当前应用中，是否用到了zoo-diy-artifact-generator组件对swagger进行增强
         */
        private boolean hasZooKnife4jExt;
    
        /**
         * {@link DefaultOutputEnum#entity_req_vo}的简易类名
         */
        private String entityReqVoName;
    
        /**
         * {@link DefaultOutputEnum#entity_resp_vo}的简易类名
         */
        private String entityRespVoName;
        
        /**
         * {@link DefaultOutputEnum#entity_list_req_vo}的简易类名
         */
        private String entityListReqVoName;
    
        /**
         * {@link DefaultOutputEnum#entity_list_resp_vo}的简易类名
         */
        private String entityListRespVoName;
        
        /**
         * {@link DefaultOutputEnum#service}的简易类名
         */
        private String serviceName;
    
        /**
         * {@link DefaultOutputEnum#service}的简易类名
         */
        private String unCapitalServiceName;
        
        /**
         * 模型名称
         */
        private String pojoNameHyphen;
    }
    
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Service extends TemplatePlaceholder {
    
        /**
         * {@link DefaultOutputEnum#entity_req_vo}的简易类名
         */
        private String entityReqVoName;
    
        /**
         * {@link DefaultOutputEnum#entity_resp_vo}的简易类名
         */
        private String entityRespVoName;
        
        /**
         * {@link DefaultOutputEnum#entity_list_req_vo}的简易类名
         */
        private String entityListReqVoName;
    
        /**
         * {@link DefaultOutputEnum#entity_list_resp_vo}的简易类名
         */
        private String entityListRespVoName;
    }
    
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class ServiceImpl extends TemplatePlaceholder {
    
        /**
         * {@link DefaultOutputEnum#entity_req_vo}的简易类名
         */
        private String entityReqVoName;
    
        /**
         * {@link DefaultOutputEnum#entity_resp_vo}的简易类名
         */
        private String entityRespVoName;
        
        /**
         * {@link DefaultOutputEnum#entity_list_req_vo}的简易类名
         */
        private String entityListReqVoName;
    
        /**
         * {@link DefaultOutputEnum#entity_list_resp_vo}的简易类名
         */
        private String entityListRespVoName;
    
        /**
         * {@link DefaultOutputEnum#service}的简易类名
         */
        private String serviceName;
    
        /**
         * {@link DefaultOutputEnum#mapper}的简易类名
         */
        private String mapperName;
    
        /**
         * {@link DefaultOutputEnum#mapper}的简易类名
         */
        private String unCapitalMapperName;
    }
    
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class MapperXml extends TemplatePlaceholder {
        
        /**
         * {@link DefaultOutputEnum#mapper}的简易类名
         */
        private String mapperName;
    
        /**
         * {@link DefaultOutputEnum#mapper}的包
         */
        private String mapperPkg;
    }
    
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EntityField {
    
        /**
         * 注释
         */
        private String comment;
    
        /**
         * 名称
         */
        private String name;
    
        /**
         * 名称（首字母大写）
         */
        private String capitalName;
    
        /**
         * 字段的类型
         * <pre>
         * {@code
         *  如：
         *    String
         *    List<String>
         * }
         * </pre>
         */
        private String type;
    }
}
