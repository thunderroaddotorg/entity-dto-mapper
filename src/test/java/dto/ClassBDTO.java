package dto;

import entity.ClassB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thunderroad.entitydtomapping.FromDtoMapper;

import java.util.Objects;

public class ClassBDTO implements FromDtoMapper<ClassB, ClassBDTO> {

    private long id;
    private ClassADTO classA;

    public ClassBDTO() {
    }

    public ClassBDTO(long id, ClassADTO classA) {
        this.id = id;
        this.classA = classA;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ClassADTO getClassA() {
        return classA;
    }

    public void setClassA(ClassADTO classA) {
        this.classA = classA;
    }

    @Override
    public String toString() {
        return "ClassBDTO{" +
                "id=" + id +
                ", classA=" + classA +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassBDTO classBDTO = (ClassBDTO) o;
        return id == classBDTO.id &&
                classA.equals(classBDTO.classA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classA);
    }

    @Override
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
