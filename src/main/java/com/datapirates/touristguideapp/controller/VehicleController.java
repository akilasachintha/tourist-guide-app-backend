package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.dto.requestDto.VehicleReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.VehicleResDTO;
import com.datapirates.touristguideapp.entity.Vehicle;
import com.datapirates.touristguideapp.service.interfaces.VehicleService;
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
@RequestMapping("/api/v1/vehicles")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    // api/v1/vehicles
   @PostMapping()
    public ResponseEntity<Vehicle> saveVehicle(@Validated @RequestBody VehicleReqDTO vehicleReqDTO) {
        Vehicle savedVehicle = vehicleService.saveVehicle(vehicleReqDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedVehicle.getVehicleNo()).toUri();

        return ResponseEntity.created(uri).body(savedVehicle);
    }

   @GetMapping
    public ResponseEntity<Map<String, List<VehicleResDTO>>> getVehicles() {
        Map<String, List<VehicleResDTO>> response = new HashMap<>();
        response.put("vehicles", vehicleService.getVehicles());
        log.info("Get Vehicles => " + response);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<String> deleteVehicle(@PathVariable(name = "vehicleId") Long id) {
        return ResponseEntity.ok().body(vehicleService.deleteVehicle(id));
    }

    @GetMapping("/{userId}")
    public List<VehicleResDTO> getVehiclesByAppUserId(@PathVariable(name = "userId") Long id) {
        return vehicleService.getVehiclesByAppUserId(id);
    }
}
