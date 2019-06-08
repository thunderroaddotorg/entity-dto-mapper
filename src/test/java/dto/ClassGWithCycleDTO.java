package dto;

import entity.ClassGWithCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thunderroad.entitydtomapping.FromDtoMapper;

import java.util.List;
import java.util.Objects;

public class ClassGWithCycleDTO extends AbstractClassDDTO implements FromDtoMapper<ClassGWithCycle,ClassGWithCycleDTO> {

    private String name;
    private List<ClassHWithCycleDTO> membersH;

    public ClassGWithCycleDTO() {
    }

    public ClassGWithCycleDTO(long id, String name, List<ClassHWithCycleDTO> membersH) {
        super(id);
        this.name = name;
        this.membersH = membersH;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClassHWithCycleDTO> getMembersH() {
        return membersH;
    }

    public void setMembersH(List<ClassHWithCycleDTO> membersH) {
        this.membersH = membersH;
    }

    @Override
    public String toString() {
        return "ClassGWithCycleDTO{" +
                "name='" + name + '\'' +
                ", membersH=" + membersH +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClassGWithCycleDTO that = (ClassGWithCycleDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(membersH, that.membersH);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, membersH);
    }

    @Override
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
