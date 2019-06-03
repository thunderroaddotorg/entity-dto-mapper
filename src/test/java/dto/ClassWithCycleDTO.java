package dto;

import entity.ClassWithCycle;
import org.thunderroad.entitydtomapping.FromDtoMapper;
import org.thunderroad.entitydtomapping.annotations.IgnoreMapping;
import org.thunderroad.entitydtomapping.annotations.Mapping;

import java.util.List;
import java.util.Objects;

public class ClassWithCycleDTO implements FromDtoMapper<ClassWithCycle,ClassWithCycleDTO> {

    @Mapping("members")
    private List<ClassGWithCycleDTO> membersG;
    @IgnoreMapping
    private String ignoredMember;

    public ClassWithCycleDTO() {
    }

    public ClassWithCycleDTO(List<ClassGWithCycleDTO> membersG) {
        this.membersG = membersG;
    }

    public List<ClassGWithCycleDTO> getMembersG() {
        return membersG;
    }

    public void setMembersG(List<ClassGWithCycleDTO> membersG) {
        this.membersG = membersG;
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
                "membersG=" + membersG +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassWithCycleDTO that = (ClassWithCycleDTO) o;
        return Objects.equals(membersG, that.membersG);
    }

    @Override
    public int hashCode() {
        return Objects.hash(membersG);
    }
}
