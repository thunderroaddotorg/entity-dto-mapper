package dto;

import entity.ClassWithPerson;
import org.thunderroad.entitydtomapping.FromDtoMapper;

import java.util.Objects;

public class ClassWithPersonDTO implements FromDtoMapper<ClassWithPerson,ClassWithPersonDTO> {

    private long id;
    private String name;
    private ClassWithAddressDTO address;

    public ClassWithPersonDTO() {
    }

    public ClassWithPersonDTO(long id, String name, ClassWithAddressDTO address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClassWithAddressDTO getAddress() {
        return address;
    }

    public void setAddress(ClassWithAddressDTO address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassWithPersonDTO that = (ClassWithPersonDTO) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }

    @Override
    public String toString() {
        return "ClassWithPersonDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
