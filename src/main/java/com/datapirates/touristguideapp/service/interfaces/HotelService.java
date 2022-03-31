package com.datapirates.touristguideapp.service.interfaces;

import com.datapirates.touristguideapp.model.Hotel;

import java.util.List;

public interface HotelService {
    Hotel saveHotel(Hotel hotel);

    List<Hotel> getHotels();

    Hotel updateHotel(Hotel hotel);

    String deleteHotel(Long id);
}
