package dto;

import entity.ClassWithCycle;
import org.thunderroad.entitydtomapping.FromDtoMapper;
import org.thunderroad.entitydtomapping.annotaions.IgnoreMapping;

import java.util.List;
import java.util.Objects;

public class ClassWithCycleDTO implements FromDtoMapper<ClassWithCycle,ClassWithCycleDTO> {

    private List<ClassGWithCycleDTO> members;
    @IgnoreMapping
    private String ignoredMember;

    public ClassWithCycleDTO() {
    }

    public ClassWithCycleDTO(List<ClassGWithCycleDTO> members) {
        this.members = members;
    }

    public List<ClassGWithCycleDTO> getMembers() {
        return members;
    }

    public void setMembers(List<ClassGWithCycleDTO> members) {
        this.members = members;
    }

    public String getIgnoredMember() {
        return ignoredMember;
    }

    public void setIgnoredMember(String ignoredMember) {
        this.ignoredMember = ignoredMember;
    }

    @Override
    public String toString() {
        return "ClassWithCycleDTO{" +
                "members=" + members +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassWithCycleDTO that = (ClassWithCycleDTO) o;
        return Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(members);
    }
}
