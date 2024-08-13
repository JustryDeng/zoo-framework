package com.ideaaedi.zoo.diy.artifact.mybatisplus.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.ideaaedi.zoo.diy.artifact.mybatisplus.mapper.BaseMapperExt;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * 自定义sql注入器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@MapperScan("com.ideaaedi.zoo.diy.artifact.mybatisplus.mapper")
public class MybatisPlusSqlInjector extends DefaultSqlInjector {
    
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        methodList.add(new BaseMapperExt.ForceSelectById());
        methodList.add(new BaseMapperExt.ForceSelectBatchByIds());
        methodList.add(new BaseMapperExt.ForceSelectCount());
        methodList.add(new BaseMapperExt.ForceSelectList());
        methodList.add(new BaseMapperExt.ForceDeleteById());
        methodList.add(new BaseMapperExt.ForceDeleteBatchByIds());
        methodList.add(new BaseMapperExt.ForceDelete());
        return methodList;
    }
}