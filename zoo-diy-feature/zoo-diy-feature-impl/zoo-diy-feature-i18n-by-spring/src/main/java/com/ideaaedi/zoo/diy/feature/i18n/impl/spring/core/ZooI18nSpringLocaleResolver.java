package com.ideaaedi.zoo.diy.feature.i18n.impl.spring.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * {@link Locale}解析器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class ZooI18nSpringLocaleResolver extends AbstractLocaleResolver {
    
    /** local信息key, 此key对应的value应为【语言_国家】或【语言_地区】，如：【zh_CN】 */
    private static final String LOCAL_KEY = "lang";
    
    private static final Pattern LOCAL_PATTERN = Pattern.compile("[a-z]+_[A-Z]+");
    
    public ZooI18nSpringLocaleResolver() {
    }
    
    @Nonnull
    @Override
    public Locale resolveLocale(@Nonnull HttpServletRequest request) {
        // 从请求头中拿前端传的local信息
        String localInfo = request.getHeader(LOCAL_KEY);
        boolean legal = StringUtils.isNotBlank(localInfo) && LOCAL_PATTERN.matcher(localInfo).matches();
        Locale locale;
        if (legal) {
            try {
                int splitSignIndex = localInfo.indexOf("_");
                String language = localInfo.substring(0, splitSignIndex);
                String country = localInfo.substring(splitSignIndex + 1);
                locale = new Locale(language, country);
                return locale;
            } catch (Exception e) {
                log.warn("parse Local instance occur error. e.getMessage() -> {}", e.getMessage());
            }
        }
        Locale defaultLocale = getDefaultLocale();
        locale = defaultLocale != null ? defaultLocale : request.getLocale();
        if (locale == null) {
            locale = Locale.SIMPLIFIED_CHINESE;
        }
        return locale;
    }
    
    @Override
    public void setLocale(@Nonnull HttpServletRequest request, HttpServletResponse response, Locale locale) {
        throw new UnsupportedOperationException("Cannot change HTTP lang header - use a different locale resolution strategy");
    }
    
}