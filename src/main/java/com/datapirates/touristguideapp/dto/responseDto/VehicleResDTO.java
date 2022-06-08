package com.datapirates.touristguideapp.dto.responseDto;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class VehicleResDTO {
    private Long vehicleId;

    private String vehicleName;

    private String vehicleNo;

    private String vehicleType;

    private int seats;

    private double priceForKm;

    private String vehicleCondition;

    private String vehiclePhotoUrl;

    private Long userId;

    private String availability;

    private double rating;

    private String userPhotoUrl;
}
