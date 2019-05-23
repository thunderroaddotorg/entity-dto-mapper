package dto;

import entity.ClassFWithArray;
import org.thunderroad.entitydtomapping.FromDtoMapper;

import java.util.Arrays;

public class ClassFWithArrayDTO extends AbstractClassDDTO implements FromDtoMapper<ClassFWithArray, ClassFWithArrayDTO> {

    private ClassADTO[] membersArray1;
    private Long[] membersArray2;
    private int[] membersArray3;

    public ClassFWithArrayDTO() {
    }

    public ClassFWithArrayDTO(long id,
                              ClassADTO[] membersArray1,
                              Long[] membersArray2,
                              int[] membersArray3) {
        super(id);
        this.membersArray1 = membersArray1;
        this.membersArray2 = membersArray2;
        this.membersArray3 = membersArray3;
    }

    public ClassADTO[] getMembersArray1() {
        return membersArray1;
    }

    public void setMembersArray1(ClassADTO[] membersArray1) {
        this.membersArray1 = membersArray1;
    }

    public Long[] getMembersArray2() {
        return membersArray2;
    }

    public void setMembersArray2(Long[] membersArray2) {
        this.membersArray2 = membersArray2;
    }

    public int[] getMembersArray3() {
        return membersArray3;
    }

    public void setMembersArray3(int[] membersArray3) {
        this.membersArray3 = membersArray3;
    }

    @Override
    public String toString() {
        return "ClassFWithArrayDTO{" +
                "membersArray1=" + membersArray1 +
                ", membersArray2=" + membersArray2 +
                ", membersArray3=" + membersArray3 +
                ", id=" + getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassFWithArrayDTO)) return false;
        if (!super.equals(o)) return false;

        ClassFWithArrayDTO that = (ClassFWithArrayDTO) o;

        return Arrays.equals(getMembersArray1(), that.getMembersArray1())
                && Arrays.equals(getMembersArray2(), that.getMembersArray2())
                && Arrays.equals(getMembersArray3(), that.getMembersArray3());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result
                + getMembersArray1().hashCode()
                + getMembersArray2().hashCode()
                + getMembersArray3().hashCode();
        return result;
    }
}
