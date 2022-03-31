package com.datapirates.touristguideapp.service.interfaces;

import com.datapirates.touristguideapp.model.user.HotelOwner;

import java.util.List;

public interface HotelOwnerService {
    HotelOwner saveGuide(HotelOwner hotelOwner);

    List<HotelOwner> getHotelOwners();

    HotelOwner updateHotelOwner(HotelOwner hotelOwner);

    String deleteHotelOwner(Long id);
}
