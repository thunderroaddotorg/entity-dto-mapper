package be.bvl.entitydtomapping;

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
 * Interface to add conversion method from a entity bean of type T to a DTO bean of type DTO.
 * @param <T>
 * @param <DTO>
 *
 * @author Van Lommel Bart
 */
public interface ToDtoMapper<T, DTO> {

    Logger logger = LoggerFactory.getLogger(ToDtoMapper.class);

    /**
     * Conversion method to convert the entity bean into the DTO bean that represents it.
     * Members that implement the ToDtoMapper interface will also be converted.
     * Members that are Collections of type java.util.List or java.util.Set will be converted
     * in a java.uti.List or java.util.Set of the corresponding DTO bean types.
     * Members that are of type Map will be converted in a Map where the key and/or value will be
     * converted if they are from a type that implements the ToDtoMapper interface.
     *
     * @param entity the entity bean that needs to be converted. (Is at the same time the calling object)
     * @param dto the DTO bean that the entity will be converted into. (needs to be instantiated before)
     */
    default void toDto(T entity, DTO dto) {
        BeanUtils.copyProperties(entity, dto);

        for (Field field : Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> Arrays.stream(field.getType().getInterfaces()).collect(Collectors.toList())
                        .contains(ToDtoMapper.class)).collect(Collectors.toList())) {

            try {
                PropertyDescriptor pdGet = new PropertyDescriptor(field.getName(), entity.getClass());
                PropertyDescriptor pdSet = new PropertyDescriptor(field.getName(), dto.getClass());
                Object newInstance = pdSet.getPropertyType().newInstance();
                Object obj = pdGet.getReadMethod().invoke(entity);
                ((ToDtoMapper<T, DTO>) obj).toDto((T) obj, (DTO) newInstance);
                pdSet.getWriteMethod().invoke(dto, newInstance);
            } catch (IntrospectionException
                    | IllegalAccessException
                    | IllegalArgumentException
                    | InvocationTargetException
                    | InstantiationException e) {
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
                // calculate source and target type
                Type typeSource = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                Type typeTarget = ((ParameterizedType) fieldDto.getGenericType()).getActualTypeArguments()[0];

                if (Arrays.stream(((Class) typeSource).getInterfaces()).collect(Collectors.toList()).contains(ToDtoMapper.class)
                        && List.class.equals(fieldDto.getType())
                        && List.class.equals(field.getType())) {
                    logger.debug("List entity");
                    List<T> listOrig = (List<T>) pdGet.getReadMethod().invoke(entity);
                    List<DTO> listTarget = new ArrayList<>();
                    for (T element : listOrig) {
                        DTO newInstance = (DTO) ((Class) typeTarget).newInstance();
                        ((ToDtoMapper<T, DTO>) element).toDto(element, newInstance);
                        listTarget.add(newInstance);
                    }
                    pdSet.getWriteMethod().invoke(dto, listTarget);
                } else if (Arrays.stream(((Class) typeSource).getInterfaces()).collect(Collectors.toList()).contains(ToDtoMapper.class)
                        && Set.class.equals(fieldDto.getType())
                        && Set.class.equals(field.getType())) {
                    logger.debug("Set entity");
                    Set<T> setOrig = (Set<T>) pdGet.getReadMethod().invoke(entity);
                    Set<DTO> setTarget = new HashSet<>();
                    for (T element : setOrig) {
                        DTO newInstance = (DTO) ((Class) typeTarget).newInstance();
                        ((ToDtoMapper<T, DTO>) element).toDto(element, newInstance);
                        setTarget.add(newInstance);
                    }
                    pdSet.getWriteMethod().invoke(dto, setTarget);
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

        for (Field field : Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> field.getType().equals(Map.class))
                        .collect(Collectors.toList())) {

            try {
                logger.debug("Map field " + field.getName());
                Type keyTypeEntity = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
                Type valueTypeEntity = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[1];

                boolean srcMappedKeyType = Arrays.stream(((Class) keyTypeEntity).getInterfaces()).collect(Collectors.toList()).contains(ToDtoMapper.class);
                boolean srcMappedValueType = Arrays.stream(((Class) valueTypeEntity).getInterfaces()).collect(Collectors.toList()).contains(ToDtoMapper.class);

                if (!srcMappedKeyType && !srcMappedValueType) {
                    continue;
                }
                Field fieldDto = dto.getClass().getDeclaredField(field.getName());
                Type keyTypeDto = ((ParameterizedType)fieldDto.getGenericType()).getActualTypeArguments()[0];
                Type valueTypeDto = ((ParameterizedType)fieldDto.getGenericType()).getActualTypeArguments()[1];

                PropertyDescriptor pdGet = new PropertyDescriptor(field.getName(), entity.getClass());
                PropertyDescriptor pdSet = new PropertyDescriptor(field.getName(), dto.getClass());

                Map srcMap = (Map)pdGet.getReadMethod().invoke(entity);
                Map targetMap = new HashMap<>();
                Object key = null;
                Object value = null;
                for (Object entityKey : srcMap.keySet()) {
                    if (srcMappedKeyType) {
                        DTO keyDto = (DTO)((Class)keyTypeDto).newInstance();
                        ((ToDtoMapper<T,DTO>)entityKey).toDto((T)entityKey, keyDto);
                        key = keyDto;
                    } else {
                        key = entityKey;
                    }
                    Object entityValue = srcMap.get(entityKey);
                    if (srcMappedValueType) {
                        DTO valueDto = (DTO)((Class)valueTypeDto).newInstance();
                        ((ToDtoMapper<T,DTO>)entityValue).toDto((T)entityValue, valueDto);
                        value = valueDto;
                    } else {
                        value = entityValue;
                    }
                    targetMap.put(key, value);
                }
                pdSet.getWriteMethod().invoke(dto, targetMap);

            } catch (NoSuchFieldException | IntrospectionException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }

        }
    }

}
