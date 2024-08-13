package com.ideaaedi.zoo.diy.feature.i18n.impl.spring.core;

import com.ideaaedi.zoo.diy.feature.i18n.api.provider.I18Translator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * i18n工具类
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public final class SpringI18Translator implements I18Translator {
    
    private static final Object[] EMPTY_ARRAY = new Object[0];
    
    private final MessageSource messageSource;
    
    public SpringI18Translator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    @Nonnull
    @Override
    public String i18n(@Nonnull String key) {
        if (messageSource == null) {
            return key;
        }
        try {
            return messageSource.getMessage(key, EMPTY_ARRAY, LocaleContextHolder.getLocale());
        } catch (Exception ignore) {
        }
        return key;
    }

    @Nonnull
    @Override
    public String i18n(@Nonnull String key, Locale locale) {
        if (messageSource == null) {
            return key;
        }
        try {
            return messageSource.getMessage(key, EMPTY_ARRAY, locale);
        } catch (Exception ignore) {
        }
        return key;
    }

    @Nonnull
    @Override
    public String i18n(@Nonnull String key, Object... args) {
        if (messageSource == null) {
            return key;
        }
        try {
            return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
        } catch (Exception ignore) {
        }
        return key;
    }

    @Nonnull
    @Override
    public String i18n(@Nonnull String key, Locale locale, Object... args) {
        if (messageSource == null) {
            return key;
        }
        try {
            return messageSource.getMessage(key, args, locale);
        } catch (Exception ignore) {
        }
        return key;
    }
}
