package com.datapirates.touristguideapp.service.implementation;

import com.datapirates.touristguideapp.model.Hotel;
import com.datapirates.touristguideapp.repository.HotelRepository;
import com.datapirates.touristguideapp.service.interfaces.HotelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImplementation implements HotelService {
    private final HotelRepository hotelRepository;

    public HotelServiceImplementation(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel updateHotel(Hotel hotel) {
        Hotel existingHotel = hotelRepository.findById(hotel.getHotelId()).orElse(null);
        assert existingHotel != null;
        existingHotel.setHotelName(hotel.getHotelName());
        existingHotel.setHotelAddress(hotel.getHotelAddress());
        existingHotel.setLatitude(hotel.getLatitude());
        existingHotel.setLongitude(hotel.getLongitude());
        return hotelRepository.save(existingHotel);
    }

    @Override
    public String deleteHotel(Long id) {
        hotelRepository.deleteById(id);
        return "Successfully Deleted " + id;
    }
}
