package org.thunderroad.entitydtomapping.converters;

public interface MappingConverter<T,DTO> {

    DTO convertToDto(T entity);

    T convertToEntity(DTO dto);
}
