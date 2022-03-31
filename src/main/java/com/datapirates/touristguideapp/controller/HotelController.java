package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.model.Hotel;
import com.datapirates.touristguideapp.service.interfaces.HotelService;
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
@RequestMapping(path = "/api")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping(path = "/hotel")
    public ResponseEntity<Hotel> saveHotel(@Validated @RequestBody Hotel hotel){
        log.info("Save hotel => " + hotel);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/hotel/save").toUriString());
        return ResponseEntity.created(uri).body(hotelService.saveHotel(hotel));
    }

    @GetMapping("/hotel")
    public ResponseEntity<Map<String, List<Hotel>>> getHotels(){
        log.info("Get hotels");
        Map<String, List<Hotel>> response = new HashMap<>();
        response.put("hotels", hotelService.getHotels());
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/hotel/update")
    public ResponseEntity<Hotel> updateDriver(@RequestBody Hotel hotel){
        log.info("Successfully Updated Hotel => " + hotel);
        return ResponseEntity.ok().body(hotelService.updateHotel(hotel));
    }

    @DeleteMapping("/hotel/delete/{id}")
    public String deleteHotel(@PathVariable Long id){
        log.info("Successfully deleted" + id);
        return hotelService.deleteHotel(id);
    }
}
