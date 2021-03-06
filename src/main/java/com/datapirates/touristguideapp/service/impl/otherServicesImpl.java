package com.datapirates.touristguideapp.service.impl;

import com.datapirates.touristguideapp.entity.bookings.Booking;
import com.datapirates.touristguideapp.entity.bookings.TemporaryBooking;
import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import com.datapirates.touristguideapp.entity.users.Tourist;
import com.datapirates.touristguideapp.service.interfaces.otherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.datapirates.touristguideapp.repository.*;

import java.util.*;
import java.text.SimpleDateFormat;

@Service
public class otherServicesImpl implements otherServices {

    @Autowired
    private com.datapirates.touristguideapp.service.interfaces.bookingService bookingService;

    @Autowired
    private bookingRepository bookingRepository;

    @Autowired
    private hotelBookingRepository hotelBookingRepository;
    @Autowired
    private driverBookingRepository driverBookingRepository;
    @Autowired
    private guideBookingRepository guideBookingRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired hotelOwnerRepository hotelOwnerRepository;
    @Autowired
    private hotelRepository hotelRepository;
    @Autowired
    private temporary_bookingRepository temporaryBookingRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private guideRepository guideRepository;

    @Autowired touristRepository touristRepository;

    @Autowired
    private com.datapirates.touristguideapp.service.interfaces.hotelService hotelService;

    @Autowired
    private JavaMailSender javaMailSender;

    private void sendMails(String toEmail, String subject, String body) {
        SimpleMailMessage massage = new SimpleMailMessage();
        massage.setFrom("subath.abeysekara@gmail.com");
        massage.setTo(toEmail);
        massage.setSubject(subject);
        massage.setText(body);

        javaMailSender.send(massage);
    }

    @Override
    public boolean compareStatus(String state1, String state2, String state3) {
        String statusType1 = "shouldSelect";
        String statusType2 = "pending";
       if(state1!=null&&state2!=null&&state3!=null){
           if (state1.equalsIgnoreCase(statusType1)|| state2.equalsIgnoreCase(statusType1)|| state3.equalsIgnoreCase(statusType1)){
               return false;
           }
           else {
               if (state1.equalsIgnoreCase(statusType2)|| state2.equalsIgnoreCase(statusType2)|| state3.equalsIgnoreCase(statusType2)){
                   return false;
               }
           }
       }
        return true;
    }

    @Override
    public boolean compareStatus2(String state1, String state2, String state3) {
        String statusType1 = "notSelect";
        String statusType2 = "confirm";
        if(state1!=null&&state2!=null&&state3!=null){
            return !(state1.equalsIgnoreCase(statusType1) && state2.equalsIgnoreCase(statusType1) && state3.equalsIgnoreCase(statusType1));
        }
        return true;
    }

    private Long getOwnerId(Long hotelId){
        List<HotelOwner> hotelOwners = hotelOwnerRepository.findAll();
        Long ownerId = null;
        for (HotelOwner hotelOwner : hotelOwners){
            Set<Hotel> hotels = hotelOwner.getHotels();
            for (Hotel hotel : hotels){
                if (Objects.equals(hotel.getHotelId(), hotelId)){
                    ownerId = hotelOwner.getUserId();
                    break;
                }
            }
        }

        return ownerId;
    }

    private Long getTourist(Long bookingId){
        Long touristId = null;
        List<Tourist> tourists = touristRepository.findAll();
        for (Tourist tourist : tourists){
            List<Booking> bookings = tourist.getBookings();
            for (Booking booking : bookings){
                if (Objects.equals(booking.getBookingId(),bookingId)){
                    touristId = tourist.getUserId();
                    break;
                }
            }
        }
        return touristId;
    }

    private Long tempId(Long bookingId){
        Long tempId = null;
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isPresent()){
            Booking booking1 = booking.get();
            Set<TemporaryBooking> temporaryBookings = booking1.getTemporaryBookings();
            for (TemporaryBooking temporaryBooking : temporaryBookings){
                tempId = temporaryBooking.getTempBookingId();
            }
        }

        return tempId;
    }

    private void setTempId(){
        List<Booking> bookings = bookingRepository.findAll();
        for (Booking booking : bookings){
            if (booking.getRelativeTemporaryId()==null&&booking.getTemporaryBookings()!=null){
                Long tempId = tempId(booking.getBookingId());
                bookingRepository.setTempId(booking.getBookingId(),tempId);
            }
        }
    }

    private void checkPendingTime(){
        setTempId();
        List<Long> pendingHotelIds;
        pendingHotelIds = temporaryBookingRepository.getIdsByHotel("pending");
        for (int i=0;i<pendingHotelIds.size(); i++){
            Long temId = pendingHotelIds.get(i);
            String endTime = temporaryBookingRepository.getEndTimeHotel(temId);

            Date date = new Date();
            SimpleDateFormat sdt = new SimpleDateFormat("dd");
            SimpleDateFormat sdt2 = new SimpleDateFormat("MM");
            SimpleDateFormat sdt3 = new SimpleDateFormat("YYYY");
            SimpleDateFormat sdt5 = new SimpleDateFormat("hh");

            String day= sdt.format(date);
            String month = sdt2.format(date);
            String year = sdt3.format(date);
            String hour = sdt5.format(date);

            Long nowTimeCount = bookingService.hourCount(day,month,year,hour);
            Long endTimeCount = Long.parseLong(endTime);

            if (endTimeCount<=nowTimeCount){
                temporaryBookingRepository.setHotelState(temId,"shouldSelect");
                Long hotelId = temporaryBookingRepository.getPendingHotel(temId);
                temporaryBookingRepository.setPendingHotel(temId,null);
                Long bookingId = bookingRepository.getTBookingId(temId);
                //hotelBookingRepository.deleteById(bookingId);
                bookingRepository.setHotel(bookingId,null);
                Long touristId = getTourist(bookingId);
                String touristEmail = userRepository.getEmail(touristId);
                String subject = "should book hotel again";
                String body = "something";
                sendMails(touristEmail,subject,body);
                Long hotelOwnerId = getOwnerId(hotelId);
                String ownerEmail = userRepository.getEmail(hotelOwnerId);
                subject = "your time has over booking is canceled";
                body = "something";
                sendMails(ownerEmail,subject,body);
                // hotelService.updateRoomsAvailability(bookingId,"available",hotelId);


            }

        }
        List<Long> pendingDriverIds = temporaryBookingRepository.getIdsByDriver("pending");
        for (int i=0;i<pendingDriverIds.size(); i++){
            Long temId = pendingDriverIds.get(i);
            String endTime = temporaryBookingRepository.getEndTimeDriver(temId);

            Date date = new Date();
            SimpleDateFormat sdt = new SimpleDateFormat("dd");
            SimpleDateFormat sdt2 = new SimpleDateFormat("MM");
            SimpleDateFormat sdt3 = new SimpleDateFormat("YYYY");
            SimpleDateFormat sdt5 = new SimpleDateFormat("hh");

            String day= sdt.format(date);
            String month = sdt2.format(date);
            String year = sdt3.format(date);
            String hour = sdt5.format(date);

            Long nowTimeCount = bookingService.hourCount(day,month,year,hour);
            Long endTimeCount = Long.parseLong(endTime);

            if (endTimeCount<=nowTimeCount){
                temporaryBookingRepository.setDriverState(temId,"shouldSelect");
                Long driverId = temporaryBookingRepository.getPendingDriver(temId);
                temporaryBookingRepository.setPendingDriver(temId,null);
                Long bookingId = bookingRepository.getTBookingId(temId);
                //driverBookingRepository.deleteById(bookingId);
                bookingRepository.setDriver(bookingId,null);
                Long touristId = getTourist(bookingId);
                String touristEmail = userRepository.getEmail(touristId);
                String subject = "should book driver again";
                String body = "something";
                sendMails(touristEmail,subject,body);
                String driverEmail = userRepository.getEmail(driverId);
                driverRepository.setAvailability(driverId,"available");
                subject = "your time has over booking is canceled";
                body = "something";
                sendMails(driverEmail,subject,body);

            }
        }
        List<Long> pendingGuideIds = temporaryBookingRepository.getIdsByGuide("pending");
        for (int i=0; i<pendingGuideIds.size(); i++){
            Long temId = pendingGuideIds.get(i);
            String endTime = temporaryBookingRepository.getEndTimeGuide(temId);

            Date date = new Date();
            SimpleDateFormat sdt = new SimpleDateFormat("dd");
            SimpleDateFormat sdt2 = new SimpleDateFormat("MM");
            SimpleDateFormat sdt3 = new SimpleDateFormat("YYYY");
            SimpleDateFormat sdt5 = new SimpleDateFormat("hh");

            String day= sdt.format(date);
            String month = sdt2.format(date);
            String year = sdt3.format(date);
            String hour = sdt5.format(date);

            Long nowTimeCount = bookingService.hourCount(day,month,year,hour);
            Long endTimeCount = Long.parseLong(endTime);

            if (endTimeCount<=nowTimeCount){
                temporaryBookingRepository.setGuideState(temId,"shouldSelect");
                Long guideId = temporaryBookingRepository.getPendingGuide(temId);
                temporaryBookingRepository.setPendingGuide(temId,null);
                Long bookingId = bookingRepository.getTBookingId(temId);
                //driverBookingRepository.deleteById(bookingId);
                bookingRepository.setGuide(bookingId,null);
                Long touristId = getTourist(bookingId);
                String touristEmail = userRepository.getEmail(touristId);
                String subject = "should book guide again";
                String body = "something";
                sendMails(touristEmail,subject,body);
                String guideEmail = userRepository.getEmail(guideId);
                guideRepository.setAvailability(guideId,"available");
                subject = "your time has over booking is canceled";
                body = "something";
                sendMails(guideEmail,subject,body);

            }
        }
    }

    private void StatusUpdate(){
        List<Booking> bookings = bookingRepository.findAll();
        for (Booking booking : bookings){

            //System.out.println("hello");
            if (booking.getTemporaryBookings()==null){
                continue;
            }
            Set<TemporaryBooking> temporaryBookings = booking.getTemporaryBookings();
           // System.out.println(tourist1.getBookings());/*
            for (TemporaryBooking temporaryBooking : temporaryBookings){
                String guideState = temporaryBookingRepository.getGuideStatus(temporaryBooking.getTempBookingId());
                String driverState = temporaryBookingRepository.getDriverStatus(temporaryBooking.getTempBookingId());
                String hotelState = temporaryBookingRepository.getHotelStatus(temporaryBooking.getTempBookingId());

                boolean bookingProcessOver = compareStatus(guideState,driverState,hotelState);
                boolean bookingConfirm = compareStatus2(guideState,driverState,hotelState);

                if(bookingProcessOver){
                    if (bookingConfirm){
                        bookingRepository.setBookingStatus(booking.getBookingId(),"shouldPay");
                        temporaryBookingRepository.deleteTemBooking(temporaryBooking.getTempBookingId());
                        Long touristId = getTourist(booking.getBookingId());
                        String touristEmail = userRepository.getEmail(touristId);
                        String subject = "Latest update About your booking";
                        String body = "Your Booking"+booking.getBookingId()+"is confirmed";
                        sendMails(touristEmail,subject,body);
                    }
                    else {
                        Long touristId = getTourist(booking.getBookingId());
                        temporaryBookingRepository.deleteTemBooking(temporaryBooking.getTempBookingId());
                        bookingRepository.deleteBooking(booking.getBookingId());
                        String touristEmail = userRepository.getEmail(touristId);
                        String subject = "Latest update About your booking";
                        String body = "Your Booking"+booking.getBookingId()+"is canceled.You have to do new booking";
                        sendMails(touristEmail,subject,body);
                    }
                }
            }

        }
    }
    @Scheduled(fixedRate = 10000L)
    void checkTimeLimit(){


        /*** booking confirmation**/

        StatusUpdate();

        /*** check rating time ***/
        List<Booking> bookings1 = bookingRepository.findByBookingStatus("paid");
        for (Booking booking : bookings1){
            String checkOutTime = booking.getCheckOutDate();

            Date date = new Date();
            SimpleDateFormat sdt = new SimpleDateFormat("dd");
            SimpleDateFormat sdt2 = new SimpleDateFormat("MM");
            SimpleDateFormat sdt3 = new SimpleDateFormat("YYYY");
            SimpleDateFormat sdt5 = new SimpleDateFormat("hh");

            String day= sdt.format(date);
            String month = sdt2.format(date);
            String year = sdt3.format(date);
            String hour = sdt5.format(date);

            Long nowTimeCount = bookingService.hourCount(day,month,year,hour);
            Long checkOutTimeCount = Long.parseLong(checkOutTime);

            if (checkOutTimeCount<=nowTimeCount){
                bookingRepository.setBookingStatus(booking.getBookingId(),"shouldRate");
            }
        }
    }
}
