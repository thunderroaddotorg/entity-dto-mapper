package entity;

import dto.ClassCDTO;
import org.thunderroad.entitydtomapping.ToDtoMapper;

import java.util.Objects;

public class ClassC implements ToDtoMapper<ClassC, ClassCDTO> {

    private long id;
    private String name;
    private ClassB classB;

    public ClassC() {
    }

    public ClassC(long id, String name, ClassB classB) {
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

    public ClassB getClassB() {
        return classB;
    }

    public void setClassB(ClassB classB) {
        this.classB = classB;
    }

    @Override
    public String toString() {
        return "ClassC{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classB=" + classB +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassC classC = (ClassC) o;
        return id == classC.id &&
                name.equals(classC.name) &&
                classB.equals(classC.classB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, classB);
    }
}
