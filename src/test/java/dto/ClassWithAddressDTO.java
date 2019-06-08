package dto;

import converter.ClassWithAddressConverter;
import entity.ClassWithAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thunderroad.entitydtomapping.FromDtoMapper;
import org.thunderroad.entitydtomapping.annotations.EntityDtoConverter;

import java.util.Objects;

@EntityDtoConverter(converter = ClassWithAddressConverter.class)
public class ClassWithAddressDTO implements FromDtoMapper<ClassWithAddress,ClassWithAddressDTO> {

    private String streetName;
    private String houseNumber;
    private String zipCode;
    private String city;
    private String country;

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassWithAddressDTO that = (ClassWithAddressDTO) o;
        return Objects.equals(streetName, that.streetName) &&
                Objects.equals(houseNumber, that.houseNumber) &&
                Objects.equals(zipCode, that.zipCode) &&
                Objects.equals(city, that.city) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, houseNumber, zipCode, city, country);
    }

    @Override
    public String toString() {
        return "ClassWithAddressDTO{" +
                "streetName='" + streetName + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
