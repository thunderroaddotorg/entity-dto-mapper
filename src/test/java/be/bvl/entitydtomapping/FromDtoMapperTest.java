package be.bvl.entitydtomapping;

import dto.*;
import entity.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class FromDtoMapperTest {

    private static final long CLASS_A1_ID = 1L;
    private static final String CLASS_A1_NAME = "classA1 Name";
    private static final Date CLASS_A1_DATE = Date.from(LocalDate.of(2000, 10, 10).atStartOfDay().toInstant(ZoneOffset.UTC));
    private static final Object CLASS_A1_OBJECT = new Object();
    private static final long CLASS_A2_ID = 10L;
    private static final String CLASS_A2_NAME = "classA2 Name";
    private static final Date CLASS_A2_DATE = Date.from(LocalDate.of(2001, 11, 11).atStartOfDay().toInstant(ZoneOffset.UTC));
    private static final Object CLASS_A2_OBJECT = new Object();
    private static final long CLASS_B_ID = 2L;
    private static final long CLASS_C_ID = 3L;
    private static final String CLASS_C_NAME = "classC Name";
    private static final long CLASS_E_ID = 5L;
    private static final String CLASS_E_NAME = "classE Name";
    private static final long CLASS_F_ID = 6L;

    @Test
    public void fromDtoSimple() {
        ClassA expectedEntity = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassADTO dto = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);

        ClassA entity = new ClassA();
        dto.fromDto(dto, entity);

        Assert.assertEquals(expectedEntity, entity);
    }

    @Test
    public void fromDoMemberToDoMapperOneLevel() {
        ClassA expectedEntityA = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassB expectedEntityB = new ClassB(CLASS_B_ID, expectedEntityA);
        ClassADTO dtoA = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassBDTO dtoB = new ClassBDTO(CLASS_B_ID, dtoA);

        ClassB entity = new ClassB();
        dtoB.fromDto(dtoB, entity);

        Assert.assertEquals(expectedEntityB, entity);
    }

    @Test
    public void fromDoMemberToDoMapperOTwoLevels() {
        ClassA expectedEntityA = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassB expectedEntityB = new ClassB(CLASS_B_ID, expectedEntityA);
        ClassC expectedEntityC = new ClassC(CLASS_C_ID, CLASS_C_NAME, expectedEntityB);
        ClassADTO dtoA = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
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

    @Test
    public void toDtoMapperWithCollection() {

        ClassA expectedEntityA1 = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassADTO dtoA1 = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassA expectedEntityA2 = new ClassA(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);
        ClassADTO dtoA2 = new ClassADTO(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);

        ClassFWithCollection expectedEntity =
                new ClassFWithCollection(CLASS_F_ID,
                        Arrays.asList(expectedEntityA1, expectedEntityA2),
                        new HashSet<>(Arrays.asList(expectedEntityA1, expectedEntityA2)));
        ClassFWithCollectionDTO dto =
                new ClassFWithCollectionDTO(CLASS_F_ID,
                        Arrays.asList(dtoA1, dtoA2),
                        new HashSet<>(Arrays.asList(dtoA1, dtoA2)));

        ClassFWithCollection entity = new ClassFWithCollection();
        dto.fromDto(dto, entity);
        Assert.assertEquals(expectedEntity, entity);
    }

}
