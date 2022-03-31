package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.model.user.HotelOwner;
import com.datapirates.touristguideapp.service.interfaces.HotelOwnerService;
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
public class HotelOwnerController {

    private final HotelOwnerService hotelOwnerService;

    @Autowired
    public HotelOwnerController(HotelOwnerService hotelOwnerService) {
        this.hotelOwnerService = hotelOwnerService;
    }

    @PostMapping("/hotelOwner")
    public ResponseEntity<HotelOwner> saveGuide(@Validated @RequestBody HotelOwner hotelOwner){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/hotelOwner/save").toUriString());
        return ResponseEntity.created(uri).body(hotelOwnerService.saveGuide(hotelOwner));
    }

    @GetMapping("/hotelOwner")
    public ResponseEntity<Map<String, List<HotelOwner>>> getHotelOwners(){
        log.info("Get hotelOwners");
        Map<String, List<HotelOwner>> response = new HashMap<>();
        response.put("hotelOwners", hotelOwnerService.getHotelOwners());
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/hotelOwner/update")
    public ResponseEntity<HotelOwner> updateHotelOwner(@RequestBody HotelOwner hotelOwner){
        log.info("Successfully Updated Hotel Owner => " + hotelOwner);
        return ResponseEntity.ok().body(hotelOwnerService.updateHotelOwner(hotelOwner));
    }

    @DeleteMapping("/hotelOwner/delete/{id}")
    public String deleteHotelOwner(@PathVariable Long id){
        log.info("Successfully deleted" + id);
        return hotelOwnerService.deleteHotelOwner(id);
    }
}
