package com.datapirates.touristguideapp.service.implementation;

import com.datapirates.touristguideapp.model.Location;
import com.datapirates.touristguideapp.repository.LocationRepository;
import com.datapirates.touristguideapp.service.interfaces.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImplementation implements LocationService {
    private final LocationRepository locationRepository;

    public LocationServiceImplementation(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location updateLocation(Location location) {
        Location existingLocation = locationRepository.findById(location.getLocationId()).orElse(null);
        assert existingLocation != null;
        existingLocation.setLocationName(location.getLocationName());
        existingLocation.setDistrict(location.getDistrict());
        existingLocation.setTown(location.getTown());
        existingLocation.setCategory(location.getCategory());
        existingLocation.setDescription(location.getDescription());
        existingLocation.setLatitude(location.getLatitude());
        existingLocation.setLongitude(location.getLongitude());

        return locationRepository.save(existingLocation);
    }

    @Override
    public String deleteLocation(Long id) {
        locationRepository.deleteById(id);
        return "Location Deleted Successfully " + id;
    }
}
