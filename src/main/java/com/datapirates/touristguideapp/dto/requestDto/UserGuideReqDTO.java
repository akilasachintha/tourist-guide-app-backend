package com.datapirates.touristguideapp.dto.requestDto;

import lombok.*;

@Data
public class UserGuideReqDTO {
    private Long userId;

    private String photoUrl;

    private String phoneNo;

    private String email;

    private String name;

    private String dob;

    private String password;

    private double rating;

    private double priceRange;

    private String NIC;

    private String availability;
}
