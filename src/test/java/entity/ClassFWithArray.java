package entity;

import dto.ClassFWithArrayDTO;
import org.thunderroad.entitydtomapping.ToDtoMapper;

import java.util.Arrays;

public class ClassFWithArray extends AbstractClassD implements ToDtoMapper<ClassFWithArray, ClassFWithArrayDTO> {

    private ClassA[] membersArray1;
    private Long[] membersArray2;
    private int[] membersArray3;

    public ClassFWithArray() {
    }

    public ClassFWithArray(long id,
                           ClassA[] membersArray1,
                           Long[] membersArray2,
                           int[] membersArray3) {
        super(id);
        this.membersArray1 = membersArray1;
        this.membersArray2 = membersArray2;
        this.membersArray3 = membersArray3;
    }

    public ClassA[] getMembersArray1() {
        return membersArray1;
    }

    public void setMembersArray1(ClassA[] membersArray1) {
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
        return "ClassFWithArray{" +
                "membersArray1=" + membersArray1 +
                ", membersArray2=" + membersArray2 +
                ", membersArray3=" + membersArray3 +
                ", id=" + getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassFWithArray)) return false;
        if (!super.equals(o)) return false;

        ClassFWithArray that = (ClassFWithArray) o;

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
