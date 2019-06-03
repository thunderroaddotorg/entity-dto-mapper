package org.thunderroad.entitydtomapping;

import dto.ClassWithAddressDTO;
import entity.Address;
import entity.ClassWithAddress;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

@RunWith(JUnit4ClassRunner.class)
public class EntityDtoConverterTest {

    ClassWithAddress entity;
    ClassWithAddressDTO dto;

    @Before
    public void before() {
        Address address = new Address();
        address.setStreet("straat");
        address.setHousenumber(1);
        address.setBoxnumber("A");
        address.setZipCode("3000");
        address.setCity("Leuven");
        address.setCountry("BE");
        entity = new ClassWithAddress();
        entity.setAddress(address);

        dto = new ClassWithAddressDTO();
        dto.setStreetName("straat");
        dto.setHouseNumber("1/A");
        dto.setZipCode("3000");
        dto.setCity("Leuven");
        dto.setCountry("BE");

    }


    @Test
    public void toDtoClassWithAddress() {

        Assert.assertEquals(dto, entity.toDto(new ClassWithAddressDTO()));
    }

    @Test
    public void toEntityClassWithAddress() {
        Assert.assertEquals(entity, dto.fromDto(new ClassWithAddress()));
    }
}
