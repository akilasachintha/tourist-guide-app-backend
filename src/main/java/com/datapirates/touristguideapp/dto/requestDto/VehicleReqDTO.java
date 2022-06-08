package com.datapirates.touristguideapp.dto.requestDto;

import lombok.Data;

@Data
public class VehicleReqDTO {
    private String vehicleNo;

    private String vehicleType;

    private String vehicleName;

    private int seats;

    private double priceForKm;

    private String vehicleCondition;

    private String vehiclePhotoUrl;

    private Long userId;

}
