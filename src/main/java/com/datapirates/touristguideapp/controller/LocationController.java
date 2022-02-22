package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.entity.Location;
import com.datapirates.touristguideapp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class LocationController {

    @Autowired
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/api/location")
    public Location saveLocation(@Validated @RequestBody Location location){
        return locationService.saveLocation(location);
    }

    @GetMapping("/api/location")
    public List<Location> getLocation(){
        return locationService.getLocation();
    }
}
