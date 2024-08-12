package com.ideaaedi.zoo.diy.artifact.logging.micrometer.filter;

import com.ideaaedi.zoo.commonbase.constant.AutoConfigurationConstant;
import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import com.ideaaedi.zoo.diy.artifact.logging.micrometer.util.TraceUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.PriorityOrdered;

import java.io.IOException;

/**
 * 记录traceXd信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class TraceXdFilter implements Filter, PriorityOrdered {
    
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            if (request instanceof HttpServletRequest httpServletRequest) {
                String headerTraceXd = httpServletRequest.getHeader(BaseConstant.TRACE_XD);
                boolean valid = TraceUtil.validateTraceXd(headerTraceXd);
                if (valid) {
                    TraceUtil.injectTraceXd(headerTraceXd);
                } else {
                    TraceUtil.generateAndInjectTraceXd(null, httpServletRequest.getServletPath());
                }
            }
            chain.doFilter(request, response);
        } finally {
            TraceUtil.cleanTraceXd();
        }
    }
    
    @Override
    public int getOrder() {
        return AutoConfigurationConstant.ZOO_LOGGING_MICROMETER_PRIORITY_ORDER;
    }
}
