package com.datapirates.touristguideapp.service.impl;

import com.datapirates.touristguideapp.dto.requestDto.HotelReqDTO;
import com.datapirates.touristguideapp.dto.requestDto.HotelRoomDto;
import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.hotel.RoomCategory;
import com.datapirates.touristguideapp.entity.location.Location;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import com.datapirates.touristguideapp.repository.exception.ResourceNotFoundException;
import com.datapirates.touristguideapp.service.interfaces.hotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datapirates.touristguideapp.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class hotelServiceImpl implements hotelService {

    @Autowired
    private hotelRepository hotelRepository;

    @Autowired
    private roomRepository roomRepository;

    @Autowired
    private roomCategoryRepository roomCategoryRepository;

    @Autowired
    private bookingRoomsRepository bookingRoomsRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private hotelOwnerRepository hotelOwnerRepository;

    @Override
    public List<RoomCategory> getAllCategories() {
        return roomCategoryRepository.findAll();
    }

    @Override
    public RoomCategory saveCategory(RoomCategory roomCategory) {
        return roomCategoryRepository.save(roomCategory);
    }

    @Override
    public String updateCategory(String id, RoomCategory roomCategory) {
        Optional<RoomCategory> checking = roomCategoryRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        roomCategoryRepository.save(roomCategory);
        return "update success";
    }
    @Override
    public Hotel saveHotel(HotelReqDTO hotelReqDTO) {
        return convertDtoToEntity(hotelReqDTO);
    }

    @Override
    public HotelRoom saveHotelRoom(HotelRoomDto hotelRoomDto) {
        return convertDatoToEntity(hotelRoomDto);
    }

    private Hotel convertDtoToEntity(HotelReqDTO hotelReqDTO) {
        Hotel hotel = new Hotel();
//        Set<HotelRoom> hotelRooms = hotelReqDTO.getHotelRooms();
//
//        hotel.setName(hotelReqDTO.getName());
//        hotel.setRateAmount(hotelReqDTO.getRateAmount());
//        hotel.setDistrict(hotelReqDTO.getDistrict());
//        hotel.setRating(hotelReqDTO.getRating());
//        hotel.setNo(hotelReqDTO.getNo());
//        hotel.setTown(hotelReqDTO.getTown());
//        hotel.setHotelRooms(hotelReqDTO.getHotelRooms());
     //   hotel.setHotelOwner(hotelReqDTO.getHotelOwner());
        hotel = hotelReqDTO.getHotel();

        Location existingLocation = locationRepository.findById(hotelReqDTO.getLocationId()).orElseThrow(() ->
                new ResourceNotFoundException("Location", "Id", hotelReqDTO.getHotel()));
        hotel.setLocation(existingLocation);

        HotelOwner existingHotelOwner = hotelOwnerRepository.findById(hotelReqDTO.getOwner()).orElseThrow(() ->
                new ResourceNotFoundException("Owner", "Id", hotelReqDTO.getHotel()));
        hotel.setHotelOwner(existingHotelOwner);

        return hotelRepository.save(hotel);
    }



    private HotelRoom convertDatoToEntity(HotelRoomDto hotelRoomDto) {
        HotelRoom hotelRoom = hotelRoomDto.getHotelRoom();
//        Set<HotelRoom> hotelRooms = hotelReqDTO.getHotelRooms();
//
//        hotel.setName(hotelReqDTO.getName());
//        hotel.setRateAmount(hotelReqDTO.getRateAmount());
//        hotel.setDistrict(hotelReqDTO.getDistrict());
//        hotel.setRating(hotelReqDTO.getRating());
//        hotel.setNo(hotelReqDTO.getNo());
//        hotel.setTown(hotelReqDTO.getTown());
//        hotel.setHotelRooms(hotelReqDTO.getHotelRooms());
        //   hotel.setHotelOwner(hotelReqDTO.getHotelOwner());

        RoomCategory existingCategory = roomCategoryRepository.findById(hotelRoomDto.getCategoryType()).orElseThrow(() ->
                new ResourceNotFoundException("Owner", "Id", hotelRoomDto.getHotelRoom()));
        hotelRoom.setRoomCategory(existingCategory);

        return roomRepository.save(hotelRoom);
    }

    @Override
    public void deleteCategory(String id) {
        roomCategoryRepository.deleteById(id);
    }

    @Override
    public List<HotelRoom> getByAvailabilityAndHotel(Long id, String availability) {
        return roomRepository.findByHotelAndRoomAvailability(id,availability);
    }

    @Override
    public List<HotelRoom> getHotelRoom() {
        return roomRepository.findAll();
    }

    @Override
    public String updateAvailability(Long id, Long roomNo, String availability) {
        Optional<HotelRoom> checking = roomRepository.findByHotelAndRoomNo(id,roomNo);
        if (!checking.isPresent()){
            return "not available Id";
        }
        roomRepository.setAvailability(id,availability,roomNo);
        return "update success";
    }

    @Override
    public void updateCategoryType(String category, String type) {
        roomRepository.setCategory(category,type);
    }

    @Override
    public String hotelRating(Long id, int starCount) {
        Optional<Hotel> checking = hotelRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        double currentRate = hotelRepository.getRate(id);
        Long currentAmount = hotelRepository.getRateAmount(id);
        double currentStars = currentAmount * currentRate;

        /*** after updating ***/

        currentStars+=starCount;
        currentAmount+=1;
        currentRate=currentStars/currentAmount;
        hotelRepository.setRate(id,currentRate);
        hotelRepository.setRateAmount(id,currentAmount);
        return "successful rated";
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }



    @Override
    public String updateHotel(Long id, Hotel hotel) {
        Optional<Hotel> checking = hotelRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        hotelRepository.save(hotel);
        return "update success";
    }

    @Override
    public Long getOwnerId(Long hotelId) {
        return hotelRepository.getOwnerId(hotelId);
    }

    @Override
    public void updateOwner(Long owner, Long id) {
        hotelRepository.setOwner(id,owner);
    }

    /*** booking rooms **/
    @Override
    public void updateRoomsAvailability(Long hotelBooking, String availability, Long hotelId) {
        List<Long> rooms = bookingRoomsRepository.getRoomNosByBooking(hotelBooking);
        for(int index=0; index<rooms.size(); index++){
            Long roomNo = rooms.get(index);
             roomRepository.setAvailability(hotelId,availability,roomNo);
        }
    }
}
