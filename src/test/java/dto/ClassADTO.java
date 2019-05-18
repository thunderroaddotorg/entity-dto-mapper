package dto;

import be.bvl.entitydtomapping.FromDtoMapper;
import entity.ClassA;

import java.util.Date;
import java.util.Objects;

public class ClassADTO implements FromDtoMapper<ClassA, ClassADTO> {

    private long id;
    private String name;
    private Date dateMember;
    private Object objMember;

    public ClassADTO() {
    }

    public ClassADTO(long id, String name, Date date, Object objMember) {
        this.id = id;
        this.name = name;
        this.dateMember = date;
        this.objMember = objMember;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateMember() {
        return dateMember;
    }

    public void setDateMember(Date dateMember) {
        this.dateMember = dateMember;
    }

    public Object getObjMember() {
        return objMember;
    }

    public void setObjMember(Object objMember) {
        this.objMember = objMember;
    }

    @Override
    public String toString() {
        return "ClassADTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateMember=" + dateMember +
                ", objMember=" + objMember +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassADTO classADTO = (ClassADTO) o;
        return id == classADTO.id &&
                name.equals(classADTO.name) &&
                dateMember.equals(classADTO.dateMember) &&
                objMember.equals(classADTO.objMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateMember, objMember);
    }
}
