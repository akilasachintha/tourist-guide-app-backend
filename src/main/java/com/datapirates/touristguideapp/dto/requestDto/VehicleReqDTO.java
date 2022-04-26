package com.datapirates.touristguideapp.dto.requestDto;

import lombok.*;

@Data
public class VehicleReqDTO {
    private String vehicleNo;

    private String vehicleType;

    private int seats;

    private double priceForKm;

    private String vehicleCondition;

    private String photoUrl;

    private Long userId;
}
