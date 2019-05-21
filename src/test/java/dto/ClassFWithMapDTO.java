package dto;

import entity.ClassFWithMap;
import org.thunderroad.entitydtomapping.FromDtoMapper;

import java.util.Date;
import java.util.Map;

public class ClassFWithMapDTO extends AbstractClassDDTO implements FromDtoMapper<ClassFWithMap, ClassFWithMapDTO> {

    private Map<String,ClassADTO> membersMap1;
    private Map<ClassADTO, ClassBDTO> membersMap2;
    private Map<String, Long> membersMap3;
    private Map<ClassADTO, Date> membersMap4;

    public ClassFWithMapDTO() {
    }

    public ClassFWithMapDTO(long id,
                            Map<String,ClassADTO> membersMap1,
                            Map<ClassADTO,ClassBDTO> membersMap2,
                            Map<String, Long> membersMap3,
                            Map<ClassADTO, Date> membersMap4) {
        super(id);
        this.membersMap1 = membersMap1;
        this.membersMap2 = membersMap2;
        this.membersMap3 = membersMap3;
        this.membersMap4 = membersMap4;
    }

    public Map<String, ClassADTO> getMembersMap1() {
        return membersMap1;
    }

    public void setMembersMap1(Map<String, ClassADTO> membersMap1) {
        this.membersMap1 = membersMap1;
    }

    public Map<ClassADTO, ClassBDTO> getMembersMap2() {
        return membersMap2;
    }

    public void setMembersMap2(Map<ClassADTO, ClassBDTO> membersMap2) {
        this.membersMap2 = membersMap2;
    }

    public Map<String, Long> getMembersMap3() {
        return membersMap3;
    }

    public void setMembersMap3(Map<String, Long> membersMap3) {
        this.membersMap3 = membersMap3;
    }

    public Map<ClassADTO, Date> getMembersMap4() {
        return membersMap4;
    }

    public void setMembersMap4(Map<ClassADTO, Date> membersMap4) {
        this.membersMap4 = membersMap4;
    }

    @Override
    public String toString() {
        return "ClassFWithCollectionDTO{" +
                "membersMap1=" + membersMap1 +
                ", membersMap2=" + membersMap2 +
                ", membersMap3=" + membersMap3 +
                ", membersMap4=" + membersMap4 +
                ", id=" + getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassFWithMapDTO)) return false;
        if (!super.equals(o)) return false;

        ClassFWithMapDTO that = (ClassFWithMapDTO) o;

        return getMembersMap1().equals(that.getMembersMap1())
                && getMembersMap2().equals(that.getMembersMap2())
                && getMembersMap3().equals(that.getMembersMap3())
                && getMembersMap4().equals(that.getMembersMap4());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result
                + getMembersMap1().hashCode()
                + getMembersMap2().hashCode()
                + getMembersMap3().hashCode()
                + getMembersMap4().hashCode();
        return result;
    }
}
