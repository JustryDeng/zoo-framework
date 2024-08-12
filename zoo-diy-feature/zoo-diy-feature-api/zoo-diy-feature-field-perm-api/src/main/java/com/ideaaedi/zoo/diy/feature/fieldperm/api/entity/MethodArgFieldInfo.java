package com.ideaaedi.zoo.diy.feature.fieldperm.api.entity;


import com.ideaaedi.zoo.diy.feature.fieldperm.api.enums.MethodArgFieldEnum;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.util.FieldPermUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;

/**
 * <p>
 * 系统-方法参数及字段信息 po模型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2021.0.5.C
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodArgFieldInfo {

    /**
     * 参数或参数中的字段唯一id
     * <br />
     * 区别是参数还是参数中的字段：
     * <ul>
     *     <li>方式一：通过{@link MethodArgFieldInfo#getLevel()}，等于1则为参数，大于1则为字段</li>
     *     <li>方式二：
     *     通过{@link MethodArgFieldInfo#getPath()} 本身的命名规则来判断，
     *     path的规则是 {argName}.{fieldName}....，如果只有argName的话，则说明是参数。
     *     命名规则详见{@link FieldPermUtil#buildArgUid(Method, ParameterNameDiscoverer)}
     *     </li>
     * </ul>
     */
    private String argOrFieldUid;
    
    /**
     * 方法参数的索引位置
     * <p>
     *  注：值 >= 0
     * </p>
     * <p>
     * 注：当当前数据代表方法的参数时，此值即为该参数在方法参数列表中的索引位置
     * </p>
     * <p>
     * 注：当当前数据代表方法参数中的字段时，此值即为该字段所属的方法参数在方法参数列表中的索引位置
     * </p>
     */
    private Integer argIndex;

    /**
     * 方法标识
     */
    private String methodUid;

    /**
     * 类型（REQ-请求字段；RESP-响应字段）
     */
    private MethodArgFieldEnum type;

    /**
     * 参数(字段)路径。 path的格式是：{argName}.{fieldName}....
     * <pre>
     *  示例1：req
     *  示例2：req.name
     *  示例3：req.age
     * </pre>
     */
    private String path;

    /**
     * 层级（顶层为1，子层依次+1）
     */
    private Integer level;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private String fieldType;
    
    /**
     * 字段描述
     */
    private String fieldDesc;
    
    /**
     * 是否可见（1-可见；0-不可见）
     */
    private Integer ifVisible;
}