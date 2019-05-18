package be.bvl.entitydtomapping;

import dto.ClassADTO;
import dto.ClassBDTO;
import dto.ClassCDTO;
import entity.ClassA;
import entity.ClassB;
import entity.ClassC;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

public class ToDtoMapperTest {

    private static final long CLASS_A_ID = 1L;
    private static final String CLASS_A_NAME = "classA Name";
    private static final Date CLASS_A_DATE = Date.from(LocalDate.of(2000, 10, 10).atStartOfDay().toInstant(ZoneOffset.UTC));
    private static final Object CLASS_A_OBJECT = new Object();
    private static final long CLASS_B_ID = 2L;
    private static final long CLASS_C_ID = 3L;
    private static final String CLASS_C_NAME = "classC Name";

    @Test
    public void toDtoSimple() {
        ClassA entity = new ClassA(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);
        ClassADTO expectedDto = new ClassADTO(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);

        ClassADTO dto = new ClassADTO();
        entity.toDto(entity, dto);

        Assert.assertEquals(expectedDto, dto);
    }

    @Test
    public void toDoMemberToDoMapperOneLevel() {
        ClassA entityA = new ClassA(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);
        ClassB entityB = new ClassB(CLASS_B_ID, entityA);
        ClassADTO expectedDtoA = new ClassADTO(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);
        ClassBDTO expectedDtoB = new ClassBDTO(CLASS_B_ID, expectedDtoA);

        ClassBDTO dto = new ClassBDTO();
        entityB.toDto(entityB, dto);

        Assert.assertEquals(expectedDtoB, dto);
    }

    @Test
    public void toDoMemberToDoMapperOTwoLevels() {
        ClassA entityA = new ClassA(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);
        ClassB entityB = new ClassB(CLASS_B_ID, entityA);
        ClassC entityC = new ClassC(CLASS_C_ID, CLASS_C_NAME, entityB);
        ClassADTO expectedDtoA = new ClassADTO(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);
        ClassBDTO expectedDtoB = new ClassBDTO(CLASS_B_ID, expectedDtoA);
        ClassCDTO expectedDtoC = new ClassCDTO(CLASS_C_ID, CLASS_C_NAME, expectedDtoB);

        ClassCDTO dto = new ClassCDTO();
        entityC.toDto(entityC, dto);

        Assert.assertEquals(expectedDtoC, dto);
    }

}
