package org.thunderroad.entitydtomapping;

import dto.*;
import entity.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.Assert.fail;

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
    private static final long CLASS_G_ID = 7L;
    private static final String CLASS_G_NAME = "classG Name";
    private static final long CLASS_H1_ID = 8L;
    private static final String CLASS_H1_NAME = "classH1 Name";
    private static final long CLASS_H2_ID = 9L;
    private static final String CLASS_H2_NAME = "classH2 Name";

    @Test
    public void fromDtoSimple() {
        ClassA expectedEntity = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassADTO dto = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);

        Assert.assertEquals(expectedEntity, dto.fromDto(new ClassA()));
    }

    @Test
    public void fromDoMemberToDoMapperOneLevel() {
        ClassA expectedEntityA = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassB expectedEntityB = new ClassB(CLASS_B_ID, expectedEntityA);
        ClassADTO dtoA = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassBDTO dtoB = new ClassBDTO(CLASS_B_ID, dtoA);

        Assert.assertEquals(expectedEntityB, dtoB.fromDto(new ClassB()));
    }

    @Test
    public void fromDoMemberToDoMapperOTwoLevels() {
        ClassA expectedEntityA = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassB expectedEntityB = new ClassB(CLASS_B_ID, expectedEntityA);
        ClassC expectedEntityC = new ClassC(CLASS_C_ID, CLASS_C_NAME, expectedEntityB);
        ClassADTO dtoA = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassBDTO dtoB = new ClassBDTO(CLASS_B_ID, dtoA);
        ClassCDTO dtoC = new ClassCDTO(CLASS_C_ID, CLASS_C_NAME, dtoB);

        Assert.assertEquals(expectedEntityC, dtoC.fromDto(new ClassC()));
    }

    @Test
    public void fromDtoMapperExtendsAbstract() {
        EffectiveClassE expectedEntity = new EffectiveClassE(CLASS_E_ID, CLASS_E_NAME);
        EffectiveClassEDTO effectiveClassEDTO = new EffectiveClassEDTO(CLASS_E_ID, CLASS_E_NAME);

        Assert.assertEquals(expectedEntity, effectiveClassEDTO.fromDto(new EffectiveClassE()));
    }

    @Test
    public void fromDtoMapperWithCollection() {

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

        Assert.assertEquals(expectedEntity, dto.fromDto(new ClassFWithCollection()));
    }

    @Test
    public void fromDtoMapperWithInvalidCollection() {

        ClassADTO dtoA1 = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassADTO dtoA2 = new ClassADTO(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);

        Queue<ClassADTO> classACollection = new LinkedList<>();

        classACollection.add(dtoA1);
        classACollection.add(dtoA2);
        ClassFWithInvalidCollectionDTO dto = new ClassFWithInvalidCollectionDTO(CLASS_F_ID, classACollection);

        try {
            dto.fromDto(new ClassFWithInvalidCollection());
            fail("Test should throw UnsupportedOperationException with message: \"<class name> holds a member that is a java.util.Collection other than java.util.List or java.util.Set.\"");
        } catch (UnsupportedOperationException e) {
            Assert.assertEquals("dto.ClassFWithInvalidCollectionDTO holds a member that is a java.util.Collection other than java.util.List or java.util.Set.", e.getMessage());
        }
    }

    @Test
    public void fromDtoMapperWithMap() {

        ClassA expectedEntityA1 = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassA expectedEntityA2 = new ClassA(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);
        ClassADTO dtoA1 = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassADTO dtoA2 = new ClassADTO(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);
        ClassB expectedEntityB = new ClassB(CLASS_B_ID, expectedEntityA1);
        ClassBDTO dtoB = new ClassBDTO(CLASS_B_ID, dtoA1);

        Map<String,ClassA> map1 = new HashMap<>();
        map1.put(expectedEntityA1.getName(), expectedEntityA1);
        map1.put(expectedEntityA2.getName(), expectedEntityA2);
        Map<ClassA,ClassB> map2 = new HashMap<>();
        map2.put(expectedEntityA1,expectedEntityB);
        Map<String,ClassADTO> map1DTO = new HashMap<>();
        map1DTO.put(dtoA1.getName(), dtoA1);
        map1DTO.put(dtoA2.getName(), dtoA2);
        Map<ClassADTO,ClassBDTO> map2DTO = new HashMap<>();
        map2DTO.put(dtoA1,dtoB);
        Map<String, Long> stringLongMap = new HashMap<>();
        stringLongMap.put("testString", 666L);
        Map<ClassA, Date> map4 = new HashMap<>();
        map4.put(expectedEntityA1, expectedEntityA1.getDateMember());
        map4.put(expectedEntityA2, expectedEntityA2.getDateMember());
        Map<ClassADTO, Date> map4DTO = new HashMap<>();
        map4DTO.put(dtoA1, dtoA1.getDateMember());
        map4DTO.put(dtoA2, dtoA2.getDateMember());

        ClassFWithMap expectedEntity = new ClassFWithMap(CLASS_F_ID, map1, map2, stringLongMap, map4);
        ClassFWithMapDTO dto = new ClassFWithMapDTO(CLASS_F_ID, map1DTO, map2DTO, stringLongMap, map4DTO);

        Assert.assertEquals(expectedEntity, dto.fromDto(new ClassFWithMap()));
    }

    @Test
    public void toDtoMapperWithArray() {

        ClassA expectedEntityA1 = new ClassA(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassA expectedEntityA2 = new ClassA(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);
        ClassADTO dtoA1 = new ClassADTO(CLASS_A1_ID, CLASS_A1_NAME, CLASS_A1_DATE, CLASS_A1_OBJECT);
        ClassADTO dtoA2 = new ClassADTO(CLASS_A2_ID, CLASS_A2_NAME, CLASS_A2_DATE, CLASS_A2_OBJECT);

        ClassFWithArray expectedEntity = new ClassFWithArray(CLASS_F_ID, new ClassA[] {expectedEntityA1, expectedEntityA2},
                new Long[] {666L, 777L}, new int[] {1});
        ClassFWithArrayDTO dto = new ClassFWithArrayDTO(CLASS_F_ID, new ClassADTO[] {dtoA1, dtoA2},
                new Long[] {666L, 777L}, new int[] {1});

        Assert.assertEquals(expectedEntity, dto.fromDto(new ClassFWithArray()));
    }

    @Test
    public void toDtoMapperWithCycle() {
        ClassGWithCycle expectedEntityG = new ClassGWithCycle(CLASS_G_ID, CLASS_G_NAME, null);
        ClassHWithCycle expectedEntityH1 = new ClassHWithCycle(CLASS_H1_ID, CLASS_H1_NAME, null); //memberG will not be filled
        ClassHWithCycle expectedEntityH2 = new ClassHWithCycle(CLASS_H2_ID, CLASS_H2_NAME, null); //memberG will not be filled
        expectedEntityG.setMembersH(Arrays.asList(expectedEntityH1, expectedEntityH2));
        ClassGWithCycleDTO dtoG = new ClassGWithCycleDTO(CLASS_G_ID, CLASS_G_NAME, null);
        ClassHWithCycleDTO dtoH1 = new ClassHWithCycleDTO(CLASS_H1_ID, CLASS_H1_NAME);
        ClassHWithCycleDTO dtoH2 = new ClassHWithCycleDTO(CLASS_H2_ID, CLASS_H2_NAME);
        dtoG.setMembersH(Arrays.asList(dtoH1, dtoH2));
        ClassWithCycle expectedEntity = new ClassWithCycle(Arrays.asList(expectedEntityG));
        ClassWithCycleDTO dto = new ClassWithCycleDTO(Arrays.asList(dtoG));
        dto.setIgnoredMember("Kiekeboe");

        Assert.assertEquals(expectedEntity, dto.fromDto(new ClassWithCycle()));

    }

}
