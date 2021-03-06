package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.dto.requestDto.*;
import com.datapirates.touristguideapp.entity.EmailBody;
import com.datapirates.touristguideapp.entity.bookings.Booking;
import com.datapirates.touristguideapp.entity.bookings.DriverBooking;
import com.datapirates.touristguideapp.entity.bookings.GuideBooking;
import com.datapirates.touristguideapp.entity.bookings.HotelBooking;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.datapirates.touristguideapp.service.interfaces.bookingService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/booking")
@CrossOrigin
//@Slf4jg
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
    private Long getTemporary(@RequestParam Long id){
        return bookingService.getTemporaryId(id);
    }
    @GetMapping("/getAll")
    private List<Booking> getAll(){
        return bookingService.getAllBooking();
    }
    @GetMapping("/getRatingUsers")
    private List<Long> getByTouristUser(@RequestParam Long id){
        return bookingService.getUsersForRating(id);
    }

    @GetMapping("/getPaymentBooks")
    private List<Booking> getByTouristPayment(@RequestParam Long id){
        return bookingService.getBookingsForPayByTOurist(id);
    }
    @GetMapping("/getRatingHotels")
    private List<Long> getByTouristHotel(@RequestParam Long id){
        return bookingService.getHotelForRating(id);
    }

    @PostMapping("/sendMail")
    private String sendMails(@RequestBody EmailBody email){
           bookingService.mailSender(email.getEmail(), email.getSubject(), email.getBody());
           return "Successfully Sended";
    }

    @GetMapping("/sendMail")
    private String sendMailsBrowser(@RequestParam String email){
        bookingService.mailSender(email,"check","hello");
        return "Successfully Sended";
    }

    @PostMapping("/add")
    private String addBooking(@RequestBody BookingReqDto bookingReqDto){
       return bookingService.saveBooking(bookingReqDto);
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
    @PutMapping("/updateTourist")
    private String updateTourist(@RequestParam Long tourist){
        bookingService.updateTourist(tourist,null);
        return "successfully updated";
    }
    @PutMapping("/cancelBooking")
    private String cancelBooking(@RequestBody BookingId bookingId){
        return bookingService.cancelFullBooking(bookingId.getBookingId());
    }
    @DeleteMapping("/delete")
    private String deleteBooking(@RequestParam Long id){
        bookingService.deleteBooking(id);
        return "Successfully deleted";
    }

    @PostMapping("/getPayment")
    private double getPayment(@RequestBody paymentReqDTO request){
        return bookingService.getTotalAmount(request.getHotelId(), request.getGuideId(), request.getVehicleId(), request.getDayCount(), request.getCategoryType(), request.getRoomCount());
    }

    @GetMapping("/getBookingPay")
    private List<Booking> getShouldpayBooking(@RequestParam Long id){
        return bookingService.getBookingsForPayment(id);
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
    private Long getIdByBooking(@RequestParam Long id){
        return bookingService.getGuideId(id);
    }

    @GetMapping("/guideBooking/confirm")
    private String confirmGuideBooking(@RequestParam Long id){
        return bookingService.confirmGuide(id,"confirm");
    }

    @GetMapping("/guideBooking/reject")
    private String rejectGuideBooking(@RequestParam Long id){
        return bookingService.confirmGuide(id,"notSelect");
    }

    @PutMapping("/guideBooking/update")
    private String updateGuideBooking(@RequestParam Long id , @RequestBody GuideBooking guideBooking){
        return bookingService.updateGuideBooking(id,guideBooking);
    }
    @PutMapping("/guideBooking/cancel")
    private String cancelGuideBooking(@RequestParam Long id){
        return bookingService.cancelSingleBooking(id,"guide");
    }
    @PutMapping("/guideBooking/updateGuide")
    private String updateGuide(@RequestParam Long guide){
           bookingService.updateGuide(guide,null);
           return "successfully updated";
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
    private Long getDriverIdByBooking(@RequestParam Long id){
        return bookingService.getDriverId(id);
    }

    @PutMapping("/driverBooking/update")
    private String updateDriverBooking(@RequestParam Long id , @RequestBody DriverBooking driverBooking){
        return bookingService.updateDriverBooking(id,driverBooking);
    }

    @PutMapping("/driverBooking/cancel")
    private String cancelDriverBooking(@RequestParam Long id){
        return bookingService.cancelSingleBooking(id,"driver");
    }

    @GetMapping("/driverBooking/confirm")
    private String confirmDriverBooking(@RequestParam Long id){
        return bookingService.confirmDriver(id,"confirm");
    }

    @GetMapping("/driverBooking/reject")
    private String rejectDriverBooking(@RequestParam Long id){
        return bookingService.confirmDriver(id,"notSelect");
    }
    @PutMapping("/driverBooking/updateDriver")
    private String updateDriver(@RequestParam Long driver){
        bookingService.updateDriver(driver,null);
        return "successfully updated";
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
    private Long getHotelIdByBooking(@RequestParam Long id){
        return bookingService.getHotelId(id);
    }

    @PutMapping("/hotelBooking/update")
    private String updateHotelBooking(@RequestParam Long id , @RequestBody HotelBooking hotelBooking ){
        return bookingService.updateHotelBooking(id,hotelBooking);
    }

    @PutMapping("/hotelBooking/cancel")
    private String cancelHotelBooking(@RequestParam Long id){
        return bookingService.cancelSingleBooking(id,"hotel");
    }
    @PutMapping("/hotelBooking/updateHotel")
    private String updateHotel(@RequestParam Long hotel ){
        bookingService.updateHotel(hotel,null);
        return "successfully updated";
    }

    @GetMapping("/hotelBooking/confirm")
    private String confirmHotelBooking(@RequestParam Long id){
        return bookingService.confirmHotel(id,"confirm");
    }

    @GetMapping("/hotelBooking/reject")
    private String rejectHotelBooking(@RequestParam Long id){
        return bookingService.confirmHotel(id,"notSelect");
    }
}
