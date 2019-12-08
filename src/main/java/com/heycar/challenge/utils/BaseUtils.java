package com.heycar.challenge.utils;

import com.heycar.challenge.config.I18nMessages;
import com.heycar.challenge.entities.BaseEntity;
import com.heycar.challenge.models.BaseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static com.heycar.challenge.utils.HeyCarConstants.DEFAULT_LOCALE_FALLBACK;

public abstract class BaseUtils {

    @Autowired
    private I18nMessages i18nMessages;

    @Autowired
    protected ModelMapper modelMapper;

    protected String i18nMessage(String key, Object... args) {
        return i18nMessages.get(key, DEFAULT_LOCALE_FALLBACK, args);
    }

    /*
     * this will map the properties of one class to another, it internally use Java Reflection API
     * I have used generics , because it should work with all types of classes
     */
    protected <E extends BaseEntity, D extends BaseDTO> E mapToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

}
