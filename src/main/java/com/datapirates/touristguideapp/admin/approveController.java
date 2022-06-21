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
@RequestMapping("/api/v1/approve")
@CrossOrigin
public class approveController {

    @Autowired
    private adminApprove adminApprove;

    /**
     * GET PENDINGS
     **/

    @GetMapping("/get/pending/driver")
    private List<Driver> getPendingGuide() {
        return adminApprove.getDriverByAdmin("pending");
    }

    @GetMapping("/get/pending/guide")
    private List<Guide> getPendingGide() {
        return adminApprove.getGuideByAdmin("pending");
    }

    @GetMapping("/get/pending/hotel")
    private List<Hotel> getPendingHotel() {
        return adminApprove.getHotelByAdmin("pending");
    }

    @GetMapping("/get/pending/hotelroom")
    private List<HotelRoom> getPendingHotelRoom() {
        return adminApprove.getRoomByAdmin("pending");
    }

    @GetMapping("/get/pending/hotelowner")
    private List<HotelOwner> getPendingHotelOwner() {
        return adminApprove.getHotelOwnerByAdmin("pending");
    }

    @GetMapping("/get/pending/vehicle")
    private List<Vehicle> getPendingVehicle() {
        return adminApprove.getVehicleByAdmin("pending");
    }

    /*** CONFIRM ***/

    @PutMapping("/guide/{userId}")
    private String confirmGuide(@PathVariable(name = "userId") Long id) {
        return adminApprove.approveGuide(id);
    }

    @PutMapping("/driver/{userId}")
    private String confirmDriver(@PathVariable(name = "userId") Long id) {
        return adminApprove.approveDriver(id);
    }

    @PutMapping("/hotel/{hotelId}")
    private String confirmHotel(@PathVariable(name = "hotelId") Long id) {
        return adminApprove.approveHotel(id);
    }

    @PutMapping("/vehicle/{vehicleId}")
    private String confirmVehicle(@PathVariable(name = "vehicleId") Long id) {
        return adminApprove.approveVehicle(id);
    }

    @PutMapping("/hotelowner/{userId}")
    private String confirmHotelOwner(@PathVariable(name = "userId") Long id) {
        return adminApprove.approveHotelOwner(id);
    }

    @PutMapping("/room/{id}")
    private String confirmRoom(@PathVariable Long id) {
        return adminApprove.approveHotelRoom(id);
    }

}
