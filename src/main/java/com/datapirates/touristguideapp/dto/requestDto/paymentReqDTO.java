package com.datapirates.touristguideapp.dto.requestDto;

import lombok.Data;

@Data
public class paymentReqDTO {

    private Long hotelId;
    private Long guideId;
    private Long vehicleId;
    private int dayCount;
    private int roomCount;
    private String categoryType;
}
