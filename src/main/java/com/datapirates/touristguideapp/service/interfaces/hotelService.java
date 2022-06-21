package com.datapirates.touristguideapp.service.interfaces;

import com.datapirates.touristguideapp.dto.requestDto.HotelReqDTO;
import com.datapirates.touristguideapp.dto.requestDto.HotelRoomDto;
import com.datapirates.touristguideapp.dto.responseDto.AvailableHotelDTO;
import com.datapirates.touristguideapp.dto.responseDto.HotelResponseDTO;
import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.hotel.RoomCategory;

import java.util.List;
import java.util.Optional;

public interface hotelService {
    /***Category***/
    List<RoomCategory> getAllCategories();
    RoomCategory saveCategory(RoomCategory roomCategory);
    String updateCategory(String id,RoomCategory roomCategory);
    String deleteCategory(String type);

    /*** HotelRoom***/

    List<AvailableHotelDTO> getAvailableHotels(String type, int amount, String startCount);
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
    void updateOwner(Long userId,Long id);
    Hotel saveHotel(HotelReqDTO hotelReqDTO);

    HotelRoom saveHotelRoom(HotelRoomDto hotelRoomDto);

    List<HotelResponseDTO> getHotelsByAppUserId(Long userId);

    String deleteHotel(Long id);

//    String deleteHotel(Long hotelId);

//    HotelOwner updateHotelOwner(Long userId, HotelOwner hotelOwner);

    /***bookingRooms***/
}
