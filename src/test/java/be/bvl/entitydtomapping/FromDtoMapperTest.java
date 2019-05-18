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

public class FromDtoMapperTest {

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
    public void fromDtoSimple() {
        ClassA expectedEntity = new ClassA(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);
        ClassADTO dto = new ClassADTO(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);

        ClassA entity = new ClassA();
        dto.fromDto(dto, entity);

        Assert.assertEquals(expectedEntity, entity);
    }

    @Test
    public void fromDoMemberToDoMapperOneLevel() {
        ClassA expectedEntityA = new ClassA(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);
        ClassB expectedEntityB = new ClassB(CLASS_B_ID, expectedEntityA);
        ClassADTO dtoA = new ClassADTO(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);
        ClassBDTO dtoB = new ClassBDTO(CLASS_B_ID, dtoA);

        ClassB entity = new ClassB();
        dtoB.fromDto(dtoB, entity);

        Assert.assertEquals(expectedEntityB, entity);
    }

    @Test
    public void fromDoMemberToDoMapperOTwoLevels() {
        ClassA expectedEntityA = new ClassA(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);
        ClassB expectedEntityB = new ClassB(CLASS_B_ID, expectedEntityA);
        ClassC expectedEntityC = new ClassC(CLASS_C_ID, CLASS_C_NAME, expectedEntityB);
        ClassADTO dtoA = new ClassADTO(CLASS_A_ID, CLASS_A_NAME, CLASS_A_DATE, CLASS_A_OBJECT);
        ClassBDTO dtoB = new ClassBDTO(CLASS_B_ID, dtoA);
        ClassCDTO dtoC = new ClassCDTO(CLASS_C_ID, CLASS_C_NAME, dtoB);

        ClassC entity = new ClassC();
        dtoC.fromDto(dtoC, entity);

        Assert.assertEquals(expectedEntityC, entity);
    }

    @Test
    public void fromDtoMapperExtendsAbstract() {
        EffectiveClassE expectedEntity = new EffectiveClassE(CLASS_E_ID, CLASS_E_NAME);
        EffectiveClassEDTO effectiveClassEDTO = new EffectiveClassEDTO(CLASS_E_ID, CLASS_E_NAME);

        EffectiveClassE entity = new EffectiveClassE();
        effectiveClassEDTO.fromDto(effectiveClassEDTO, entity);
        Assert.assertEquals(expectedEntity, entity);
    }

}
