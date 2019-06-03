package converter;

import dto.ClassWithAddressDTO;
import entity.Address;
import entity.ClassWithAddress;
import org.springframework.util.StringUtils;
import org.thunderroad.entitydtomapping.converters.MappingConverter;

public class ClassWithAddressConverter implements MappingConverter<ClassWithAddress, ClassWithAddressDTO> {

    @Override
    public ClassWithAddressDTO convertToDto(ClassWithAddress entity) {
        ClassWithAddressDTO dto = new ClassWithAddressDTO();
        dto.setStreetName(entity.getAddress().getStreet());
        StringBuilder housenr = new StringBuilder().append(entity.getAddress().getHousenumber());
        if (!StringUtils.isEmpty(entity.getAddress().getBoxnumber())) {
            housenr.append("/").append(entity.getAddress().getBoxnumber());
        }
        dto.setHouseNumber(housenr.toString());
        dto.setZipCode(entity.getAddress().getZipCode());
        dto.setCity(entity.getAddress().getCity());
        dto.setCountry(entity.getAddress().getCountry());
        return dto;
    }

    @Override
    public ClassWithAddress convertToEntity(ClassWithAddressDTO dto) {
        ClassWithAddress entity = new ClassWithAddress();
        Address address = new Address();
        entity.setAddress(address);
        address.setStreet(dto.getStreetName());
        String[] parts = dto.getHouseNumber().split( "/");
        if (parts[0].trim().matches("[0-9]+")) {
            address.setHousenumber(Integer.parseInt(parts[0].trim()));
            address.setBoxnumber(dto.getHouseNumber().substring(dto.getHouseNumber().indexOf("/")+1));
        } else {
            address.setBoxnumber(dto.getHouseNumber());
        }
        address.setZipCode(dto.getZipCode());
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());

        return entity;
    }

}
