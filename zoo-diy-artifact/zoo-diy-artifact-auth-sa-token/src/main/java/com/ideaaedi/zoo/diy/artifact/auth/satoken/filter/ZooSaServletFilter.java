package com.ideaaedi.zoo.diy.artifact.auth.satoken.filter;

import cn.dev33.satoken.filter.SaServletFilter;
import com.ideaaedi.zoo.commonbase.zoo_util.ZooContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class ZooSaServletFilter extends SaServletFilter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            super.doFilter(request, response, chain);
        } finally {
            // 清除用户上下文信息
            ZooContext.clear();
        }
    }
    
}
