package com.datapirates.touristguideapp.service.implementation;

import com.datapirates.touristguideapp.model.user.HotelOwner;
import com.datapirates.touristguideapp.repository.HotelOwnerRepository;
import com.datapirates.touristguideapp.service.interfaces.HotelOwnerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelOwnerServiceImplementation implements HotelOwnerService {

    private final HotelOwnerRepository hotelOwnerRepository;

    public HotelOwnerServiceImplementation(HotelOwnerRepository hotelOwnerRepository) {
        this.hotelOwnerRepository = hotelOwnerRepository;
    }

    @Override
    public HotelOwner saveGuide(HotelOwner hotelOwner) {
        return hotelOwnerRepository.save(hotelOwner);
    }

    @Override
    public List<HotelOwner> getHotelOwners() {
        return hotelOwnerRepository.findAll();
    }

    @Override
    public HotelOwner updateHotelOwner(HotelOwner hotelOwner) {
        HotelOwner existingHotelOwner = hotelOwnerRepository.findById(hotelOwner.getUserId()).orElse(null);
        assert existingHotelOwner != null;
        existingHotelOwner.setName(hotelOwner.getName());
        existingHotelOwner.setEmail(hotelOwner.getEmail());
        existingHotelOwner.setDob(hotelOwner.getDob());
        existingHotelOwner.setPassword(hotelOwner.getPassword());
        existingHotelOwner.setPhotoUrl(hotelOwner.getPhotoUrl());
        existingHotelOwner.setNic(hotelOwner.getNic());
        return hotelOwnerRepository.save(existingHotelOwner);
    }

    @Override
    public String deleteHotelOwner(Long id) {
        hotelOwnerRepository.deleteById(id);
        return "Successfully Deleted Hotel Owner " + id;
    }
}
