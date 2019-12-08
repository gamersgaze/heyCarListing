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

    protected <E extends BaseEntity, D extends BaseDTO> E mapToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }


//    protected <D extends BaseDTO, E extends BaseEntity> D mapToDTO(E entity, Class<D> dtoClass) {
//        return modelMapper.map(entity, dtoClass);
//    }


//    protected <S extends BaseDTO, E extends BaseEntity> List<E> mapAll(Collection<S> dtos, Class<E> entityClass) {
//        if (nonNull(dtos)) {
//            return dtos.stream().filter(Objects::nonNull).map(e -> modelMapper.map(e, entityClass)).collect(Collectors.toList());
//        }
//        return Collections.emptyList();
//    }
}
