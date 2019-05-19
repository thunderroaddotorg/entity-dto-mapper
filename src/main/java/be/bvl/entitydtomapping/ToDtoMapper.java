package be.bvl.entitydtomapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public interface ToDtoMapper<T, DTO> {

    Logger logger = LoggerFactory.getLogger(ToDtoMapper.class);

    default void toDto(T entity, DTO dto) {
        BeanUtils.copyProperties(entity, dto);

        for (Field field : Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> Arrays.stream(field.getType().getInterfaces()).collect(Collectors.toList())
                        .contains(ToDtoMapper.class)).collect(Collectors.toList())) {

            try {
                Field fieldDto = dto.getClass().getDeclaredField(field.getName());
                PropertyDescriptor pdGet = new PropertyDescriptor(field.getName(), entity.getClass());
                PropertyDescriptor pdSet = new PropertyDescriptor(field.getName(), dto.getClass());
                Object newInstance = pdSet.getPropertyType().newInstance();
                Object obj = pdGet.getReadMethod().invoke(entity);
                ((ToDtoMapper<T, DTO>)obj).toDto((T) obj, (DTO)newInstance);
                pdSet.getWriteMethod().invoke(dto, newInstance);
            } catch (IntrospectionException
                    | IllegalAccessException
                    | IllegalArgumentException
                    | InvocationTargetException
                    | InstantiationException
                    | NoSuchFieldException e) {
                logger.debug(e.getMessage(), e);
            }
        }

        for (Field field : Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> Arrays.stream(field.getType().getInterfaces()).collect(Collectors.toList())
                        .contains(Collection.class)).collect(Collectors.toList())) {

            try {
                Field fieldDto = dto.getClass().getDeclaredField(field.getName());
                PropertyDescriptor pdGet = new PropertyDescriptor(field.getName(), entity.getClass());
                PropertyDescriptor pdSet = new PropertyDescriptor(field.getName(), dto.getClass());
                Class<?> entityListType = pdGet.getPropertyType();
                Class<?> dtoListType = pdSet.getPropertyType();

                if (List.class.equals(fieldDto.getType()) && List.class.equals(field.getType())) {
                    logger.debug("List entity");
                } else if (Set.class.equals(fieldDto.getType()) && Set.class.equals(field.getType())) {
                    logger.debug("Set entity");
                } else if (Map.class.equals(fieldDto.getType()) && Map.class.equals(field.getType())) {
                    logger.debug("Map entity");
                } else throw new UnsupportedOperationException();

            } catch (IntrospectionException
//                    | IllegalAccessException
                    | IllegalArgumentException
//                    | InvocationTargetException
//                    | InstantiationException
                    | NoSuchFieldException e) {
                logger.debug(e.getMessage(), e);
            }
        }
    }

}
