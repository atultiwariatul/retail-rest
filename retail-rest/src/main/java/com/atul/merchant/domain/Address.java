package com.atul.merchant.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Address Class to represent all the Addresses. Ideally we should be using it with
 * @DBRef annotation.
 *
 * Created by atiwa00 on 6/4/16.
 */
@XmlRootElement(name = "address")
public class Address {
    private String street;
    private String apartment;
    private Integer zip;
    private String city;
    private String state;
    private String Country;
    private String countryCode;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "\nAddress{" +
                "street='" + street + '\'' +
                ", apartment='" + apartment + '\'' +
                ", zip=" + zip +
                ", city='" + city + '\'' +
                ", Country='" + Country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
