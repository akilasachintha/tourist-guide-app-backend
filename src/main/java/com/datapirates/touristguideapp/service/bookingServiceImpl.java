package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.entity.bookings.*;
import com.datapirates.touristguideapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class bookingServiceImpl implements bookingService{

    @Autowired
    private bookingRepository bookingRepository;

    @Autowired
    private temporary_bookingRepository temporaryBookingRepository;

    @Autowired
    private guideBookingRepository guideBookingRepository;

    @Autowired
    private driverBookingRepository driverBookingRepository;

    @Autowired
    private hotelBookingRepository hotelBookingRepository;

    @Override
    public List<Booking> getBookingByTourist(Long id) {
        return bookingRepository.findByTourist(id);
    }

    @Override
    public Optional<Booking> getBookingByTemporary(Long id) {
        return bookingRepository.findByRelativeTemporaryId(id);
    }

    @Override
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public String updateBooking(Long id, Booking booking) {
        Optional<Booking> checking = bookingRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        bookingRepository.save(booking);
        return "update success";
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public String updateTemporaryId(Long id, Long id2) {
        Optional<Booking> checking = bookingRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        bookingRepository.setTempId(id,id2);
        return "update success";
    }

    @Override
    public Optional<Booking> getTemporaryId(Long id) {
        return bookingRepository.findTempId(id);
    }

    @Override
    public String setBookingStatus(Long id , String status) {
        Optional<Booking> checking = bookingRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        bookingRepository.setBookingStatus(id, status);
        return "update success";
    }

    @Override
    public void updateTourist(Long tourist, Long id) {
        bookingRepository.setTourist(id,tourist);
    }

    /*****temporary booking methods****/

    @Override
    public String updateGuideState(Long id, String state) {
        Optional<TemporaryBooking> checking = temporaryBookingRepository.findByBookingid(id);
        if (!checking.isPresent()){
            return "Error Id";
        }
        temporaryBookingRepository.setGuideState(id,state);
        return "updated";
    }

    @Override
    public String updateDriverState(Long id, String state) {
        Optional<TemporaryBooking> checking = temporaryBookingRepository.findByBookingid(id);
        if (!checking.isPresent()){
            return "Error Id";
        }
        temporaryBookingRepository.setDriverState(id,state);
        return "updated";
    }

    @Override
    public String updateHotelState(Long id, String state) {
        Optional<TemporaryBooking> checking = temporaryBookingRepository.findByBookingid(id);
        if (!checking.isPresent()){
            return "Error Id";
        }
        temporaryBookingRepository.setHotelState(id,state);
        return "updated";
    }

    @Override
    public String updateGuideEndTime(Long id,String day,String month,String year,String hour) {
        Optional<TemporaryBooking> checking = temporaryBookingRepository.findById(id);
        if (!checking.isPresent()){
            return "Error Id";
        }

        String endTime=calculateEndTime(day,month,year,hour);
        temporaryBookingRepository.setGuideEndTime(id,endTime);
        return "updated";
    }

    @Override
    public String updateDriverEndTime(Long id,String day,String month,String year,String hour) {
        Optional<TemporaryBooking> checking = temporaryBookingRepository.findById(id);
        if (!checking.isPresent()){
            return "Error Id";
        }


        String endTime=calculateEndTime(day,month,year,hour);
        temporaryBookingRepository.setDriverEndTime(id,endTime);
        return "updated";
    }

    @Override
    public String updateHotelEndTime(Long id,String day,String month,String year,String hour) {
        Optional<TemporaryBooking> checking = temporaryBookingRepository.findById(id);
        if (!checking.isPresent()){
            return "Error Id";
        }


        String endTime=calculateEndTime(day,month,year,hour);
        temporaryBookingRepository.setHotelEndTime(id,endTime);
        return "updated";
    }

    @Override
    public String updatePendingGuide(Long id, Long id2) {
        Optional<TemporaryBooking> checking = temporaryBookingRepository.findByBookingid(id);
        if (!checking.isPresent()){
            return "Error Id";
        }
        temporaryBookingRepository.setPendingGuide(id,id2);
        return "updated";
    }

    @Override
    public String updatePendingDriver(Long id, Long id2) {
        Optional<TemporaryBooking> checking = temporaryBookingRepository.findByBookingid(id);
        if (!checking.isPresent()){
            return "Error Id";
        }
        temporaryBookingRepository.setPendingDriver(id,id2);
        return "updated";
    }

    @Override
    public String updatePendingHotel(Long id, Long id2) {
        Optional<TemporaryBooking> checking = temporaryBookingRepository.findByBookingid(id);
        if (!checking.isPresent()){
            return "Error Id";
        }
        temporaryBookingRepository.setPendingHotel(id,id2);
        return "updated";
    }

    @Override
    public void deleteTemporary(Long id) {
        temporaryBookingRepository.deleteById(id);
    }

    @Override
    public TemporaryBooking saveTemporary(TemporaryBooking temporaryBooking) {
        return temporaryBookingRepository.save(temporaryBooking);
    }

    @Override
    public List<TemporaryBooking> getAllTemporary() {
        return temporaryBookingRepository.findAll();
    }

    @Override
    public Optional<TemporaryBooking> checkGuideIsPending(Long id) {
        Optional<TemporaryBooking> checking = temporaryBookingRepository.findByPendingGuide(id);
        if (!checking.isPresent()){
            return null;
        }
        return temporaryBookingRepository.checkPendingGuide(id);
    }


    @Override
    public Optional<TemporaryBooking> checkDriverIsPending(Long id) {
        Optional<TemporaryBooking> checking = temporaryBookingRepository.findByPendingDriver(id);
        if (!checking.isPresent()){
            return null;
        }
        return temporaryBookingRepository.checkPendingDriver(id);
    }

    @Override
    public List<TemporaryBooking> checkHotelIsPending(Long id) {
        Optional<TemporaryBooking> checking = temporaryBookingRepository.findByPendingHotel(id);
        if (!checking.isPresent()){
            return null;
        }
        return temporaryBookingRepository.checkPendingHotel(id);
    }

    @Override
    public String calculateEndTime(String day, String month, String year, String hour) {

        Date date = new Date();
        SimpleDateFormat sdt = new SimpleDateFormat("dd");
        SimpleDateFormat sdt2 = new SimpleDateFormat("MM");
        SimpleDateFormat sdt3 = new SimpleDateFormat("YYYY");
        SimpleDateFormat sdt4 = new SimpleDateFormat("dd/MM/YYYY");
        SimpleDateFormat sdt5 = new SimpleDateFormat("hh");

        String endTime="";

        String sDay= sdt.format(date);
        String sMonth = sdt2.format(date);
        String sYear = sdt3.format(date);
        String sHour = sdt5.format(date);

        Long nowHourCount = hourCount(sDay,sMonth,sYear,sHour);

        if (hour == "2"){
            nowHourCount+=1;
            endTime = Long.toString(nowHourCount);
        }
        else {
            Long bookingHourCount = hourCount(day,month,year,hour);
            Long endHourCount = Math.round((nowHourCount-bookingHourCount)*0.2);
            endTime = Long.toString(endHourCount);
        }

        return endTime;
    }

    @Override
    public Long hourCount(String day, String month, String year, String hour) {
        Long years = Long.parseLong(year);
        Long days = Long.parseLong(day);
        Long months = Long.parseLong(month);
        Long hours = Long.parseLong(hour);
        years-=2022;
        Long hourCount=years*365*24+months*30*24+days*24+hours;
        return hourCount;
    }

    /***guide booking***/

    @Override
    public GuideBooking saveGuideBooking(GuideBooking guideBooking) {
        return guideBookingRepository.save(guideBooking);
    }

    @Override
    public Optional<GuideBooking> getBookingIdByGuide(Long id) {
        return guideBookingRepository.findIdByGuideId(id);
    }

    @Override
    public String updateGuideBooking(Long id, GuideBooking guideBooking) {
        Optional<GuideBooking> checking = guideBookingRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        bookingRepository.save(guideBooking);
        return "update success";
    }

    @Override
    public Optional<GuideBooking> getGuideId(Long id) {
        return guideBookingRepository.findGuideId(id);
    }

    @Override
    public void updateGuide(Long guide, Long id) {
        guideBookingRepository.setGuide(id,guide);
    }

    /***driver booking***/

    @Override
    public DriverBooking saveDriverBooking(DriverBooking driverBooking) {
        return driverBookingRepository.save(driverBooking);
    }

    @Override
    public Optional<DriverBooking> getBookingIdByDriver(Long id) {
        return driverBookingRepository.findIdByDriverId(id);
    }

    @Override
    public String updateDriverBooking(Long id, DriverBooking driverBooking) {
        Optional<DriverBooking> checking = driverBookingRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        bookingRepository.save(driverBooking);
        return "update success";
    }

    @Override
    public Optional<DriverBooking> getDriverId(Long id) {
        return driverBookingRepository.findDriverId(id);
    }

    @Override
    public void updateDriver(Long driver, Long id) {
        driverBookingRepository.setDriver(id,driver);
    }

    /****Hotel Booking****/


    @Override
    public HotelBooking saveHotelBooking(HotelBooking hotelBooking) {
        return hotelBookingRepository.save(hotelBooking);
    }

    @Override
    public Optional<HotelBooking> getBookingIdHotel(Long id) {
        return hotelBookingRepository.findIdByHotelId(id);
    }

    @Override
    public String updateHotelBooking(Long id, HotelBooking hotelBooking) {
        Optional<HotelBooking> checking = hotelBookingRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        bookingRepository.save(hotelBooking);
        return "update success";
    }

    @Override
    public List<HotelBooking> getHotelId(Long id) {
        return hotelBookingRepository.findHotelId(id);
    }

    @Override
    public void updateHotel(Long hotel, Long id) {
        hotelBookingRepository.setHotel(id,hotel);
    }
}
