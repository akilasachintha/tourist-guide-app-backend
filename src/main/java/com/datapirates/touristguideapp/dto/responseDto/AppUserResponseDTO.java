package com.datapirates.touristguideapp.dto.responseDto;

import lombok.Data;

@Data
public class AppUserResponseDTO {

    private Long userId;

    private String userType;

    private String userPhotoUrl;

    private String phoneNo;

    private String email;

    private String name;

    private String dob;

    private double rating;

    private String adminStatus;

    private String availability;

    private String verifyStatus;
}
