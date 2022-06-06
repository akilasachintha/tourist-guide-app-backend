package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.dto.requestDto.HotelReqDTO;
import com.datapirates.touristguideapp.dto.requestDto.HotelRoomDto;
import com.datapirates.touristguideapp.dto.responseDto.DriverResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.datapirates.touristguideapp.service.interfaces.hotelService;
import com.datapirates.touristguideapp.entity.hotel.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/hotel")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class hotelController {
    @Autowired
    private hotelService hotelService;

    @PostMapping("/add")
    private String addHotel(@RequestBody HotelReqDTO hotelReqDTO){
        hotelService.saveHotel(hotelReqDTO);
        return "successful added";
    }

    @PostMapping("/addRoom")
    private String addHotelRoom(@RequestBody HotelRoomDto hotelRoomDto){
        hotelService.saveHotelRoom(hotelRoomDto);
        return "successful added";
    }


    @GetMapping("/getAll")
    private List<Hotel> getAllHotels(){
        return hotelService.getAllHotels();
    }
    @GetMapping("/getHotel")
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
    @PutMapping("/updateOwner")
    private String updateOwner(@RequestParam Long owner){
        hotelService.updateOwner(owner,null);
        return "successfully updated";
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
    @GetMapping("/hotelRoom/getRooms")
    private List<HotelRoom> getAllRooms(){
        return hotelService.getHotelRoom();
    }

    @PutMapping("/hotelRoom/updateAvailability")
    private String updateRoomAvailability(@RequestParam Long hotelBooking, String availability, Long hotelId){
         hotelService.updateRoomsAvailability(hotelBooking,availability,hotelId);
         return "successfully updated";
    }

    @PutMapping("/hotelRoom/updateCategory")
    private String updateCategory(@RequestParam String type){
        hotelService.updateCategoryType(type,null);
        return "successfully updated";
    }
}
