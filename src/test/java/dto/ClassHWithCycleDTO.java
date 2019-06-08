package dto;

import entity.ClassHWithCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thunderroad.entitydtomapping.FromDtoMapper;
import org.thunderroad.entitydtomapping.annotations.Mapping;

import java.util.Objects;

public class ClassHWithCycleDTO extends AbstractClassDDTO implements FromDtoMapper<ClassHWithCycle,ClassHWithCycleDTO> {

    @Mapping("name")
    private String nameH;

    public ClassHWithCycleDTO() {
    }

    public ClassHWithCycleDTO(long id, String nameH) {
        super(id);
        this.nameH = nameH;
    }

    public String getNameH() {
        return nameH;
    }

    public void setNameH(String nameH) {
        this.nameH = nameH;
    }

    @Override
    public String toString() {
        return "ClassHWithCycleDTO{" +
                "nameH='" + nameH + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClassHWithCycleDTO that = (ClassHWithCycleDTO) o;
        return Objects.equals(nameH, that.nameH);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nameH);
    }

    @Override
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
