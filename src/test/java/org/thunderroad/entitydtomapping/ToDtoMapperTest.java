package org.thunderroad.entitydtomapping;

import dto.*;
import entity.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.Assert.fail;

public class ToDtoMapperTest {

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
    public void toDtoSimple() {
        ClassA entity = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassADTO expectedDto = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);

        ClassADTO dto = new ClassADTO();
        entity.toDto(entity, dto);

        Assert.assertEquals(expectedDto, dto);
    }

    @Test
    public void toDtoMapperOneLevel() {
        ClassA entityA = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassB entityB = new ClassB(CLASS_B_ID, entityA);
        ClassADTO expectedDtoA = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassBDTO expectedDtoB = new ClassBDTO(CLASS_B_ID, expectedDtoA);

        ClassBDTO dto = new ClassBDTO();
        entityB.toDto(entityB, dto);

        Assert.assertEquals(expectedDtoB, dto);
    }

    @Test
    public void toDtoMapperOTwoLevels() {
        ClassA entityA = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassB entityB = new ClassB(CLASS_B_ID, entityA);
        ClassC entityC = new ClassC(CLASS_C_ID, CLASS_C_NAME, entityB);
        ClassADTO expectedDtoA = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
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

    @Test
    public void toDtoMapperWithCollection() {

        ClassA entityA1 = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassADTO expectedDtoA1 = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassA entityA2 = new ClassA(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);
        ClassADTO expectedDtoA2 = new ClassADTO(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);

        ClassFWithCollection entity =
                new ClassFWithCollection(CLASS_F_ID,
                        Arrays.asList(entityA1, entityA2),
                        new HashSet<>(Arrays.asList(entityA1, entityA2)));
        ClassFWithCollectionDTO expectedDto =
                new ClassFWithCollectionDTO(CLASS_F_ID,
                        Arrays.asList(expectedDtoA1, expectedDtoA2),
                        new HashSet<>(Arrays.asList(expectedDtoA1, expectedDtoA2)));

        ClassFWithCollectionDTO dto = new ClassFWithCollectionDTO();
        entity.toDto(entity, dto);
        Assert.assertEquals(expectedDto, dto);
    }

    @Test
    public void toDtoMapperWithInvalidCollection() {

        ClassA entityA1 = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassA entityA2 = new ClassA(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);

        Queue<ClassA> classAVector = new LinkedList<>();

        classAVector.add(entityA1);
        classAVector.add(entityA2);
        ClassFWithInvalidCollection entity = new ClassFWithInvalidCollection(CLASS_F_ID, classAVector);

        ClassFWithInvalidCollectionDTO dto = new ClassFWithInvalidCollectionDTO();
        try {
            entity.toDto(entity, dto);
            fail("Test should throw UnsupportedOperationException with message: \"<class name> holds a member that is a java.util.Collection other than java.util.List or java.util.Set.\"");
        } catch (UnsupportedOperationException e) {
            Assert.assertEquals("entity.ClassFWithInvalidCollection holds a member that is a java.util.Collection other than java.util.List or java.util.Set.", e.getMessage());
        }
    }

}
