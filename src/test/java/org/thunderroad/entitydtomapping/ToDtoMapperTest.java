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
    private static final long CLASS_G_ID = 7L;
    private static final String CLASS_G_NAME = "classG Name";
    private static final long CLASS_H1_ID = 8L;
    private static final String CLASS_H1_NAME = "classH1 Name";
    private static final long CLASS_H2_ID = 9L;
    private static final String CLASS_H2_NAME = "classH2 Name";

    @Test
    public void toDtoSimple() {
        ClassA entity = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassADTO expectedDto = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);

        Assert.assertEquals(expectedDto, entity.toDto(new ClassADTO()));
    }

    @Test
    public void toDtoMapperOneLevel() {
        ClassA entityA = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassB entityB = new ClassB(CLASS_B_ID, entityA);
        ClassADTO expectedDtoA = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassBDTO expectedDtoB = new ClassBDTO(CLASS_B_ID, expectedDtoA);

        Assert.assertEquals(expectedDtoB, entityB.toDto(new ClassBDTO()));
    }

    @Test
    public void toDtoMapperOTwoLevels() {
        ClassA entityA = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassB entityB = new ClassB(CLASS_B_ID, entityA);
        ClassC entityC = new ClassC(CLASS_C_ID, CLASS_C_NAME, entityB);
        ClassADTO expectedDtoA = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassBDTO expectedDtoB = new ClassBDTO(CLASS_B_ID, expectedDtoA);
        ClassCDTO expectedDtoC = new ClassCDTO(CLASS_C_ID, CLASS_C_NAME, expectedDtoB);

        Assert.assertEquals(expectedDtoC, entityC.toDto(new ClassCDTO()));
    }

    @Test
    public void toDtoMapperExtendsAbstract() {
        EffectiveClassE entity = new EffectiveClassE(CLASS_E_ID, CLASS_E_NAME);
        EffectiveClassEDTO expectedEffectiveClassEDTO = new EffectiveClassEDTO(CLASS_E_ID, CLASS_E_NAME);

        Assert.assertEquals(expectedEffectiveClassEDTO, entity.toDto(new EffectiveClassEDTO()));
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

        Assert.assertEquals(expectedDto, entity.toDto(new ClassFWithCollectionDTO()));
    }

    @Test
    public void toDtoMapperWithInvalidCollection() {

        ClassA entityA1 = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassA entityA2 = new ClassA(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);

        Queue<ClassA> classACollection = new LinkedList<>();

        classACollection.add(entityA1);
        classACollection.add(entityA2);
        ClassFWithInvalidCollection entity = new ClassFWithInvalidCollection(CLASS_F_ID, classACollection);

        try {
            entity.toDto(new ClassFWithInvalidCollectionDTO());
            fail("Test should throw UnsupportedOperationException with message: \"<class name> holds a member that is a java.util.Collection other than java.util.List or java.util.Set.\"");
        } catch (UnsupportedOperationException e) {
            Assert.assertEquals("entity.ClassFWithInvalidCollection holds a member that is a java.util.Collection other than java.util.List or java.util.Set.", e.getMessage());
        }
    }

    @Test
    public void toDtoMapperWithMap() {

        ClassA entityA1 = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassA entityA2 = new ClassA(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);
        ClassADTO expectedDtoA1 = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassADTO expectedDtoA2 = new ClassADTO(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);
        ClassB entityB = new ClassB(CLASS_B_ID, entityA1);
        ClassBDTO expectedDtoB = new ClassBDTO(CLASS_B_ID, expectedDtoA1);

        Map<String,ClassA> map1 = new HashMap<>();
        map1.put(entityA1.getName(), entityA1);
        map1.put(entityA2.getName(), entityA2);
        Map<ClassA,ClassB> map2 = new HashMap<>();
        map2.put(entityA1,entityB);
        Map<String,ClassADTO> map1DTO = new HashMap<>();
        map1DTO.put(expectedDtoA1.getName(), expectedDtoA1);
        map1DTO.put(expectedDtoA2.getName(), expectedDtoA2);
        Map<ClassADTO,ClassBDTO> map2DTO = new HashMap<>();
        map2DTO.put(expectedDtoA1,expectedDtoB);
        Map<String, Long> stringLongMap = new HashMap<>();
        stringLongMap.put("testString", 666L);
        Map<ClassA, Date> map4 = new HashMap<>();
        map4.put(entityA1, entityA1.getDateMember());
        map4.put(entityA2, entityA2.getDateMember());
        Map<ClassADTO, Date> map4DTO = new HashMap<>();
        map4DTO.put(expectedDtoA1, expectedDtoA1.getDateMember());
        map4DTO.put(expectedDtoA2, expectedDtoA2.getDateMember());

        ClassFWithMap entity = new ClassFWithMap(CLASS_F_ID, map1, map2, stringLongMap, map4);
        ClassFWithMapDTO expectedDto = new ClassFWithMapDTO(CLASS_F_ID, map1DTO, map2DTO, stringLongMap, map4DTO);

        Assert.assertEquals(expectedDto, entity.toDto(new ClassFWithMapDTO()));
    }

    @Test
    public void toDtoMapperWithArray() {

        ClassA entityA1 = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassA entityA2 = new ClassA(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);
        ClassADTO expectedDtoA1 = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassADTO expectedDtoA2 = new ClassADTO(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);

        ClassFWithArray entity = new ClassFWithArray(CLASS_F_ID, new ClassA[] {entityA1, entityA2},
                new Long[] {666L, 777L}, new int[] {1});
        ClassFWithArrayDTO expectedDto = new ClassFWithArrayDTO(CLASS_F_ID, new ClassADTO[] {expectedDtoA1, expectedDtoA2},
                new Long[] {666L, 777L}, new int[] {1});

        Assert.assertEquals(expectedDto, entity.toDto(new ClassFWithArrayDTO()));
    }

    @Test
    public void toDtoMapperWithCycle() {
        ClassGWithCycle entityG = new ClassGWithCycle(CLASS_G_ID, CLASS_G_NAME, null);
        ClassHWithCycle entityH1 = new ClassHWithCycle(CLASS_H1_ID, CLASS_H1_NAME, entityG);
        ClassHWithCycle entityH2 = new ClassHWithCycle(CLASS_H2_ID, CLASS_H2_NAME, entityG);
        entityG.setMembersH(Arrays.asList(entityH1, entityH2));
        ClassGWithCycleDTO expectedEntityGDTO = new ClassGWithCycleDTO(CLASS_G_ID, CLASS_G_NAME, null);
        ClassHWithCycleDTO expectedEntityH1DTO = new ClassHWithCycleDTO(CLASS_H1_ID, CLASS_H1_NAME);
        ClassHWithCycleDTO expectedEntityH2DTO = new ClassHWithCycleDTO(CLASS_H2_ID, CLASS_H2_NAME);
        expectedEntityGDTO.setMembersH(Arrays.asList(expectedEntityH1DTO, expectedEntityH2DTO));
        ClassWithCycle entity = new ClassWithCycle(Arrays.asList(entityG));
        entityG.setParent(entity);
        ClassWithCycleDTO expectedDto = new ClassWithCycleDTO(Arrays.asList(expectedEntityGDTO));

        Assert.assertEquals(expectedDto, entity.toDto(new ClassWithCycleDTO()));

    }
}
