package com.datapirates.touristguideapp.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.datapirates.touristguideapp.dto.requestDto.BookingReqDto;
import com.datapirates.touristguideapp.entity.bookings.*;

public interface bookingService {
    List<Booking> getBookingByTourist(Long id);
    Optional<Booking> getBookingByTemporary(Long id);
    List<Booking> getAllBooking();
    String saveBooking(BookingReqDto bookingReqDto);
    String updateBooking(Long id,Booking booking);
    void deleteBooking(Long id);
    String updateTemporaryId(Long id,Long id2);
    Long getTemporaryId(Long id);
    String setBookingStatus(Long id,String status);
    void updateTourist(Long tourist,Long id);
    List<Booking> getBookingByTouristAndState(Long id,String status);
    String cancelFullBooking(Long id);
    String cancelSingleBooking(Long id,String type);
    double getTotalAmount(Long hotelId,Long guideId,Long vehicleId,int dayCount,String categoryType,int roomCount);

    List<Long> getUsersForRating(Long id);

    List<Long> getHotelForRating(Long id);

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
    Booking checkGuideIsPending(Long id);
    Booking checkDriverIsPending(Long id);
    List<Booking> checkHotelIsPending(Long id);
    String calculateEndTime(String day,String month,String year,String hour);
    Long hourCount(String day,String month,String year,String hour);

    List<Booking> getBookingsForPayment(Long id);
/***guide booking interface***/

    GuideBooking saveGuideBooking(GuideBooking guideBooking);
    Optional<GuideBooking> getBookingIdByGuide(Long id);
    String updateGuideBooking(Long id,GuideBooking guideBooking);
    Long getGuideId(Long id);
    void updateGuide(Long guide,Long id);

    /***driver booking interface***/

    DriverBooking saveDriverBooking(DriverBooking driverBooking);
    Optional<DriverBooking> getBookingIdByDriver(Long id);
    String updateDriverBooking(Long id,DriverBooking driverBooking);
   Long getDriverId(Long id);
    void updateDriver(Long driver,Long id);

    /***Hotel booking interface***/

    HotelBooking saveHotelBooking(HotelBooking hotelBooking);
    Optional<HotelBooking> getBookingIdHotel(Long id);
    String updateHotelBooking(Long id,HotelBooking hotelBooking);
   Long getHotelId(Long id);
    void updateHotel(Long hotel,Long id);

    void mailSender(String toEmail, String subject, String body);


}
