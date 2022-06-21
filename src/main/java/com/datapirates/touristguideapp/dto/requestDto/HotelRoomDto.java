package com.datapirates.touristguideapp.dto.requestDto;

import lombok.Data;

@Data
public class HotelRoomDto {
    private Long roomNo;

    private String roomCondition;

    private String roomAvailability = "yes";

    private double price;

    private String categoryType;

    private Long hotelId;
}
