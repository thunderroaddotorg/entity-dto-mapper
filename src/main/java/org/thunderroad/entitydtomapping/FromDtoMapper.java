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
 * Interface to add conversion method from a DTO bean of type DTO to a entity bean of type T.
 * @param <T> the class of the entity bean
 * @param <DTO> the class of the DTO bean
 *
 * @author Van Lommel Bart
 */
public interface FromDtoMapper<T, DTO> {

    Logger logger();

    /**
     * Conversion method to convert the DTO bean into the entity bean that represents it.
     * Members that implement the FromDtoMapper interface will also be converted.
     * Members that are Collections of type java.util.List or java.util.Set will be converted
     * in a java.uti.List or java.util.Set of the corresponding entity bean types.
     * Members that are of type Map will be converted in a Map where the key and/or value will be
     * converted if they are from a type that implements the FromDtoMapper interface.
     *
     * @deprecated As of entity-dto-mapper 1.2,
     *  *             {@link #fromDto(Object)} ()} instead.
     * @param dto the DTO bean that needs to be converted. (Is at the same time the calling object)
     * @param entity the entity bean that the entity will be converted into. (needs to be instantiated before)
     * @return entity of type T that is the conversion of the calling DTO object
     */
    @Deprecated
    default T fromDto(DTO dto, T entity) {
        return this.fromDto(entity);
    }

    /**
     * Conversion method to convert the DTO bean into the entity bean that represents it.
     * Members that implement the FromDtoMapper interface will also be converted.
     * Members that are Collections of type java.util.List or java.util.Set will be converted
     * in a java.uti.List or java.util.Set of the corresponding entity bean types.
     * Members that are of type Map will be converted in a Map where the key and/or value will be
     * converted if they are from a type that implements the FromDtoMapper interface.
     *
     * @param entity the entity bean that the entity will be converted into. (needs to be instantiated before)
     * @return entity of type T that is the conversion of the calling DTO object
     */
    default T fromDto(T entity) {
        BeanUtils.copyProperties(this, entity);

        if (this.getClass().isAnnotationPresent(EntityDtoConverter.class)) {
            Class<?> converterClass = this.getClass().getAnnotation(EntityDtoConverter.class).converter();
            if (Arrays.stream(converterClass.getInterfaces()).collect(Collectors.toList()).contains(MappingConverter.class)) {

                try {
                    MappingConverter<T,DTO> converter = (MappingConverter<T,DTO>)converterClass.newInstance();
                    return converter.convertToEntity((DTO)this);
                } catch (InstantiationException  | IllegalAccessException e) {
                    logger().debug(e.getMessage(), e);
                }
            }
        }

        for (Field fieldDto : this.getClass().getDeclaredFields()) {
            if (fieldDto.isAnnotationPresent(IgnoreMapping.class)) {
                continue;
            }
            String mapping = fieldDto.isAnnotationPresent(Mapping.class) ? fieldDto.getAnnotation(Mapping.class).value() : fieldDto.getName();

            try {
                PropertyDescriptor pdGet = new PropertyDescriptor(fieldDto.getName(), this.getClass());
                PropertyDescriptor pdSet = new PropertyDescriptor(mapping, entity.getClass());
                if (Arrays.stream(fieldDto.getType().getInterfaces()).collect(Collectors.toList())
                        .contains(FromDtoMapper.class)) {
                    Object newInstance = pdSet.getPropertyType().newInstance();
                    Object obj = pdGet.getReadMethod().invoke((DTO)this);
                    pdSet.getWriteMethod().invoke(entity, ((FromDtoMapper<T, DTO>) obj).fromDto((T) newInstance));
                } else if (fieldDto.isAnnotationPresent(Mapping.class)) {
                    pdSet.getWriteMethod().invoke(entity, pdGet.getReadMethod().invoke((DTO)this));
                }

                if (Arrays.stream(fieldDto.getType().getInterfaces()).collect(Collectors.toList()).contains(Collection.class)) {
                    Field field = entity.getClass().getDeclaredField(mapping);
                    pdGet = new PropertyDescriptor(fieldDto.getName(), this.getClass());
                    pdSet = new PropertyDescriptor(field.getName(), entity.getClass());
                    // calculate source and target type
                    Type typeSource = ((ParameterizedType) fieldDto.getGenericType()).getActualTypeArguments()[0];
                    Type typeTarget = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

                    Collection<DTO> originalCollection = (Collection<DTO>) pdGet.getReadMethod().invoke((DTO)this);
                    Collection<T> targetCollection = null;
                    if (Arrays.stream(((Class) typeSource).getInterfaces()).collect(Collectors.toList()).contains(FromDtoMapper.class)
                            && List.class.equals(fieldDto.getType())
                            && List.class.equals(field.getType())) {
                        logger().debug("List entity");
                        targetCollection = new ArrayList<>();
                    } else if (Arrays.stream(((Class) typeSource).getInterfaces()).collect(Collectors.toList()).contains(FromDtoMapper.class)
                            && Set.class.equals(fieldDto.getType())
                            && Set.class.equals(field.getType())) {
                        logger().debug("Set entity");
                        targetCollection = new HashSet<>();
                    } else {
                        throw new UnsupportedOperationException(this.getClass().getTypeName() + " holds a member that is a java.util.Collection other than java.util.List or java.util.Set.");
                    }
                    Assert.notNull(targetCollection, "Something went wrong while creating target collection. Only java.util.List and java.util.Set are supported!");
                    for (DTO element : originalCollection) {
                        T newInstance = (T) ((Class) typeTarget).newInstance();
                        targetCollection.add(((FromDtoMapper<T, DTO>) element).fromDto(newInstance));
                    }
                    pdSet.getWriteMethod().invoke(entity, targetCollection);

                    continue;
                }

                if (fieldDto.getType().equals(Map.class)) {

                    if (logger().isDebugEnabled()) logger().debug(String.format("Map field %s", fieldDto.getName()));
                    Type keyTypeDto = ((ParameterizedType)fieldDto.getGenericType()).getActualTypeArguments()[0];
                    Type valueTypeDto = ((ParameterizedType)fieldDto.getGenericType()).getActualTypeArguments()[1];

                    boolean srcMappedKeyType = Arrays.stream(((Class) keyTypeDto).getInterfaces())
                            .collect(Collectors.toList()).contains(FromDtoMapper.class);
                    boolean srcMappedValueType = Arrays.stream(((Class) valueTypeDto).getInterfaces())
                            .collect(Collectors.toList()).contains(FromDtoMapper.class);

                    if (!srcMappedKeyType && !srcMappedValueType) {
                        continue;
                    }
                    Field field = entity.getClass().getDeclaredField(mapping);
                    Type keyTypeEntity = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
                    Type valueTypeEntity = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[1];

                    pdGet = new PropertyDescriptor(fieldDto.getName(), this.getClass());
                    pdSet = new PropertyDescriptor(field.getName(), entity.getClass());

                    Map srcMap = (Map)pdGet.getReadMethod().invoke((DTO)this);
                    Map targetMap = new HashMap<>();
                    Object key = null;
                    Object value = null;
                    for (Object dtoKey : srcMap.keySet()) {
                        if (srcMappedKeyType) {
                            T keyEntity = (T)((Class)keyTypeEntity).newInstance();
                            key = ((FromDtoMapper<T,DTO>)dtoKey).fromDto(keyEntity);
                        } else {
                            key = dtoKey;
                        }
                        Object dtoValue = srcMap.get(dtoKey);
                        if (srcMappedValueType) {
                            T valueEntity = (T)((Class)valueTypeEntity).newInstance();
                            value = ((FromDtoMapper<T,DTO>)dtoValue).fromDto(valueEntity);
                        } else {
                            value = dtoValue;
                        }
                        targetMap.put(key, value);
                    }
                    pdSet.getWriteMethod().invoke(entity, targetMap);

                    continue;
                }

                if (fieldDto.getType().isArray()) {
                    String elementTypename = fieldDto.getType().getTypeName().substring(0, fieldDto.getType().getTypeName().length() - 2);
                    Class<?> elementTypeSrc = Class.forName(elementTypename);

                    if (Arrays.stream(elementTypeSrc.getInterfaces()).collect(Collectors.toList()).contains(FromDtoMapper.class)) {
                        Field field = entity.getClass().getDeclaredField(mapping);
                        String targetElementTypename = field.getType().getTypeName().substring(0, field.getType().getTypeName().length() - 2);
                        Class<?> elementTypeTarget = Class.forName(targetElementTypename);

                        pdGet = new PropertyDescriptor(fieldDto.getName(), this.getClass());
                        pdSet = new PropertyDescriptor(field.getName(), entity.getClass());

                        Object srcObject = fieldDto.getType().cast(pdGet.getReadMethod().invoke((DTO) this));
                        DTO[] srcArray = (DTO[]) srcObject;
                        int length = srcArray.length;
                        T[] targetArray = (T[]) Array.newInstance(elementTypeTarget, length);
                        for (int index = 0; index < length; index++) {
                            T newinstance = (T) elementTypeTarget.newInstance();
                            targetArray[index] = ((FromDtoMapper<T, DTO>) srcArray[index]).fromDto(newinstance);
                        }
                        pdSet.getWriteMethod().invoke(entity, (Object) targetArray);

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

        return entity;
    }

}
