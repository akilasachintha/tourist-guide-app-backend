package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.dto.requestDto.LoginReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.LoginResDTO;
import com.datapirates.touristguideapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.datapirates.touristguideapp.service.hotelService;
import com.datapirates.touristguideapp.entity.hotel.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotel")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class hotelController {
    @Autowired
    private hotelService hotelService;

    @PostMapping("/add")
    private String addHotel(@RequestBody Hotel hotel){
        hotelService.saveHotel(hotel);
        return "successful added";
    }

    @GetMapping("/getAll")
    private List<Hotel> getAllHotels(){
        return hotelService.getAllHotels();
    }
    @GetMapping("/getAll")
    private Optional<Hotel> getHotel(@RequestParam Long id){
        return hotelService.getHotelById(id);
    }

    @GetMapping("/getOwner")
    private Long getOwner(@RequestParam Long id){
        return hotelService.getOwnerId(id);
    }

    @PutMapping("/update")
    private String updateHotel(@RequestParam Long id,@RequestBody Hotel hotel){
        return hotelService.updateHotel(id,hotel);
    }
    @PutMapping("/rating")
    private String rateHotel(@RequestParam Long id,@RequestBody int starCount){
        return hotelService.hotelRating(id,starCount);
    }

    /**** CATEGORY ***/

    @PostMapping("/category/add")
    private String addCat(@RequestBody RoomCategory roomCategory){
        hotelService.saveCategory(roomCategory);
        return "successful added";
    }

    @GetMapping("/category/getAll")
    private List<RoomCategory> getAllCat(){
        return hotelService.getAllCategories();
    }

    @PutMapping("/category/update")
    private String updateCat(@RequestParam String id,@RequestBody RoomCategory roomCategory){
        return hotelService.updateCategory(id,roomCategory);
    }

    @DeleteMapping("/category/delete")
    private String deleteCat(@RequestParam String id){
        hotelService.deleteCategory(id);
        return "successful deleted";
    }

    /**** HOTEL ROOM ***/

    @GetMapping("/hotelRoom/getAvailableRooms")
    private List<HotelRoom> getRooms(@RequestParam Long id,String availability){
        return hotelService.getByAvailabilityAndHotel(id,availability);
    }

    @PutMapping("/hotelRoom/updateAvailability")
    private String updateRoom(@RequestParam Long id,Long roomNo,String availability){
        return hotelService.updateAvailability(id,roomNo,availability);
    }
}
