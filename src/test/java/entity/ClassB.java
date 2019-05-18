package entity;

import be.bvl.entitydtomapping.ToDtoMapper;
import dto.ClassBDTO;

import java.util.Objects;

public class ClassB implements ToDtoMapper<ClassB, ClassBDTO> {

    private long id;
    private ClassA classA;

    public ClassB() {
    }

    public ClassB(long id, ClassA classA) {
        this.id = id;
        this.classA = classA;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ClassA getClassA() {
        return classA;
    }

    public void setClassA(ClassA classA) {
        this.classA = classA;
    }

    @Override
    public String toString() {
        return "ClassB{" +
                "id=" + id +
                ", classA=" + classA +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassB classB = (ClassB) o;
        return id == classB.id &&
                classA.equals(classB.classA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classA);
    }
}
