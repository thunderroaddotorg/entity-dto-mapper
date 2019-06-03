package entity;

import java.util.Objects;

public class Address {

    private String street;
    private int housenumber;
    private String boxnumber;
    private String zipCode;
    private String city;
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(int housenumber) {
        this.housenumber = housenumber;
    }

    public String getBoxnumber() {
        return boxnumber;
    }

    public void setBoxnumber(String boxnumber) {
        this.boxnumber = boxnumber;
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
        Address address = (Address) o;
        return housenumber == address.housenumber &&
                Objects.equals(street, address.street) &&
                boxnumber.equals(address.boxnumber) &&
                Objects.equals(zipCode, address.zipCode) &&
                Objects.equals(city, address.city) &&
                Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, housenumber, boxnumber, zipCode, city, country);
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", housenumber=" + housenumber +
                ", boxnumber='" + boxnumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
