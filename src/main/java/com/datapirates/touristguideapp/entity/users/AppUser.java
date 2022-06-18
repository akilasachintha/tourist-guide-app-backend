package com.datapirates.touristguideapp.entity.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private Long rateAmount = 0L;

    private String userType;

    private String userPhotoUrl;

    private String phoneNo;

    private String email;

    private String name;

    private String dob;

    private String password;

    private double rating;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
