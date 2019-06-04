package org.thunderroad.entitydtomapping.converters;

/**
 * Interface for a converter that can perform the conversion of an entity bean of type T to a DTO bean of type DTO, and reverse.
 *
 * @param <T> the entity bean type
 * @param <DTO> the DTO bean type
 */
public interface MappingConverter<T,DTO> {

    DTO convertToDto(T entity);

    T convertToEntity(DTO dto);
}
