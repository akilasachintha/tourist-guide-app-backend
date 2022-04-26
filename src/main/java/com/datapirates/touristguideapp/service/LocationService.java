package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.dto.responseDto.LocationLocationImageDTO;
import com.datapirates.touristguideapp.entity.location.Location;

import java.util.List;

public interface LocationService {
    Location saveLocation(Location location);

    Location updateLocation(Long id, Location location);

    String deleteLocation(Long id);

    LocationLocationImageDTO getLocationById(Long id);

    List<LocationLocationImageDTO> getAllLocations();
}
