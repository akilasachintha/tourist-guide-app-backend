package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.dto.requestDto.LoginReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.LoginResDTO;
import com.datapirates.touristguideapp.entity.bookings.Booking;
import com.datapirates.touristguideapp.entity.bookings.DriverBooking;
import com.datapirates.touristguideapp.entity.bookings.GuideBooking;
import com.datapirates.touristguideapp.entity.bookings.HotelBooking;
import com.datapirates.touristguideapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.datapirates.touristguideapp.service.bookingService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class BookingController {

    @Autowired
    private bookingService bookingService;

    @GetMapping("/getByTourist")
    private List<Booking> getByTourist(@RequestParam Long id){
        return bookingService.getBookingByTourist(id);
    }
    @GetMapping("/getByTemp")
    private Optional<Booking> getByTemporary(@RequestParam Long id){
        return bookingService.getBookingByTemporary(id);
    }
    @GetMapping("/getTemporary")
    private Optional<Booking> getTemporary(@RequestParam Long id){
        return bookingService.getTemporaryId(id);
    }
    @GetMapping("/getAll")
    private List<Booking> getAll(){
        return bookingService.getAllBooking();
    }

    @PostMapping("/add")
    private String addBooking(@RequestBody Booking booking){
        bookingService.saveBooking(booking);
        return "Successfully added";
    }

    @PutMapping("/update")
    private String updateBooking(@RequestParam Long id , @RequestBody Booking booking){
       return bookingService.updateBooking(id,booking);
    }
    @PutMapping("/updateTempId")
    private String updateTempId(@RequestParam Long id , @RequestParam Long tempId){
        return bookingService.updateTemporaryId(id,tempId);
    }
    @PutMapping("/updateStatus")
    private String updateStatus(@RequestParam Long id , @RequestParam String status){
        return bookingService.setBookingStatus(id,status);
    }
    @DeleteMapping("/delete")
    private String deleteBooking(@RequestParam Long id){
        bookingService.deleteBooking(id);
        return "Successfully deleted";
    }

    /*** Guide Booking ***/

    @PostMapping("/guideBooking/add")
    private String addGuideBooking(@RequestBody GuideBooking guideBooking){
        bookingService.saveGuideBooking(guideBooking);
        return "Successfully added";
    }

    @GetMapping("/guideBooking/getBookingId")
    private Optional<GuideBooking> getBookingId(@RequestParam Long id){
        return bookingService.getBookingIdByGuide(id);
    }
    @GetMapping("/guideBooking/getId")
    private Optional<GuideBooking> getIdByBooking(@RequestParam Long id){
        return bookingService.getGuideId(id);
    }

    @PutMapping("/guideBooking/update")
    private String updateGuideBooking(@RequestParam Long id , @RequestBody GuideBooking guideBooking){
        return bookingService.updateGuideBooking(id,guideBooking);
    }

    /*** Driver Booking ***/

    @PostMapping("/driverBooking/add")
    private String addDriverBooking(@RequestBody DriverBooking driverBooking){
        bookingService.saveDriverBooking(driverBooking);
        return "Successfully added";
    }

    @GetMapping("/driverBooking/getBookingId")
    private Optional<DriverBooking> getBookingIdByDriver(@RequestParam Long id){
        return bookingService.getBookingIdByDriver(id);
    }
    @GetMapping("/driverBooking/getId")
    private Optional<DriverBooking> getDriverIdByBooking(@RequestParam Long id){
        return bookingService.getDriverId(id);
    }

    @PutMapping("/driverBooking/update")
    private String updateDriverBooking(@RequestParam Long id , @RequestBody DriverBooking driverBooking){
        return bookingService.updateDriverBooking(id,driverBooking);
    }

    /*** Hotel Booking ***/


    @PostMapping("/hotelBooking/add")
    private String addHotelBooking(@RequestBody HotelBooking hotelBooking){
        bookingService.saveHotelBooking(hotelBooking);
        return "Successfully added";
    }

    @GetMapping("/hotelBooking/getBookingId")
    private Optional<HotelBooking> getBookingIdByHotel(@RequestParam Long id){
        return bookingService.getBookingIdHotel(id);
    }
    @GetMapping("/hotelBooking/getId")
    private List<HotelBooking> getHotelIdByBooking(@RequestParam Long id){
        return bookingService.getHotelId(id);
    }

    @PutMapping("/hotelBooking/update")
    private String updateHotelBooking(@RequestParam Long id , @RequestBody HotelBooking hotelBooking ){
        return bookingService.updateHotelBooking(id,hotelBooking);
    }
}
