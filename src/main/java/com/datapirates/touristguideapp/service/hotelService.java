package com.datapirates.touristguideapp.service;

import java.util.List;
import java.util.Optional;

import com.datapirates.touristguideapp.entity.hotel.*;

public interface hotelService {
    /***Category***/
    List<RoomCategory> getAllCategories();
    RoomCategory saveCategory(RoomCategory roomCategory);
    String updateCategory(String id,RoomCategory roomCategory);
    void deleteCategory(String id);

    /*** HotelRoom***/

    List<HotelRoom> getByAvailabilityAndHotel(Long id,String availability);
    String updateAvailability(Long id,Long roomNo,String availability);
    void updateCategoryType(String category,String type);

    /***Hotel***/
    String hotelRating(Long id,int starCount);
    List<Hotel> getAllHotels();
    Optional<Hotel> getHotelById(Long id);
    Hotel saveHotel(Hotel hotel);
    String updateHotel(Long id,Hotel hotel);
    Long getOwnerId(Long hotelId);
    void updateOwner(Long owner,Long id);

    /***bookingRooms***/
    void updateRoomsAvailability(Long hotelBooking, String availability, Long hotelId);
}
