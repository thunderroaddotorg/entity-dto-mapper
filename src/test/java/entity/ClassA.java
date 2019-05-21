package entity;

import dto.ClassADTO;
import org.thunderroad.entitydtomapping.ToDtoMapper;

import java.util.Date;
import java.util.Objects;

public class ClassA implements ToDtoMapper<ClassA, ClassADTO> {

    private long id;
    private String name;
    private Date dateMember;
    private Object objMember;

    public ClassA() {
    }

    public ClassA(long id, String name, Date dateMember, Object objMember) {
        this.id = id;
        this.name = name;
        this.dateMember = dateMember;
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
        return "ClassA{" +
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
        ClassA classA = (ClassA) o;
        return id == classA.id &&
                name.equals(classA.name) &&
                dateMember.equals(classA.dateMember) &&
                objMember.equals(classA.objMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateMember, objMember);
    }
}
