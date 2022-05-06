package com.datapirates.touristguideapp.entity.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private Long rateAmount;

    private String userType;

    private String userPhotoUrl;

    private String phoneNo;

    private String email;

    private String name;

    private String dob;

    private String password;

}
