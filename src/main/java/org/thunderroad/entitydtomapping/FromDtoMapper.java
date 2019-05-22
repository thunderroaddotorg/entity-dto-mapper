package org.thunderroad.entitydtomapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Interface to add conversion method from a DTO bean of type DTO to a entity bean of type T.
 * @param <T>
 * @param <DTO>
 *
 * @author Van Lommel Bart
 */
public interface FromDtoMapper<T, DTO> {

    Logger logger = LoggerFactory.getLogger(FromDtoMapper.class);

    /**
     * Conversion method to convert the DTO bean into the entity bean that represents it.
     * Members that implement the FromDtoMapper interface will also be converted.
     * Members that are Collections of type java.util.List or java.util.Set will be converted
     * in a java.uti.List or java.util.Set of the corresponding entity bean types.
     * Members that are of type Map will be converted in a Map where the key and/or value will be
     * converted if they are from a type that implements the FromDtoMapper interface.
     *
     * @param dto the DTO bean that needs to be converted. (Is at the same time the calling object)
     * @param entity the entity bean that the entity will be converted into. (needs to be instantiated before)
     */
    default void fromDto(DTO dto, T entity) {
        BeanUtils.copyProperties(dto, entity);
        for (Field fieldDto : Arrays.stream(dto.getClass().getDeclaredFields())
                .filter(field -> Arrays.stream(field.getType().getInterfaces()).collect(Collectors.toList())
                        .contains(FromDtoMapper.class)).collect(Collectors.toList())) {

            try {
                PropertyDescriptor pdGet = new PropertyDescriptor(fieldDto.getName(), dto.getClass());
                PropertyDescriptor pdSet = new PropertyDescriptor(fieldDto.getName(), entity.getClass());
                Object newInstance = pdSet.getPropertyType().newInstance();
                Object obj = pdGet.getReadMethod().invoke(dto);
                ((FromDtoMapper<T, DTO>)obj).fromDto((DTO) obj, (T)newInstance);
                pdSet.getWriteMethod().invoke(entity, newInstance);
            } catch (IntrospectionException
                    | IllegalAccessException
                    | IllegalArgumentException
                    | InvocationTargetException
                    | InstantiationException e) {
                logger.debug(e.getMessage(), e);
            }
        }

        for (Field fieldDto : Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> Arrays.stream(field.getType().getInterfaces()).collect(Collectors.toList())
                        .contains(Collection.class)).collect(Collectors.toList())) {

            try {
                Field field = entity.getClass().getDeclaredField(fieldDto.getName());
                PropertyDescriptor pdGet = new PropertyDescriptor(fieldDto.getName(), dto.getClass());
                PropertyDescriptor pdSet = new PropertyDescriptor(field.getName(), entity.getClass());
                // calculate source and target type
                Type typeSource = ((ParameterizedType) fieldDto.getGenericType()).getActualTypeArguments()[0];
                Type typeTarget = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

                if (Arrays.stream(((Class) typeSource).getInterfaces()).collect(Collectors.toList()).contains(FromDtoMapper.class)
                        && List.class.equals(fieldDto.getType())
                        && List.class.equals(field.getType())) {
                    logger.debug("List entity");
                    List<DTO> listOrig = (List<DTO>) pdGet.getReadMethod().invoke(dto);
                    List<T> listTarget = new ArrayList<>();
                    for (DTO element : listOrig) {
                        T newInstance = (T) ((Class) typeTarget).newInstance();
                        ((FromDtoMapper<T, DTO>) element).fromDto(element, newInstance);
                        listTarget.add(newInstance);
                    }
                    pdSet.getWriteMethod().invoke(entity, listTarget);
                } else if (Arrays.stream(((Class) typeSource).getInterfaces()).collect(Collectors.toList()).contains(FromDtoMapper.class)
                        && Set.class.equals(fieldDto.getType())
                        && Set.class.equals(fieldDto.getType())) {
                    logger.debug("Set entity");
                    Set<DTO> setOrig = (Set<DTO>) pdGet.getReadMethod().invoke(dto);
                    Set<T> setTarget = new HashSet<>();
                    for (DTO element : setOrig) {
                        T newInstance = (T) ((Class) typeTarget).newInstance();
                        ((FromDtoMapper<T, DTO>) element).fromDto(element, newInstance);
                        setTarget.add(newInstance);
                    }
                    pdSet.getWriteMethod().invoke(entity, setTarget);
                } else {
                    throw new UnsupportedOperationException(this.getClass().getTypeName() + " holds a member that is a java.util.Collection other than java.util.List or java.util.Set.");
                }
            } catch (IntrospectionException
                    | IllegalAccessException
                    | IllegalArgumentException
                    | InvocationTargetException
                    | InstantiationException
                    | NoSuchFieldException e) {
                logger.debug(e.getMessage(), e);
            }
        }

        for (Field fieldDto : Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> field.getType().equals(Map.class))
                .collect(Collectors.toList())) {

            try {
                logger.debug("Map field " + fieldDto.getName());
                Type keyTypeDto = ((ParameterizedType)fieldDto.getGenericType()).getActualTypeArguments()[0];
                Type valueTypeDto = ((ParameterizedType)fieldDto.getGenericType()).getActualTypeArguments()[1];

                boolean srcMappedKeyType = Arrays.stream(((Class) keyTypeDto).getInterfaces()).collect(Collectors.toList()).contains(FromDtoMapper.class);
                boolean srcMappedValueType = Arrays.stream(((Class) valueTypeDto).getInterfaces()).collect(Collectors.toList()).contains(FromDtoMapper.class);

                if (!srcMappedKeyType && !srcMappedValueType) {
                    continue;
                }
                Field field = entity.getClass().getDeclaredField(fieldDto.getName());
                Type keyTypeEntity = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
                Type valueTypeEntity = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[1];

                PropertyDescriptor pdGet = new PropertyDescriptor(fieldDto.getName(), dto.getClass());
                PropertyDescriptor pdSet = new PropertyDescriptor(field.getName(), entity.getClass());

                Map srcMap = (Map)pdGet.getReadMethod().invoke(dto);
                Map targetMap = new HashMap<>();
                Object key = null;
                Object value = null;
                for (Object dtoKey : srcMap.keySet()) {
                    if (srcMappedKeyType) {
                        T keyEntity = (T)((Class)keyTypeEntity).newInstance();
                        ((FromDtoMapper<T,DTO>)dtoKey).fromDto((DTO)dtoKey, keyEntity);
                        key = keyEntity;
                    } else {
                        key = dtoKey;
                    }
                    Object dtoValue = srcMap.get(dtoKey);
                    if (srcMappedValueType) {
                        T valueEntity = (T)((Class)valueTypeEntity).newInstance();
                        ((FromDtoMapper<T,DTO>)dtoValue).fromDto((DTO)dtoValue, valueEntity);
                        value = valueEntity;
                    } else {
                        value = dtoValue;
                    }
                    targetMap.put(key, value);
                }
                pdSet.getWriteMethod().invoke(entity, targetMap);

            } catch (NoSuchFieldException
                    | IntrospectionException
                    | IllegalAccessException
                    | InvocationTargetException
                    | InstantiationException e) {
                e.printStackTrace();
            }

        }

    }

}
