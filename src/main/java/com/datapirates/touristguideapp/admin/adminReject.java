package com.datapirates.touristguideapp.admin;

public interface adminReject {
    String rejectGuide(Long id);
    String rejectDriver(Long id);
    String rejectHotelOwner(Long id);
    String rejectHotel(Long id);
    String rejectVehicle(Long id);
    String rejectHotelRoom(Long id);

}
