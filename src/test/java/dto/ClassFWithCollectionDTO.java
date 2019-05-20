package dto;

import be.bvl.entitydtomapping.FromDtoMapper;
import entity.ClassFWithCollection;

import java.util.List;
import java.util.Set;

public class ClassFWithCollectionDTO extends AbstractClassDDTO implements FromDtoMapper<ClassFWithCollection, ClassFWithCollectionDTO> {

    private List<ClassADTO> membersList;
    private Set<ClassADTO> membersSet;

    public ClassFWithCollectionDTO() {
    }

    public ClassFWithCollectionDTO(long id, List<ClassADTO> membersList, Set<ClassADTO> membersSet) {
        super(id);
        this.membersList = membersList;
        this.membersSet = membersSet;
    }

    public List<ClassADTO> getMembersList() {
        return membersList;
    }

    public void setMembersList(List<ClassADTO> membersList) {
        this.membersList = membersList;
    }

    public Set<ClassADTO> getMembersSet() {
        return membersSet;
    }

    public void setMembersSet(Set<ClassADTO> membersSet) {
        this.membersSet = membersSet;
    }

    @Override
    public String toString() {
        return "ClassFWithCollectionDTO{" +
                "membersList=" + membersList +
                ", membersSet=" + membersSet +
                ", id=" + getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassFWithCollectionDTO)) return false;
        if (!super.equals(o)) return false;

        ClassFWithCollectionDTO that = (ClassFWithCollectionDTO) o;

        return getMembersList().equals(that.getMembersList()) && getMembersSet().equals(that.getMembersSet());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getMembersList().hashCode() + getMembersSet().hashCode();
        return result;
    }
}
