package com.baseApp.backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;

@Component
public class TranslateUtils {
    private static ResourceBundleMessageSource messageSource;

    private static String fileBaseName =  "messages";

    @Autowired
    TranslateUtils(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public static String tl(String msgCode, @Nullable Object ...args) {
        messageSource.setBasename(fileBaseName);
        Locale locale = LocaleContextHolder.getLocale();
        if (args == null){
            return messageSource.getMessage(msgCode, null, locale);
        } else {
            return String.format(messageSource.getMessage(msgCode, null, locale), Arrays.stream(args).toArray(Object[]::new));
        }
    }

    public static String tl(String msgCode, Locale locale, @Nullable Object ...args) {
        messageSource.setBasename(fileBaseName);
        if (args == null){
            return messageSource.getMessage(msgCode, null, locale);
        } else {
            return String.format(messageSource.getMessage(msgCode, null, locale), Arrays.stream(args).toArray(Object[]::new));
        }
    }

    public static String tl(String msgCode, String fileBaseName, Locale locale, @Nullable Object ...args) {
        messageSource.setBasename(fileBaseName);

        if (args == null){
            return messageSource.getMessage(msgCode, null, locale);
        } else {
            return String.format(messageSource.getMessage(msgCode, null, locale), Arrays.stream(args).toArray(Object[]::new));
        }
    }
}
