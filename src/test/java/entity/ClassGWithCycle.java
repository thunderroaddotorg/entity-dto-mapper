package entity;

import dto.ClassGWithCycleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thunderroad.entitydtomapping.ToDtoMapper;
import org.thunderroad.entitydtomapping.annotations.IgnoreMapping;

import java.util.List;
import java.util.Objects;

public class ClassGWithCycle extends AbstractClassD implements ToDtoMapper<ClassGWithCycle, ClassGWithCycleDTO> {

    private String name;
    private List<ClassHWithCycle> membersH;
    @IgnoreMapping
    private ClassWithCycle parent;

    public ClassGWithCycle() {
    }

    public ClassGWithCycle(long id, String name, List<ClassHWithCycle> membersH) {
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

    public List<ClassHWithCycle> getMembersH() {
        return membersH;
    }

    public void setMembersH(List<ClassHWithCycle> membersH) {
        this.membersH = membersH;
    }

    public ClassWithCycle getParent() {
        return parent;
    }

    public void setParent(ClassWithCycle parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "ClassGWithCycle{" +
                "name='" + name + '\'' +
                ", membersH=" + membersH +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClassGWithCycle that = (ClassGWithCycle) o;
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
