package com.datapirates.touristguideapp.service.impl;

import com.datapirates.touristguideapp.dto.responseDto.LocationLocationImageDTO;
import com.datapirates.touristguideapp.entity.location.Location;
import com.datapirates.touristguideapp.service.interfaces.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {
    @Override
    public Location saveLocation(Location location) {
        return null;
    }

    @Override
    public Location updateLocation(Long id, Location location) {
        return null;
    }

    @Override
    public String deleteLocation(Long id) {
        return null;
    }

    @Override
    public LocationLocationImageDTO getLocationById(Long id) {
        return null;
    }

    @Override
    public List<LocationLocationImageDTO> getAllLocations() {
        return null;
    }

//    private final LocationRepository locationRepository;
//
//    @Override
//    public Location saveLocation(Location location) {
//        return locationRepository.save(location);
//    }
//
//    @Override
//    public String updateLocation(Long id, Location location) {
//        Optional<Location> optionalLibrary = locationRepository.findById(id);
//        if (!optionalLibrary.isPresent()) {
//            return "Update Error";
//        }
//        location.setLocationId(optionalLibrary.get().getLocationId());
//        locationRepository.save(location);
//        return "Location Id " + id + " Updated Successfully";
//    }
//
//    @Override
//    public String deleteLocation(Long id) {
//        Optional<Location> optionalLibrary = locationRepository.findById(id);
//        if (!optionalLibrary.isPresent()) {
//            return "Deletion Error";
//        }
//        locationRepository.delete(optionalLibrary.get());
//        return "Location Id " + id + " Deleted Successfully";
//    }
//
//    @Override
//    public Optional<Location> getLocationById(Long id) {
//        return locationRepository.findById(id);
//    }
//
//    @Override
//    public List<Location> getAllLocations() {
//        return locationRepository.findAll();
//    }
}
