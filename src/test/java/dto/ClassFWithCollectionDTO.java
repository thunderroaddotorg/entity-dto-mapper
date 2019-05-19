package dto;

import be.bvl.entitydtomapping.FromDtoMapper;
import entity.ClassFWithCollection;

import java.util.List;

public class ClassFWithCollectionDTO extends AbstractClassDDTO implements FromDtoMapper<ClassFWithCollection, ClassFWithCollectionDTO> {

    private List<ClassADTO> members;

    public ClassFWithCollectionDTO() {
    }

    public ClassFWithCollectionDTO(long id, List<ClassADTO> members) {
        super(id);
        this.members = members;
    }

    public List<ClassADTO> getMembers() {
        return members;
    }

    public void setMembers(List<ClassADTO> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "ClassFWithCollectionDTO{" +
                "members=" + members +
                ", id=" + getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassFWithCollectionDTO)) return false;
        if (!super.equals(o)) return false;

        ClassFWithCollectionDTO that = (ClassFWithCollectionDTO) o;

        return getMembers().equals(that.getMembers());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getMembers().hashCode();
        return result;
    }
}
