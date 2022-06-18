package com.datapirates.touristguideapp.admin;

import com.datapirates.touristguideapp.entity.Vehicle;
import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.datapirates.touristguideapp.entity.users.Guide;
import com.datapirates.touristguideapp.entity.users.HotelOwner;

import java.util.List;

public interface adminReject {
    String rejectGuide(Long id);
    String rejectDriver(Long id);
    String rejectHotelOwner(Long id);
    String rejectHotel(Long id);
    String rejectVehicle(Long id);
    String rejectHotelRoom(Long id, Long RoomNo);

}
