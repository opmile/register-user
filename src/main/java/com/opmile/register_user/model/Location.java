package com.opmile.register_user.model;

import com.opmile.register_user.dto.location.ILocationData;
import com.opmile.register_user.dto.location.LocationCreateDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String city;

    private String state;

    private String country;

    @OneToOne(mappedBy = "location")
    private User user;

    public Location() {}
    public Location(String street, String city, String state, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
    }
    public Location(ILocationData dto) {
        this(
                dto.street(),
                dto.city(),
                dto.state(),
                dto.country()
        );
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public User getUser() {
        return user;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
