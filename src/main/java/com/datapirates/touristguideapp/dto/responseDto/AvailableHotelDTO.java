package com.datapirates.touristguideapp.dto.responseDto;

import com.datapirates.touristguideapp.entity.hotel.Hotel;
import lombok.Data;

@Data
public class AvailableHotelDTO {

    private Hotel hotel;
    private double price;
}
