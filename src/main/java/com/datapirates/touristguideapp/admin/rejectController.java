package com.datapirates.touristguideapp.admin;

import com.datapirates.touristguideapp.entity.Vehicle;
import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.datapirates.touristguideapp.entity.users.Guide;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reject")
@CrossOrigin
public class rejectController {

    @Autowired
    private adminReject adminReject;

    /**Delete By Admin **/

    @DeleteMapping("/driver")
    private String deleteDriver(@RequestParam Long id){
        return adminReject.rejectDriver(id);
    }

    @DeleteMapping("/guide")
    private String deleteGuide(@RequestParam Long id){
        return adminReject.rejectGuide(id);
    }

    @DeleteMapping("/hotelowner")
    private String deleteOwner(@RequestParam Long id){
        return adminReject.rejectHotelOwner(id);
    }

    @DeleteMapping("/hotel")
    private String deleteHotel(@RequestParam Long id){
        return adminReject.rejectHotel(id);
    }

    @DeleteMapping("/vehicle")
    private String deleteVehicle(@RequestParam Long id){
        return adminReject.rejectVehicle(id);
    }

    @DeleteMapping("/room")
    private String deleteRoom(@RequestParam Long id,Long roomNo){
        return adminReject.rejectHotelRoom(id,roomNo);
    }
}
