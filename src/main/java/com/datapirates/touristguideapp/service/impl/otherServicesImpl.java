package com.datapirates.touristguideapp.service.impl;

import com.datapirates.touristguideapp.service.interfaces.otherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.datapirates.touristguideapp.repository.*;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    @Autowired
    private hotelRepository hotelRepository;
    @Autowired
    private temporary_bookingRepository temporaryBookingRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private guideRepository guideRepository;

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
           if (state1.toLowerCase().equals(statusType1.toLowerCase())||state2.toLowerCase().equals(statusType1.toLowerCase())||state3.toLowerCase().equals(statusType1.toLowerCase())){
               return false;
           }
           else {
               if (state1.toLowerCase().equals(statusType2.toLowerCase())||state2.toLowerCase().equals(statusType2.toLowerCase())||state3.toLowerCase().equals(statusType2.toLowerCase())){
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
            return !state1.toLowerCase().equals(statusType1.toLowerCase()) && !state2.toLowerCase().equals(statusType1.toLowerCase()) && !state3.toLowerCase().equals(statusType1.toLowerCase());
        }
        return true;
    }

    @Scheduled(fixedRate = 10000L)
    void checkTimeLimit(){
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
                hotelBookingRepository.deleteById(bookingId);
                Long touristId = bookingRepository.getTouristId(bookingId);
                String touristEmail = userRepository.getEmail(touristId);
                String subject = "should book hotel again";
                String body = "something";
                sendMails(touristEmail,subject,body);
                Long hotelOwnerId = hotelRepository.getOwnerId(hotelId);
                String ownerEmail = userRepository.getEmail(hotelOwnerId);
                subject = "your time has over booking is canceled";
                body = "something";
                sendMails(ownerEmail,subject,body);
                hotelService.updateRoomsAvailability(bookingId,"available",hotelId);

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
                driverBookingRepository.deleteById(bookingId);
                Long touristId = bookingRepository.getTouristId(bookingId);
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
                driverBookingRepository.deleteById(bookingId);
                Long touristId = bookingRepository.getTouristId(bookingId);
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

        /*** booking confirmation**/

        List<Long> allTemporaryIds = temporaryBookingRepository.getAllTempIds();
        for (int index=0;index<allTemporaryIds.size();index++){
            Long temporaryId = allTemporaryIds.get(index);
            String guideState = temporaryBookingRepository.getGuideStatus(temporaryId);
            String driverState = temporaryBookingRepository.getDriverStatus(temporaryId);
            String hotelState = temporaryBookingRepository.getHotelStatus(temporaryId);

            boolean bookingProcessOver = compareStatus(guideState,driverState,hotelState);
            boolean bookingConfirm = compareStatus2(guideState,driverState,hotelState);

            if(!bookingProcessOver){
                if (bookingConfirm){
                    Long bookingId = bookingRepository.getTBookingId(temporaryId);
                    bookingRepository.setBookingStatus(bookingId,"shouldPay");
                    temporaryBookingRepository.deleteById(temporaryId);
                    Long touristId = bookingRepository.getTouristId(bookingId);
                    String touristEmail = userRepository.getEmail(touristId);
                    String subject = "Latest update About your booking";
                    String body = "Your Booking"+bookingId+"is confirmed";
                    sendMails(touristEmail,subject,body);
                }
                else {
                    Long bookingId = bookingRepository.getTBookingId(temporaryId);
                    Long touristId = bookingRepository.getTouristId(bookingId);
                    bookingRepository.deleteById(bookingId);
                    String touristEmail = userRepository.getEmail(touristId);
                    String subject = "Latest update About your booking";
                    String body = "Your Booking"+bookingId+"is canceled.You have to do new booking";
                    sendMails(touristEmail,subject,body);
                }
            }
        }

        /*** check rating time ***/
        List<Long> bookingIdsOnPayed = bookingRepository.getAllBookingIdsByState("paid");
        for (int index=0; index<bookingIdsOnPayed.size();index++){
            Long bookingId = bookingIdsOnPayed.get(index);
            String checkOutTime = bookingRepository.getCheckOutDateById(bookingId);

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
                bookingRepository.setBookingStatus(bookingId,"shouldRate");
            }
        }
    }
}
