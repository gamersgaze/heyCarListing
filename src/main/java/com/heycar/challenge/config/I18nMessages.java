package com.heycar.challenge.config;

import com.heycar.challenge.utils.HeyCarConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

/*
 *  Used for messages
 */
@Component
public class I18nMessages {

    private final MessageSource messageSource;

    public I18nMessages(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String key, Locale locale, Object... args) {
        if (isNull(locale)) {
            locale = HeyCarConstants.DEFAULT_LOCALE_FALLBACK;
        }
        if (isBlank(key)) {
            throw new IllegalArgumentException("Message key must not be blank or null.");
        }
        return messageSource.getMessage(key, args, locale);
    }
}
