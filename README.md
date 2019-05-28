# entity-dto-mapper

The entity-dto-mapper is a mapping library to facilitate the convertion of an 
entity bean to a similar DTO (Data Transfer Object) bean or reverse.
To make it possible, the entity or DTO bean class only has to implement an 
interface, ToDtoMapper<T,DTO> for conversion to DTO, and FromDtoMapper<T,DTO> 
for conversion from DTO to entity.
If the converted bean has members of a type that's also implementing the 
respective mapper, these members is also converted. 

## Registration

### Maven
```
<dependency>
    <groupId>com.github.thunderroaddotorg</groupId>
    <artifactId>entity-dto-mapper</artifactId>
    <version>${project.version}</version>
</dependency>
```
### Gradle
```
compile group: 'com.github.thunderroaddotorg', name: 'entity-dto-mapper', version: '${project.version}'
```

## Usage

### ToDtoMapper<T,DTO>

1.Implement the interface
```
public class SomeEntity implements ToDtoMapper<SomeEntity,SomeDTO> {
    
    ...
}
```
Some prerequisites:
  * SomeEntity and SomeDTO have the same mirrored structure, f.e. a member of type List<SomeOtherClass> has a mirror member of type List<SomeOtherDTO>.
  * for the members the types java.util.List, java.util.Set, java.util.Map, as well as arrays are supported and also converted, primitive members and objects of classes that don't implement the ToDtoMapper (or in reverse FromDtoMapper), will be copied as is.
  * the classes SomeEntity and SomeDTO need to have a default constructor.
  * if the field names differ in the entity bean and the dto, they can be mapped with the @Mapping annotation (in both directions).
  * if a field does not have a mirrored member and does not need to be converted, this can be indicated with the @IgnoreMapping annotation.

2.Execute the conversion in the code. 

To get a DTO from an entity bean the following code can be used:
```
    ...
    // entity is the entity bean that implements ToDtoMapper<SomeEntity,SomeDTO>
    SomeDTO dto = new SomeDTO();
    entity.toDto(entity, dto);
    // do something with the dto object
    ...
```

### FromDtoMapper<T,DTO>

1.Implement the interface
```
public class SomeDTO implements ToDtoMapper<SomeEntity,SomeDTO> {
    
    ...
}
```
Some prerequisites:
  * SomeEntity and SomeDTO have the same mirrored structure, f.e. a member of type List<SomeOtherClass> has a mirror member of type List<SomeOtherDTO>.
  * for the members the types java.util.List, java.util.Set, java.util.Map, as well as arrays are supported and also converted, primitive members and objects of classes that don't implement the ToDtoMapper (or in reverse FromDtoMapper), will be copied as is.
  * the classes SomeEntity and SomeDTO need to have a default constructor.
  * if the field names differ in the entity bean and the dto, they can be mapped with the @Mapping annotation (in both directions).
  * if a field does not have a mirrored member and does not need to be converted, this can be indicated with the @IgnoreMapping annotation.

2.Execute the conversion in the code. 

To get an entity from a DTO bean the following code can be used:
```
    ...
    // dto is the DTO bean that implements FromDtoMapper<SomeEntity,SomeDTO>
    SomeEntity entity = new SomeEntity();
    dto.fromDto(dto, entity);
    // do something with the entity object
    ...
```

## API
https://www.javadoc.io/doc/com.github.thunderroaddotorg/entity-dto-mapper
