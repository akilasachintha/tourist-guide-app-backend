package com.datapirates.touristguideapp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reject")
@CrossOrigin
public class rejectController {

    @Autowired
    private adminReject adminReject;

    /**
     * Delete By Admin
     **/

    @DeleteMapping("driver/{userId}")
    private String deleteDriver(@PathVariable(name = "userId") Long id) {
        return adminReject.rejectDriver(id);
    }

    @DeleteMapping("/guide/{userId}")
    private String deleteGuide(@PathVariable(name = "userId") Long id) {
        return adminReject.rejectGuide(id);
    }

    @DeleteMapping("/hotelowner")
    private String deleteOwner(@RequestParam Long id) {
        return adminReject.rejectHotelOwner(id);
    }

    @DeleteMapping("/hotel")
    private String deleteHotel(@RequestParam Long id) {
        return adminReject.rejectHotel(id);
    }

    @DeleteMapping("/vehicle/{vehicleId}")
    private String deleteVehicle(@PathVariable(name = "vehicleId") Long id) {
        return adminReject.rejectVehicle(id);
    }

    @DeleteMapping("/room")
    private String deleteRoom(@RequestParam Long id, Long roomNo) {
        return adminReject.rejectHotelRoom(id, roomNo);
    }
}
