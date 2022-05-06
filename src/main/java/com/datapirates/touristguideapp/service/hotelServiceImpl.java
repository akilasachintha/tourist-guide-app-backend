package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.hotel.RoomCategory;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.datapirates.touristguideapp.entity.users.Guide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datapirates.touristguideapp.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class hotelServiceImpl implements hotelService{

    @Autowired
    private hotelRepository hotelRepository;

    @Autowired
    private roomRepository roomRepository;

    @Autowired
    private roomCategoryRepository roomCategoryRepository;
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
    public void deleteCategory(String id) {
        roomCategoryRepository.deleteById(id);
    }

    @Override
    public List<HotelRoom> getByAvailabilityAndHotel(Long id, String availability) {
        return roomRepository.findByHotelIdAndRoomAvailability(id,availability);
    }

    @Override
    public String updateAvailability(Long id, Long roomNo, String availability) {
        Optional<HotelRoom> checking = roomRepository.findByHotelIdAndRoomNo(id,roomNo);
        if (!checking.isPresent()){
            return "not available Id";
        }
        roomRepository.setAvailability(id,availability,roomNo);
        return "update success";
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
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
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
}
