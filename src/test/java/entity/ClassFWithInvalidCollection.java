package entity;

import dto.ClassFWithInvalidCollectionDTO;
import org.thunderroad.entitydtomapping.ToDtoMapper;

import java.util.Queue;

public class ClassFWithInvalidCollection extends AbstractClassD implements ToDtoMapper<ClassFWithInvalidCollection, ClassFWithInvalidCollectionDTO> {

    private Queue<ClassA> members;

    public ClassFWithInvalidCollection() {
    }

    public ClassFWithInvalidCollection(long id, Queue<ClassA> members) {
        super(id);
        this.members = members;
    }

    public Queue<ClassA> getMembers() {
        return members;
    }

    public void setMembers(Queue<ClassA> members) {
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
        if (!(o instanceof ClassFWithInvalidCollection)) return false;
        if (!super.equals(o)) return false;

        ClassFWithInvalidCollection that = (ClassFWithInvalidCollection) o;

        return getMembers().equals(that.getMembers());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getMembers().hashCode();
        return result;
    }
}
