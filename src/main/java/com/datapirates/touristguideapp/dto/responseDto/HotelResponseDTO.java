package com.datapirates.touristguideapp.dto.responseDto;

import com.datapirates.touristguideapp.entity.hotel.HotelImage;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import lombok.Data;

import java.util.Set;

@Data
public class HotelResponseDTO {

    private Long hotelId;

    private double rating;

    private Long rateAmount;

    private String name;

    private String No;

    private String district;

    private String town;

    private String adminStatus = "pending";

    private String description;

    private Long hotelOwnerId;

    private Set<HotelImage> hotelImages;

    private Set<HotelRoom> hotelRooms;
}
