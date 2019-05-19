package entity;

import be.bvl.entitydtomapping.ToDtoMapper;
import dto.ClassFWithCollectionDTO;

import java.util.List;

public class ClassFWithCollection extends AbstractClassD implements ToDtoMapper<ClassFWithCollection, ClassFWithCollectionDTO> {

    private List<ClassA> members;

    public ClassFWithCollection() {
    }

    public ClassFWithCollection(long id, List<ClassA> members) {
        super(id);
        this.members = members;
    }

    public List<ClassA> getMembers() {
        return members;
    }

    public void setMembers(List<ClassA> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "ClassFWithCollection{" +
                "members=" + members +
                ", id=" + getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassFWithCollection)) return false;
        if (!super.equals(o)) return false;

        ClassFWithCollection that = (ClassFWithCollection) o;

        return getMembers().equals(that.getMembers());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getMembers().hashCode();
        return result;
    }
}
