package entity;

import be.bvl.entitydtomapping.ToDtoMapper;
import dto.EffectiveClassEDTO;

public class EffectiveClassE extends AbstractClassD implements ToDtoMapper<EffectiveClassE, EffectiveClassEDTO> {

    private String nameMember;

    public EffectiveClassE() {
    }

    public EffectiveClassE(long id, String nameMember) {
        super(id);
        this.nameMember = nameMember;
    }

    public String getNameMember() {
        return nameMember;
    }

    public void setNameMember(String nameMember) {
        this.nameMember = nameMember;
    }

    @Override
    public String toString() {
        return "EffectiveClassE{" +
                "nameMember='" + nameMember + '\'' +
                ", id=" + getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EffectiveClassE that = (EffectiveClassE) o;
        return nameMember.equals(that.nameMember) && getId() == that.getId();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + nameMember.hashCode();
        return result;
    }
}
