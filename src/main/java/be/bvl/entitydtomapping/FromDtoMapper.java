package be.bvl.entitydtomapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

public interface FromDtoMapper<T, DTO> {

    Logger logger = LoggerFactory.getLogger(FromDtoMapper.class);

    default void fromDto(DTO dto, T entity) {
        BeanUtils.copyProperties(dto, entity);
        for (Field field : Arrays.stream(dto.getClass().getDeclaredFields())
                .filter(field -> Arrays.stream(field.getType().getInterfaces()).collect(Collectors.toList())
                        .contains(FromDtoMapper.class)).collect(Collectors.toList())) {

            try {
                Field fieldDto = dto.getClass().getDeclaredField(field.getName());
                    PropertyDescriptor pdGet = new PropertyDescriptor(field.getName(), dto.getClass());
                    PropertyDescriptor pdSet = new PropertyDescriptor(field.getName(), entity.getClass());
                    Object newInstance = pdSet.getPropertyType().newInstance();
                    Object obj = pdGet.getReadMethod().invoke(dto);
                    ((FromDtoMapper<T, DTO>)(DTO) obj).fromDto((DTO) obj, (T)newInstance);
                    pdSet.getWriteMethod().invoke(entity, newInstance);
            } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | NoSuchFieldException e) {
                logger.debug(e.getMessage(), e);
            }
        }

    }

}
