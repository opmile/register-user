package com.opmile.register_user.model;

import com.opmile.register_user.dto.user.UserCreateDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String username;

    private int age;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    public User() {}
    public User(String fullName, String email, String username, int age, Gender gender, Location location) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.location = location;
    }
    public User(UserCreateDTO dto) {
        this(
                dto.fullName(),
                dto.email(),
                dto.username(),
                dto.age(),
                Gender.fromString(dto.gender()),
                new Location(dto.location())
        );
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public Location getLocation() {
        return location;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
