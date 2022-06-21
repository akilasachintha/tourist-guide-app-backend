package com.datapirates.touristguideapp.admin;

import com.datapirates.touristguideapp.entity.Vehicle;
import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.datapirates.touristguideapp.entity.users.Guide;
import com.datapirates.touristguideapp.entity.users.HotelOwner;

import java.util.List;

public interface adminApprove {
    List<HotelRoom> getRoomByAdmin(String status);

    List<Guide> getGuideByAdmin(String status);

    List<Hotel> getHotelByAdmin(String status);

    List<Driver> getDriverByAdmin(String status);

    List<HotelOwner> getHotelOwnerByAdmin(String status);

    List<Vehicle> getVehicleByAdmin(String status);

    String approveGuide(Long id);

    String approveDriver(Long id);

    String approveHotelOwner(Long id);

    String approveHotel(Long id);

    String approveVehicle(Long id);

    String approveHotelRoom(Long id);

}
