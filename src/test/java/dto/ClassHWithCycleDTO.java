package dto;

import entity.ClassHWithCycle;
import org.thunderroad.entitydtomapping.FromDtoMapper;

import java.util.Objects;

public class ClassHWithCycleDTO extends AbstractClassDDTO implements FromDtoMapper<ClassHWithCycle,ClassHWithCycleDTO> {

    private String name;

    public ClassHWithCycleDTO() {
    }

    public ClassHWithCycleDTO(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassHWithCycle{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClassHWithCycleDTO that = (ClassHWithCycleDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
