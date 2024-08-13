package com.ideaaedi.zoo.commonbase.zoo_component.mybatisplus;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据库模型基类
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TableBaseDTO extends TableOperateDTO {

    @TableId("id")
    @Schema(description = "id")
    private Long id;

    @TableLogic
    @TableField("deleted")
    @Schema(description = "是否已删除(0-未删除；1-已删除)")
    private Integer deleted;
    
}