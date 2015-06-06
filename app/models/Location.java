package models;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by Kris on 2015-06-06.
 */
@Embedded
public class Location {
    String city;
    String country;
    String street;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}