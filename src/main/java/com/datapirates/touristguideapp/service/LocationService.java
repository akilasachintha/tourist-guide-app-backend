package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.entity.Location;

import java.util.List;

public interface LocationService {

    Location saveLocation(Location location);

    List<Location> getLocation();
}
