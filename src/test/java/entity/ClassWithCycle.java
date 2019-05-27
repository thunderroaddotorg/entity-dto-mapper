package entity;

import dto.ClassWithCycleDTO;
import org.thunderroad.entitydtomapping.ToDtoMapper;

import java.util.List;
import java.util.Objects;

public class ClassWithCycle implements ToDtoMapper<ClassWithCycle, ClassWithCycleDTO> {

    private List<ClassGWithCycle> members;

    public ClassWithCycle() {
    }

    public ClassWithCycle(List<ClassGWithCycle> members) {
        this.members = members;
    }

    public List<ClassGWithCycle> getMembers() {
        return members;
    }

    public void setMembers(List<ClassGWithCycle> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "ClassWithCycle{" +
                "members=" + members +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassWithCycle that = (ClassWithCycle) o;
        return Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(members);
    }
}
