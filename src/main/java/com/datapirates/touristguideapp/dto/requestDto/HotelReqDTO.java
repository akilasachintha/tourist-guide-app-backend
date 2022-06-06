package com.datapirates.touristguideapp.dto.requestDto;

import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import lombok.Data;
import com.datapirates.touristguideapp.entity.*;

import java.util.HashSet;
import java.util.Set;

@Data
public class HotelReqDTO {

    private Hotel hotel;
    private Long locationId;
    private Long owner;
}

