package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.dto.requestDto.HotelReqDTO;
import com.datapirates.touristguideapp.dto.requestDto.HotelRoomDto;
import com.datapirates.touristguideapp.dto.responseDto.AvailableHotelDTO;
import com.datapirates.touristguideapp.dto.responseDto.HotelResponseDTO;
import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.hotel.RoomCategory;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import com.datapirates.touristguideapp.service.interfaces.hotelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/hotel")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class HotelController {
    @Autowired
    private hotelService hotelService;

    @Autowired
    private com.datapirates.touristguideapp.admin.adminApprove adminApprove;

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


//    @DeleteMapping("{hotelId}")
//    private String deleteHotel(@PathVariable(name = "hotelId") Long hotelId){
//        return hotelService.deleteHotel(hotelId);
//    }

    @GetMapping("/getAll")
    private List<Hotel> getAllHotels(){
        return adminApprove.getHotelByAdmin("confirm");
    }
    @GetMapping("/getHotel")
    private Optional<Hotel> getHotel(@RequestParam Long id){
        return hotelService.getHotelById(id);
    }

    @GetMapping("/getOwner")
    private Long getOwner(@RequestParam Long id){
        return hotelService.getOwnerId(id);
    }

    @PutMapping("/update/{hotelId}")
    private String updateHotel(@PathVariable(name = "hotelId") Long id,@RequestBody Hotel hotel){
        return hotelService.updateHotel(id,hotel);
    }
    @PutMapping("/updateOwner/{userId}")
    private String updateOwner(@PathVariable(name = "userId") Long userId,HotelOwner hotelOwner){
        hotelService.updateOwner(userId,null);
        return "successfully updated";
    }

//    @PutMapping("updateHotelOwner/{userId}")
//    private HotelOwner updateHotelOwner(@PathVariable(name = "userId") Long userId, HotelOwner hotelOwner){
//        return hotelService.updateHotelOwner(userId, hotelOwner);
//    }

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
        return hotelService.deleteCategory(id);
    }

    /**** HOTEL ROOM ***/

    @GetMapping("/hotelRoom/getAvailableHotels")
    private List<AvailableHotelDTO> getRooms(@RequestParam String type, @RequestParam int amount, @RequestParam String startCount){
        return hotelService.getAvailableHotels(type,amount,startCount);
    }
    @GetMapping("/hotelRoom/getRooms")
    private List<HotelRoom> getAllRooms(){
        return adminApprove.getRoomByAdmin("confirm");
    }

    @PutMapping("/hotelRoom/updateAvailability")
    private String updateRoomAvailability(@RequestParam Long roomId, String availability){
         hotelService.updateAvailability(roomId,availability);
         return "successfully updated";
    }

    @PutMapping("/hotelRoom/updateCategory")
    private String updateCategory(@RequestParam String type){
        hotelService.updateCategoryType(type,null);
        return "successfully updated";
    }

    @GetMapping("/{userId}")
    private List<HotelResponseDTO> getHotelsByAppUserId(@PathVariable(name = "userId") Long userId){
        return hotelService.getHotelsByAppUserId(userId);

    }
    @DeleteMapping ("/deleteHotel")
    private String deleteHotel(@RequestParam Long id){
        return hotelService.deleteHotel(id);

    }
}
