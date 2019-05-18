package be.bvl.entitydtomapping;

import dto.ClassADTO;
import dto.ClassBDTO;
import dto.ClassCDTO;
import dto.EffectiveClassEDTO;
import entity.ClassA;
import entity.ClassB;
import entity.ClassC;
import entity.EffectiveClassE;
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
    private static final long CLASS_E_ID = 5L;
    private static final String CLASS_E_NAME = "classE Name";

    @Test
    public void toDtoSimple() {
        ClassA entity = new ClassA(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);
        ClassADTO expectedDto = new ClassADTO(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);

        ClassADTO dto = new ClassADTO();
        entity.toDto(entity, dto);

        Assert.assertEquals(expectedDto, dto);
    }

    @Test
    public void toDtoMapperOneLevel() {
        ClassA entityA = new ClassA(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);
        ClassB entityB = new ClassB(CLASS_B_ID, entityA);
        ClassADTO expectedDtoA = new ClassADTO(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);
        ClassBDTO expectedDtoB = new ClassBDTO(CLASS_B_ID, expectedDtoA);

        ClassBDTO dto = new ClassBDTO();
        entityB.toDto(entityB, dto);

        Assert.assertEquals(expectedDtoB, dto);
    }

    @Test
    public void toDtoMapperOTwoLevels() {
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

    @Test
    public void toDtoMapperExtendsAbstract() {
        EffectiveClassE entity = new EffectiveClassE(CLASS_E_ID, CLASS_E_NAME);
        EffectiveClassEDTO expectedEffectiveClassEDTO = new EffectiveClassEDTO(CLASS_E_ID, CLASS_E_NAME);

        EffectiveClassEDTO dto = new EffectiveClassEDTO();
        entity.toDto(entity, dto);
        Assert.assertEquals(expectedEffectiveClassEDTO, dto);
    }

}
