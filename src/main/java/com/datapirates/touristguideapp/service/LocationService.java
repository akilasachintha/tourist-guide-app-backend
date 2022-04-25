package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.entity.location.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    Location saveLocation(Location location);

    String updateLocation(Long id, Location location);

    String deleteLocation(Long id);

    Optional<Location> getLocationById(Long id);

    List<Location> getAllLocations();
}
