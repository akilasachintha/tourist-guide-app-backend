package com.datapirates.touristguideapp.service.interfaces;

import com.datapirates.touristguideapp.model.Location;

import java.util.List;

public interface LocationService {
    Location saveLocation(Location location);

    List<Location> getLocations();

    Location updateLocation(Location location);

    Object deleteLocation(Long id);
}
