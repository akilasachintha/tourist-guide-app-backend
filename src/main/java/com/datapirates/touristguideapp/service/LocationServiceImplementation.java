package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.entity.Location;
import com.datapirates.touristguideapp.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImplementation implements LocationService{

    @Autowired
    private final LocationRepository locationRepository;

    public LocationServiceImplementation(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public List<Location> getLocation() {
        return locationRepository.findAll();
    }
}
