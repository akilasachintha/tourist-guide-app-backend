package com.datapirates.touristguideapp.dto.requestDto;

import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import lombok.Data;

@Data
public class HotelRoomDto {
    private Long roomNo;

    private String roomCondition;

    private String roomAvailability;

    private double price;

    private String categoryType;

    private Long hotelId;
}
