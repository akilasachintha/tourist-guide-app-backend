package com.datapirates.touristguideapp.service;

import java.util.List;
import java.util.Optional;

import com.datapirates.touristguideapp.entity.bookings.*;

public interface bookingService {
    List<Booking> getBookingByTourist(Long id);
    Optional<Booking> getBookingByTemporary(Long id);
    List<Booking> getAllBooking();
    Booking saveBooking(Booking booking);
    String updateBooking(Long id,Booking booking);
    void deleteBooking(Long id);
    String updateTemporaryId(Long id,Long id2);
    Optional<Booking> getTemporaryId(Long id);
    String setBookingStatus(Long id,String status);

    /*********temporary interface********/

    String updateGuideState(Long id,String state);
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
    List<TemporaryBooking> checkHotelIsPending(Long id);
    String calculateEndTime(String day,String month,String year,String hour);
    Long hourCount(String day,String month,String year,String hour);

/***guide booking interface***/

    GuideBooking saveGuideBooking(GuideBooking guideBooking);
    Optional<GuideBooking> getBookingIdByGuide(Long id);
    String updateGuideBooking(Long id,GuideBooking guideBooking);
    Optional<GuideBooking> getGuideId(Long id);

    /***driver booking interface***/

    DriverBooking saveDriverBooking(DriverBooking driverBooking);
    Optional<DriverBooking> getBookingIdByDriver(Long id);
    String updateDriverBooking(Long id,DriverBooking driverBooking);
    Optional<DriverBooking> getDriverId(Long id);

    /***Hotel booking interface***/

    HotelBooking saveHotelBooking(HotelBooking hotelBooking);
    Optional<HotelBooking> getBookingIdHotel(Long id);
    String updateHotelBooking(Long id,HotelBooking hotelBooking);
    List<HotelBooking> getHotelId(Long id);

}
