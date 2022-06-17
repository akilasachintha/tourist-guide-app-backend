package com.datapirates.touristguideapp.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.datapirates.touristguideapp.dto.requestDto.HotelReqDTO;
import com.datapirates.touristguideapp.dto.requestDto.HotelRoomDto;
import com.datapirates.touristguideapp.entity.hotel.*;

public interface hotelService {
    /***Category***/
    List<RoomCategory> getAllCategories();
    RoomCategory saveCategory(RoomCategory roomCategory);
    String updateCategory(String id,RoomCategory roomCategory);
    void deleteCategory(String id);

    /*** HotelRoom***/

    List<Hotel> getAvailableHotels(String type,int amount);
    List<HotelRoom> getHotelRoom();
    String updateAvailability(Long id,String availability);
    void updateCategoryType(String category,String type);

    /***Hotel***/
    String hotelRating(Long id,int starCount);
    List<Hotel> getAllHotels();
    Optional<Hotel> getHotelById(Long id);
    //Hotel saveHotel(Hotel hotel);
    String updateHotel(Long id,Hotel hotel);
    Long getOwnerId(Long hotelId);
    void updateOwner(Long owner,Long id);
    Hotel saveHotel(HotelReqDTO hotelReqDTO);

    HotelRoom saveHotelRoom(HotelRoomDto hotelRoomDto);

    /***bookingRooms***/
}
