package com.datapirates.touristguideapp.dto.requestDto;

import com.datapirates.touristguideapp.entity.users.HotelOwner;
import lombok.Data;
import com.datapirates.touristguideapp.entity.*;

@Data
public class HotelReqDTO {
    private Long locationId;
    private  Long hotelId;
    private double rating;
    private Long rateAmount;
    private String name;
    private String No;
    private String district;
    private String town;

    private Long userId;



}

