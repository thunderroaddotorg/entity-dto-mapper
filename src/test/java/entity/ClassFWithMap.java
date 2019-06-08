package entity;

import dto.ClassFWithMapDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thunderroad.entitydtomapping.ToDtoMapper;

import java.util.Date;
import java.util.Map;

public class ClassFWithMap extends AbstractClassD implements ToDtoMapper<ClassFWithMap, ClassFWithMapDTO> {

    private Map<String, ClassA> membersMap1;
    private Map<ClassA, ClassB> membersMap2;
    private Map<String, Long> membersMap3;
    private Map<ClassA, Date> membersMap4;

    public ClassFWithMap() {
    }

    public ClassFWithMap(long id,
                         Map<String, ClassA> membersMap1,
                         Map<ClassA, ClassB> membersMap2,
                         Map<String, Long> membersMap3,
                         Map<ClassA, Date> membersMap4) {
        super(id);
        this.membersMap1 = membersMap1;
        this.membersMap2 = membersMap2;
        this.membersMap3 = membersMap3;
        this.membersMap4 = membersMap4;
    }

    public Map<String, ClassA> getMembersMap1() {
        return membersMap1;
    }

    public void setMembersMap1(Map<String, ClassA> membersMap1) {
        this.membersMap1 = membersMap1;
    }

    public Map<ClassA, ClassB> getMembersMap2() {
        return membersMap2;
    }

    public void setMembersMap2(Map<ClassA, ClassB> membersMap2) {
        this.membersMap2 = membersMap2;
    }

    public Map<String, Long> getMembersMap3() {
        return membersMap3;
    }

    public void setMembersMap3(Map<String, Long> membersMap3) {
        this.membersMap3 = membersMap3;
    }

    public Map<ClassA, Date> getMembersMap4() {
        return membersMap4;
    }

    public void setMembersMap4(Map<ClassA, Date> membersMap4) {
        this.membersMap4 = membersMap4;
    }

    @Override
    public String toString() {
        return "ClassFWithMap{" +
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
        if (!(o instanceof ClassFWithMap)) return false;
        if (!super.equals(o)) return false;

        ClassFWithMap that = (ClassFWithMap) o;

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

    @Override
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
