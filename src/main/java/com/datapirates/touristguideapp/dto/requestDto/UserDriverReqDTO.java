package com.datapirates.touristguideapp.dto.requestDto;

import lombok.*;

@Data
public class UserDriverReqDTO {
    private Long userId;

    private String photoUrl;

    private String phoneNo;

    private String email;

    private String name;

    private String dob;

    private String password;

    private String licenceNo;

    private String availability;

    private double rating;

    private Long locationId;
}
