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

                if (Arrays.stream(((Class)typeSource).getInterfaces()).collect(Collectors.toList()).contains(FromDtoMapper.class)
                        && List.class.equals(fieldDto.getType())
                        && List.class.equals(field.getType())) {
                    logger.debug("List entity");
                    List<DTO> listOrig = (List<DTO>)pdGet.getReadMethod().invoke(dto);
                    List<T> listTarget = new ArrayList<>();
                    for (DTO element : listOrig) {
                        T newInstance = (T) ((Class)typeTarget).newInstance();
                        ((FromDtoMapper<T, DTO>)element).fromDto(element, newInstance);
                        listTarget.add(newInstance);
                    }
                    pdSet.getWriteMethod().invoke(entity, listTarget);
                } else if (Arrays.stream(((Class)typeSource).getInterfaces()).collect(Collectors.toList()).contains(FromDtoMapper.class)
                        && Set.class.equals(fieldDto.getType())
                        && Set.class.equals(fieldDto.getType())) {
                    logger.debug("Set entity");
                    Set<DTO> setOrig = (Set<DTO>)pdGet.getReadMethod().invoke(dto);
                    Set<T> setTarget = new HashSet<>();
                    for (DTO element : setOrig) {
                        T newInstance = (T) ((Class)typeTarget).newInstance();
                        ((FromDtoMapper<T, DTO>)element).fromDto(element, newInstance);
                        setTarget.add(newInstance);
                    }
                    pdSet.getWriteMethod().invoke(entity, setTarget);
//                } else if (Map.class.equals(fieldDto.getType()) && Map.class.equals(field.getType())) {
//                    logger.debug("Map entity");
                } else throw new UnsupportedOperationException();

            } catch (IntrospectionException
                    | IllegalAccessException
                    | IllegalArgumentException
                    | InvocationTargetException
                    | InstantiationException
                    | NoSuchFieldException e) {
                logger.debug(e.getMessage(), e);
            }
        }

    }

}
