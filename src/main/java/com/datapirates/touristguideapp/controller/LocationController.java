package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.model.Location;
import com.datapirates.touristguideapp.service.LocationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/locations")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class LocationController {
    private final LocationService locationService;

    // api/v1/locations
    @PostMapping
    public ResponseEntity<Location> saveLocation(@Validated @RequestBody Location location) {
        Location savedLocation = locationService.saveLocation(location);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedLocation.getLocationId()).toUri();

        return ResponseEntity.created(uri).body(savedLocation);
    }

    // api/v1/locations
    @GetMapping
    public ResponseEntity<Map<String, List<Location>>> getAllLocations() {
        Map<String, List<Location>> response = new HashMap<>();
        response.put("locations", locationService.getAllLocations());
        log.info("Get Locations => " + response);
        return ResponseEntity.ok().body(response);
    }

    // api/v1/locations/1
    @GetMapping("/{locationId}")
    public Optional<Location> getLocationById(@PathVariable(name = "locationId") Long id) {
        return locationService.getLocationById(id);
    }

    // api/v1/locations/1
    @PutMapping("/{locationId}")
    public ResponseEntity<String> updateLocation(@PathVariable(name = "locationId") Long id, @RequestBody Location location) {
        return ResponseEntity.ok().body(locationService.updateLocation(id, location));
    }

    // api/v1/locations/1
    @DeleteMapping("/{locationId}")
    public ResponseEntity<String> deleteLocation(@PathVariable(name = "locationId") Long id) {
        return ResponseEntity.ok().body(locationService.deleteLocation(id));
    }
}
