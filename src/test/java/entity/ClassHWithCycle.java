package entity;

import dto.ClassHWithCycleDTO;
import org.thunderroad.entitydtomapping.ToDtoMapper;

import java.util.Objects;

public class ClassHWithCycle extends AbstractClassD implements ToDtoMapper<ClassHWithCycle, ClassHWithCycleDTO> {

    private String name;
    private ClassGWithCycle memberG;

    public ClassHWithCycle() {
    }

    public ClassHWithCycle(long id, String name, ClassGWithCycle memberG) {
        super(id);
        this.name = name;
        this.memberG = memberG;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClassGWithCycle getMemberG() {
        return memberG;
    }

    public void setMemberG(ClassGWithCycle memberG) {
        this.memberG = memberG;
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
        ClassHWithCycle that = (ClassHWithCycle) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(memberG, that.memberG);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, memberG);
    }
}
