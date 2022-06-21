package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.entity.bookings.Booking;
import com.datapirates.touristguideapp.entity.bookings.TemporaryBooking;
import com.datapirates.touristguideapp.service.interfaces.bookingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking/temporaryBooking")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class temporaryBookingController {

    /*********temporary booking*******/

   /**String updateGuideState(Long id,String state);
    String updateDriverState(Long id,String state);
    String updateHotelState(Long id,String state);
    String updateGuideEndTime(Long id,String day,String month,String year,String hour);
    String updateDriverEndTime(Long id,String day,String month,String year,String hour);
    String updateHotelEndTime(Long id,String day,String month,String year,String hour);
    String updatePendingGuide(Long id,Long id2);
    String updatePendingDriver(Long id,Long id2);
    String updatePendingHotel(Long id,Long id2);
    void deleteTemporary(Long id);
    TemporaryBooking saveTemporary(TemporaryBooking temporaryBooking);
    List<TemporaryBooking> getAllTemporary();
    Optional<TemporaryBooking> checkGuideIsPending(Long id);
    Optional<TemporaryBooking> checkDriverIsPending(Long id);
    List<TemporaryBooking> checkHotelIsPending(Long id);*/

   @Autowired
    private bookingService bookingService;

   @PostMapping("/add")
   private String addTemporary(@RequestBody TemporaryBooking temporaryBooking){
       bookingService.saveTemporary(temporaryBooking);
       return "Successfully added";
   }
   @GetMapping("/getAll")
   private List<TemporaryBooking> getAllTemporary(){
       return bookingService.getAllTemporary();
   }

   @DeleteMapping("/delete")
   private String deleteTemp(Long id){
       bookingService.deleteTemporary(id);
       return "successfully deleted";
   }

   /** guide temp**/

    @GetMapping("/checkGuideIsPending")
    private Booking checkGuidePending(@RequestParam Long id){
        return bookingService.checkGuideIsPending(id);
    }
   @PutMapping("/updateGuideState")
    private String updateGuideState(@RequestParam Long id,String status){
       return bookingService.updateGuideState(id, status);
   }
    @PutMapping("/updateGuideEndTime")
    private String updateGuideEndTIme(@RequestParam Long id,String day,String month,String year,String hour){
        return bookingService.updateGuideEndTime(id, day, month, year, hour);
    }
    @PutMapping("/updatePendingGuide")
    private String updatePendingGuide(@RequestParam Long id,Long guideId){
        return bookingService.updatePendingGuide(id,guideId);
    }

    /** driver temp **/

    @GetMapping("/checkDriverIsPending")
    private Booking checkDriverPending(@RequestParam Long id){
        return bookingService.checkDriverIsPending(id);
    }
    @PutMapping("/updateDriverState")
    private String updateDriverState(@RequestParam Long id,String status){
        return bookingService.updateDriverState(id, status);
    }
    @PutMapping("/updateDriverEndTime")
    private String updateDriverEndTIme(@RequestParam Long id,String day,String month,String year,String hour){
        return bookingService.updateDriverEndTime(id, day, month, year, hour);
    }
    @PutMapping("/updatePendingDriver")
    private String updatePendingDriver(@RequestParam Long id,Long guideId){
        return bookingService.updatePendingDriver(id,guideId);
    }

    /** hotel temp **/

    @GetMapping("/checkHotelIsPending")
    private List<Booking> checkHotelPending(@RequestParam Long owner){
        return bookingService.checkHotelIsPending(owner);
    }
    @PutMapping("/updateHotelState")
    private String updateHotelState(@RequestParam Long id,String status){
        return bookingService.updateHotelState(id, status);
    }
    @PutMapping("/updateHotelEndTime")
    private String updateHotelEndTIme(@RequestParam Long id,String day,String month,String year,String hour){
        return bookingService.updateHotelEndTime(id, day, month, year, hour);
    }
    @PutMapping("/updatePendingHotel")
    private String updatePendingHotel(@RequestParam Long id,Long guideId){
        return bookingService.updatePendingHotel(id,guideId);
    }
}
