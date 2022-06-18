package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.admin.adminApprove;
import com.datapirates.touristguideapp.dto.requestDto.UserDriverReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.DriverResponseDTO;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.datapirates.touristguideapp.service.interfaces.DriverService;
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

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class DriverController {

    private final DriverService driverService;

    private adminApprove adminApprove;


    @PostMapping("/drivers")
    public ResponseEntity<Driver> saveDriver(@Validated @RequestBody UserDriverReqDTO userDriverReqDTO) {
        Driver savedDriver = driverService.saveDriver(userDriverReqDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDriver.getUserId()).toUri();
        return ResponseEntity.created(uri).body(savedDriver);
    }


    @GetMapping("/drivers")
    public ResponseEntity<Map<String, List<DriverResponseDTO>>> getAllDrivers() {
        Map<String, List<DriverResponseDTO>> response = new HashMap<>();
        response.put("drivers", driverService.getAllDrivers());
        log.info("Get Drivers => " + response);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/driver/getByAvailabilityAndLocation")
    private List<Driver> getGuideByAvailability(@RequestParam String availability, @RequestParam Long id) {
        return driverService.getDriverByAvailabilityAndLocationId(availability, id);
    }

    @PutMapping("/driver/rate")
    private String ratingDriver(@RequestParam Long id, @RequestParam int starCount) {
        return driverService.driverRating(id, starCount);
    }

    @PutMapping("/driver/{userId}")
    public ResponseEntity<Driver> updateDriver(@PathVariable(name = "userId") Long id, @RequestBody Driver driver) {
        return ResponseEntity.ok().body(driverService.updateDriver(id, driver));
    }
}
