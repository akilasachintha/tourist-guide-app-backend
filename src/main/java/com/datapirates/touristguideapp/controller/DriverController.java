package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.dto.requestDto.UserDriverReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.DriverResponseDTO;
import com.datapirates.touristguideapp.dto.responseDto.LocationLocationImageDTO;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.datapirates.touristguideapp.service.interfaces.DriverService;
import lombok.AllArgsConstructor;
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
@RequestMapping("/api/v1/user")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class DriverController {

    @Autowired
    private  DriverService driverService;

    @PostMapping("/drivers")
    public ResponseEntity<Driver> saveDriver(@Validated @RequestBody UserDriverReqDTO userDriverReqDTO) {
        Driver savedDriver = driverService.saveDriver(userDriverReqDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDriver.getUserId()).toUri();
        return ResponseEntity.created(uri).body(savedDriver);
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<DriverResponseDTO> getDriverById(@PathVariable(name = "userId") Long userId){
//        DriverResponseDTO driverResponseDTO = driverService.getDriverById(userId);
//        return ResponseEntity.ok().body(driverResponseDTO);
//    }

    @GetMapping("/drivers")
    public ResponseEntity<Map<String, List<DriverResponseDTO>>> getAllDrivers() {
        Map<String, List<DriverResponseDTO>> response = new HashMap<>();
        response.put("drivers", driverService.getAllDrivers());
        log.info("Get Drivers => " + response);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/driver/getByAvailabilityAndLocation")
    private List<Driver> getGuideByAvailability(@RequestParam String availability, @RequestParam Long id){
        return driverService.getDriverByAvailabilityAndLocationId(availability,id);
    }

    @PutMapping("/driver/rate")
    private String ratingDriver(@RequestParam Long id, @RequestParam int starCount){
        return driverService.driverRating(id,starCount);
    }
}
