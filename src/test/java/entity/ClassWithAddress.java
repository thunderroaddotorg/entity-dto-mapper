package entity;

import converter.ClassWithAddressConverter;
import dto.ClassWithAddressDTO;
import org.thunderroad.entitydtomapping.ToDtoMapper;
import org.thunderroad.entitydtomapping.annotations.EntityDtoConverter;

import java.util.Objects;

@EntityDtoConverter(converter = ClassWithAddressConverter.class)
public class ClassWithAddress implements ToDtoMapper<ClassWithAddress, ClassWithAddressDTO> {

    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassWithAddress that = (ClassWithAddress) o;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return "ClassWithAddress{" +
                "address=" + address +
                '}';
    }
}
