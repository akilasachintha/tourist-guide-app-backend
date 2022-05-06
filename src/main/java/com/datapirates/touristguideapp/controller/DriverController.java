package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.dto.requestDto.UserDriverReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.DriverResponseDTO;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.datapirates.touristguideapp.entity.users.Guide;
import com.datapirates.touristguideapp.service.DriverService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/user")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class DriverController {

    @Autowired
    private  DriverService driverService;

    @PostMapping()
    public ResponseEntity<Driver> saveDriver(@Validated @RequestBody UserDriverReqDTO userDriverReqDTO) {

        Driver savedDriver = driverService.saveDriver(userDriverReqDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDriver.getUserId()).toUri();

        return ResponseEntity.created(uri).body(savedDriver);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<DriverResponseDTO> getDriverById(@PathVariable(name = "userId") Long userId){
        DriverResponseDTO driverResponseDTO = driverService.getDriverById(userId);
        return ResponseEntity.ok().body(driverResponseDTO);
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
