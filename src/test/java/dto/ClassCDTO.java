package dto;

import be.bvl.entitydtomapping.FromDtoMapper;
import entity.ClassC;

import java.util.Objects;

public class ClassCDTO implements FromDtoMapper<ClassC, ClassCDTO> {

    private long id;
    private String name;
    private ClassBDTO classB;

    public ClassCDTO() {
    }

    public ClassCDTO(long id, String name, ClassBDTO classB) {
        this.id = id;
        this.name = name;
        this.classB = classB;
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

    public ClassBDTO getClassB() {
        return classB;
    }

    public void setClassB(ClassBDTO classB) {
        this.classB = classB;
    }

    @Override
    public String toString() {
        return "ClassCDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classB=" + classB +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassCDTO classCDTO = (ClassCDTO) o;
        return id == classCDTO.id &&
                name.equals(classCDTO.name) &&
                classB.equals(classCDTO.classB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, classB);
    }
}
