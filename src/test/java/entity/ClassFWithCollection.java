package entity;

import dto.ClassFWithCollectionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thunderroad.entitydtomapping.ToDtoMapper;

import java.util.List;
import java.util.Set;

public class ClassFWithCollection extends AbstractClassD implements ToDtoMapper<ClassFWithCollection, ClassFWithCollectionDTO> {

    private List<ClassA> membersList;
    private Set<ClassA> membersSet;

    public ClassFWithCollection() {
    }

    public ClassFWithCollection(long id, List<ClassA> membersList, Set<ClassA> membersSet) {
        super(id);
        this.membersList = membersList;
        this.membersSet = membersSet;
    }

    public List<ClassA> getMembersList() {
        return membersList;
    }

    public void setMembersList(List<ClassA> membersList) {
        this.membersList = membersList;
    }

    public Set<ClassA> getMembersSet() {
        return membersSet;
    }

    public void setMembersSet(Set<ClassA> membersSet) {
        this.membersSet = membersSet;
    }

    @Override
    public String toString() {
        return "ClassFWithCollection{" +
                "membersList=" + membersList +
                ", membersSet=" + membersSet +
                ", id=" + getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassFWithCollection)) return false;
        if (!super.equals(o)) return false;

        ClassFWithCollection that = (ClassFWithCollection) o;

        return getMembersList().equals(that.getMembersList()) && getMembersSet().equals(that.getMembersSet());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getMembersList().hashCode() + getMembersSet().hashCode();
        return result;
    }

    @Override
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
