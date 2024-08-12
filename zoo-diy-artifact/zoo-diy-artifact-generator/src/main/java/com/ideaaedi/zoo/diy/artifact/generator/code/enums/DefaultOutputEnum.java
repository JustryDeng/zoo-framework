package com.ideaaedi.zoo.diy.artifact.generator.code.enums;

import com.ideaaedi.zoo.diy.artifact.generator.code.config.AbstractOutputConfig;
import com.ideaaedi.zoo.diy.artifact.generator.code.config.BasicOutputConfig;
import com.ideaaedi.zoo.diy.artifact.generator.code.config.OutputConfig;

import javax.annotation.Nonnull;

/**
 * 默认可选的文件输出类型
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public enum DefaultOutputEnum implements BasicOutputConfig {
    
    entity(AbstractOutputConfig.OUTPUT_TYPE_ENTITY, "", ".java", "entity"),
    entity_req_vo(AbstractOutputConfig.OUTPUT_TYPE_ENTITY_REQ_VO, "ReqVO", ".java", "entity.vo.req"),
    entity_resp_vo(AbstractOutputConfig.OUTPUT_TYPE_ENTITY_RESP_VO, "RespVO", ".java", "entity.vo.resp"),
    entity_list_req_vo(AbstractOutputConfig.OUTPUT_TYPE_ENTITY_LIST_REQ_VO, "ListReqVO", ".java", "entity.vo.req"),
    entity_list_resp_vo(AbstractOutputConfig.OUTPUT_TYPE_ENTITY_LIST_RESP_VO, "ListRespVO", ".java", "entity.vo.resp"),
    controller(AbstractOutputConfig.OUTPUT_TYPE_CONTROLLER, "Controller", ".java", "controller"),
    service(AbstractOutputConfig.OUTPUT_TYPE_SERVICE, "Service", ".java", "service"),
    service_impl(AbstractOutputConfig.OUTPUT_TYPE_SERVICE_IMPL, "ServiceImpl", ".java", "service.impl"),
    mapper(AbstractOutputConfig.OUTPUT_TYPE_MAPPER, "Mapper", ".java", "mapper"),
    mapper_xml(AbstractOutputConfig.OUTPUT_TYPE_MAPPER_XML, "Mapper", ".xml", "mapper");
    
    /** 输出类型 */
    private final String outputType;
    
     /** 输出名称（） */
    private final String outputName;
    
     /** 输出文件后缀 */
    private final String outputSuffix;
    
     /**
      * @see OutputConfig
      */
    private final String relativePkg;
    
    DefaultOutputEnum(String outputType, String outputName, String outputSuffix, String relativePkg) {
        this.outputType = outputType;
        this.outputName = outputName;
        this.outputSuffix = outputSuffix;
        this.relativePkg = relativePkg;
    }
    
    @Override
    public String outputType() {
        return this.outputType;
    }
    
    @Override
    public String outputSuffix() {
        return this.outputSuffix;
    }
    
    @Override
    public String outputName(@Nonnull String pojoName) {
        return pojoName + this.outputName;
    }
    
    @Override
    public String relativePkg() {
        return this.relativePkg;
    }
}
