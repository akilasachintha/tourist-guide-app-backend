package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.model.Location;
import com.datapirates.touristguideapp.service.interfaces.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Slf4j
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/location")
    public ResponseEntity<Location> saveLocation(@Validated @RequestBody Location location){
        log.info("Successfully Added location => " + location);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/location/save").toUriString());
        return ResponseEntity.created(uri).body(locationService.saveLocation(location));
    }

    @GetMapping("/location")
    public ResponseEntity<Map<String, List<Location>>> getLocations(){
        Map<String, List<Location>> response = new HashMap<>();
        response.put("locations", locationService.getLocations());
        log.info("Get Locations => " + response);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/location/update")
    public ResponseEntity<Location> updateLocation(@RequestBody Location location){
        log.info("Successfully Updated Location => " + location);
        return ResponseEntity.ok().body(locationService.updateLocation(location));
    }

    @DeleteMapping("/location/delete/{id}")
    public String deleteLocation(@PathVariable Long id){
        return (String) locationService.deleteLocation(id);
    }
}
