package com.ideaaedi.zoo.commonbase.zoo_component.logging;

import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.io.Serializable;

/**
 * trace工具
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public final class TraceBaseUtil {
    
    /**
     * 将用户id写入TRACE_XD中
     *
     * @param userId 用户id
     */
    public static void fillUserId2TraceXd(Serializable userId) {
        if (userId == null) {
            return;
        }
        String traceXd = MDC.get(BaseConstant.TRACE_XD);
        if (StringUtils.isBlank(traceXd)) {
            return;
        }
        if (traceXd.contains("%s")) {
            String newTraceXd = String.format(traceXd, userId);
            MDC.put(BaseConstant.TRACE_XD, newTraceXd);
            log.info("upgrade traceXd from {} to {}", traceXd, newTraceXd);
        }
    }

}
