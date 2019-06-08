package org.thunderroad.entitydtomapping;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.thunderroad.entitydtomapping.annotations.EntityDtoConverter;
import org.thunderroad.entitydtomapping.annotations.IgnoreMapping;
import org.thunderroad.entitydtomapping.annotations.Mapping;
import org.thunderroad.entitydtomapping.converters.MappingConverter;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Interface to add conversion method from a entity bean of type T to a DTO bean of type DTO.
 * @param <T> the class of the entity bean
 * @param <DTO> the class of the DTO bean
 *
 * @author Van Lommel Bart
 */
public interface ToDtoMapper<T, DTO> {

    Logger logger();

    /**
     * Conversion method to convert the entity bean into the DTO bean that represents it.
     * Members that implement the ToDtoMapper interface will also be converted.
     * Members that are Collections of type java.util.List or java.util.Set will be converted
     * in a java.uti.List or java.util.Set of the corresponding DTO bean types.
     * Members that are of type Map will be converted in a Map where the key and/or value will be
     * converted if they are from a type that implements the ToDtoMapper interface.
     *
     * @deprecated As of entity-dto-mapper 1.2,
     *      *  *             {@link #toDto(Object)} ()} instead.
     * @param entity the entity bean that needs to be converted. (Is at the same time the calling object)
     * @param dto the DTO bean that the entity will be converted into. (needs to be instantiated before)
     * @return dto the DTO bean that is the conversion of the calling entity bean
     */
    @Deprecated
    default DTO toDto(T entity, DTO dto) {
        return this.toDto(dto);
    }

    /**
     * Conversion method to convert the entity bean into the DTO bean that represents it.
     * Members that implement the ToDtoMapper interface will also be converted.
     * Members that are Collections of type java.util.List or java.util.Set will be converted
     * in a java.uti.List or java.util.Set of the corresponding DTO bean types.
     * Members that are of type Map will be converted in a Map where the key and/or value will be
     * converted if they are from a type that implements the ToDtoMapper interface.
     *
     * @param dto the DTO bean that the entity will be converted into. (needs to be instantiated before)
     * @return dto the DTO bean that is the conversion of the calling entity bean
     */
    default DTO toDto(DTO dto) {
        BeanUtils.copyProperties(this, dto);

        if (this.getClass().isAnnotationPresent(EntityDtoConverter.class)) {
            Class<?> converterClass = this.getClass().getAnnotation(EntityDtoConverter.class).converter();
            if (Arrays.stream(converterClass.getInterfaces()).collect(Collectors.toList()).contains(MappingConverter.class)) {

                try {
                    MappingConverter<T,DTO> converter = (MappingConverter<T,DTO>)converterClass.newInstance();
                    return converter.convertToDto((T)this);
                } catch (InstantiationException  | IllegalAccessException e) {
                    logger().debug(e.getMessage(), e);
                }
            }
        }

        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(IgnoreMapping.class)) {
                continue;
            }
            String mapping = field.isAnnotationPresent(Mapping.class) ? field.getAnnotation(Mapping.class).value() : field.getName();

            try {
                PropertyDescriptor pdGet = new PropertyDescriptor(field.getName(), this.getClass());
                PropertyDescriptor pdSet = new PropertyDescriptor(mapping, dto.getClass());
                if (Arrays.stream(field.getType().getInterfaces()).collect(Collectors.toList())
                        .contains(ToDtoMapper.class)) {
                    DTO newInstance = (DTO) pdSet.getPropertyType().newInstance();
                    T obj = (T) pdGet.getReadMethod().invoke((T) this);
                    pdSet.getWriteMethod().invoke(dto, ((ToDtoMapper<T, DTO>) obj).toDto(newInstance));
                    continue;
                } else if (field.isAnnotationPresent(Mapping.class)) {
                    pdSet.getWriteMethod().invoke(dto, pdGet.getReadMethod().invoke((T) this));
                }

                if (Arrays.stream(field.getType().getInterfaces()).collect(Collectors.toList()).contains(Collection.class)) {

                    Field fieldDto = dto.getClass().getDeclaredField(mapping);
                    pdGet = new PropertyDescriptor(field.getName(), this.getClass());
                    pdSet = new PropertyDescriptor(fieldDto.getName(), dto.getClass());
                    // calculate source and target type
                    Type typeSource = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                    Type typeTarget = ((ParameterizedType) fieldDto.getGenericType()).getActualTypeArguments()[0];

                    Collection<T> originalCollection = (Collection<T>) pdGet.getReadMethod().invoke((T) this);
                    Collection<DTO> targetCollection = null;
                    if (Arrays.stream(((Class) typeSource).getInterfaces()).collect(Collectors.toList()).contains(ToDtoMapper.class)
                            && List.class.equals(fieldDto.getType())
                            && List.class.equals(field.getType())) {
                        logger().debug("List entity");
                        targetCollection = new ArrayList<>();
                    } else if (Arrays.stream(((Class) typeSource).getInterfaces()).collect(Collectors.toList()).contains(ToDtoMapper.class)
                            && Set.class.equals(fieldDto.getType())
                            && Set.class.equals(field.getType())) {
                        logger().debug("Set entity");
                        targetCollection = new HashSet<>();
                    } else {
                        throw new UnsupportedOperationException(this.getClass().getTypeName() + " holds a member that is a java.util.Collection other than java.util.List or java.util.Set.");
                    }
                    Assert.notNull(targetCollection, "Something went wrong while creating target collection. Only java.util.List and java.util.Set are supported!");
                    for (T element : originalCollection) {
                        DTO newInstance = (DTO) ((Class) typeTarget).newInstance();
                        targetCollection.add(((ToDtoMapper<T, DTO>) element).toDto(newInstance));
                    }
                    pdSet.getWriteMethod().invoke(dto, targetCollection);

                    continue;
                }

                if (field.getType().equals(Map.class)) {

                    if (logger().isDebugEnabled()) logger().debug(String.format("Map field %s", field.getName()));
                    Type keyTypeEntity = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                    Type valueTypeEntity = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[1];

                    boolean srcMappedKeyType = Arrays.stream(((Class) keyTypeEntity).getInterfaces()).collect(Collectors.toList()).contains(ToDtoMapper.class);
                    boolean srcMappedValueType = Arrays.stream(((Class) valueTypeEntity).getInterfaces()).collect(Collectors.toList()).contains(ToDtoMapper.class);

                    if (!srcMappedKeyType && !srcMappedValueType) {
                        continue;
                    }
                    Field fieldDto = dto.getClass().getDeclaredField(mapping);
                    Type keyTypeDto = ((ParameterizedType) fieldDto.getGenericType()).getActualTypeArguments()[0];
                    Type valueTypeDto = ((ParameterizedType) fieldDto.getGenericType()).getActualTypeArguments()[1];

                    /*PropertyDescriptor*/
                    pdGet = new PropertyDescriptor(field.getName(), this.getClass());
                    /*PropertyDescriptor*/
                    pdSet = new PropertyDescriptor(mapping, dto.getClass());

                    Map srcMap = (Map) pdGet.getReadMethod().invoke((T) this);
                    Map targetMap = new HashMap<>();
                    Object key = null;
                    Object value = null;
                    for (Object entityKey : srcMap.keySet()) {
                        if (srcMappedKeyType) {
                            DTO keyDto = (DTO) ((Class) keyTypeDto).newInstance();
                            key = ((ToDtoMapper<T, DTO>) entityKey).toDto(keyDto);
                        } else {
                            key = entityKey;
                        }
                        Object entityValue = srcMap.get(entityKey);
                        if (srcMappedValueType) {
                            DTO valueDto = (DTO) ((Class) valueTypeDto).newInstance();
                            value = ((ToDtoMapper<T, DTO>) entityValue).toDto(valueDto);
                        } else {
                            value = entityValue;
                        }
                        targetMap.put(key, value);
                    }
                    pdSet.getWriteMethod().invoke(dto, targetMap);

                    continue;
                }

                if (((Class) field.getType()).isArray()) {
                    String elementTypename = field.getType().getTypeName().substring(0, field.getType().getTypeName().length() - 2);
                    Class<?> elementTypeSrc = Class.forName(elementTypename);

                    if (Arrays.stream(elementTypeSrc.getInterfaces()).collect(Collectors.toList()).contains(ToDtoMapper.class)) {
                        Field fieldDto = dto.getClass().getDeclaredField(mapping);
                        String targetElementTypename = fieldDto.getType().getTypeName().substring(0, fieldDto.getType().getTypeName().length() - 2);
                        Class<?> elementTypeTarget = Class.forName(targetElementTypename);

                        /*PropertyDescriptor*/
                        pdGet = new PropertyDescriptor(field.getName(), this.getClass());
                        /*PropertyDescriptor*/
                        pdSet = new PropertyDescriptor(fieldDto.getName(), dto.getClass());

                        Object srcObject = field.getType().cast(pdGet.getReadMethod().invoke((T) this));
                        T[] srcArray = (T[]) srcObject;
                        int length = srcArray.length;
                        DTO[] targetArray = (DTO[]) Array.newInstance(elementTypeTarget, length);
                        for (int index = 0; index < length; index++) {
                            DTO newinstance = (DTO) elementTypeTarget.newInstance();
                            targetArray[index] = ((ToDtoMapper<T, DTO>) srcArray[index]).toDto(newinstance);
                        }
                        pdSet.getWriteMethod().invoke(dto, (Object) targetArray);

                    }
                }
            } catch (ClassNotFoundException
                    | NoSuchFieldException
                    | IntrospectionException
                    | IllegalAccessException
                    | InvocationTargetException
                    | InstantiationException e) {
                logger().debug(e.getMessage(), e);
            }
        }

        return dto;
    }

}
