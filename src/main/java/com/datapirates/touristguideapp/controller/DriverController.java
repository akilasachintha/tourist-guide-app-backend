package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.dto.requestDto.UserDriverReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.DriverResponseDTO;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.datapirates.touristguideapp.service.DriverService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/v1/drivers")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class DriverController {

    private final DriverService driverService;

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
}
