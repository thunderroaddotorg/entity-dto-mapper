package entity;

import dto.ClassWithPersonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thunderroad.entitydtomapping.ToDtoMapper;

import java.util.Objects;

public class ClassWithPerson implements ToDtoMapper<ClassWithPerson, ClassWithPersonDTO> {

    private long id;
    private String name;
    private ClassWithAddress address;

    public ClassWithPerson() {
    }

    public ClassWithPerson(long id, String name, ClassWithAddress address) {
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

    public ClassWithAddress getAddress() {
        return address;
    }

    public void setAddress(ClassWithAddress address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassWithPerson that = (ClassWithPerson) o;
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
        return "ClassWithPerson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }

    @Override
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
