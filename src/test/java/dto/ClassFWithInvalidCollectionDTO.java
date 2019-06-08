package dto;

import entity.ClassFWithInvalidCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thunderroad.entitydtomapping.FromDtoMapper;

import java.util.Queue;

public class ClassFWithInvalidCollectionDTO extends AbstractClassDDTO
        implements FromDtoMapper<ClassFWithInvalidCollection, ClassFWithInvalidCollectionDTO> {

    private Queue<ClassADTO> members;

    public ClassFWithInvalidCollectionDTO() {
    }

    public ClassFWithInvalidCollectionDTO(long id, Queue<ClassADTO> members) {
        super(id);
        this.members = members;
    }

    public Queue<ClassADTO> getMembers() {
        return members;
    }

    public void setMembers(Queue<ClassADTO> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "ClassFWithInvalidCollectionDTO{" +
                "members=" + members +
                ", id=" + getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassFWithInvalidCollectionDTO)) return false;
        if (!super.equals(o)) return false;

        ClassFWithInvalidCollectionDTO that = (ClassFWithInvalidCollectionDTO) o;

        return getMembers().equals(that.getMembers());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getMembers().hashCode();
        return result;
    }

    @Override
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
