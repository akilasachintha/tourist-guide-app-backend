package com.datapirates.touristguideapp.dto.responseDto;

import lombok.Data;

@Data
public class DriverResponseDTO {
    private Long userId;

    private String userType;

    private String userPhotoUrl;

    private String name;

    private String licenceNo;

    private String availability;

    private double rating;
}
