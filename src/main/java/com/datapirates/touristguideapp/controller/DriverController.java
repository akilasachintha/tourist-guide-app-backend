package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.model.user.Driver;
import com.datapirates.touristguideapp.service.interfaces.DriverService;
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
@Slf4j
@RequestMapping("/api")
public class DriverController {
    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/driver")
    public ResponseEntity<Driver> saveDriver(@Validated @RequestBody Driver driver){
        log.info("Save Driver => " + driver);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/driver/save").toUriString());
        return ResponseEntity.created(uri).body(driverService.saveDriver(driver));
    }

    @GetMapping("/driver")
    public ResponseEntity<Map<String, List<Driver>>> getDrivers(){
        log.info("Get drivers");
        Map<String, List<Driver>> response = new HashMap<>();
        response.put("drivers", driverService.getDrivers());
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/driver/update")
    public ResponseEntity<Driver> updateDriver(@RequestBody Driver driver){
        log.info("Successfully Updated Driver => " + driver);
        return ResponseEntity.ok().body(driverService.updateDriver(driver));
    }

    @DeleteMapping("/driver/delete/{id}")
    public String deleteDriver(@PathVariable Long id){
        log.info("Successfully deleted" + id);
        return driverService.deleteDriver(id);
    }
}
