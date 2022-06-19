package com.datapirates.touristguideapp.service.impl;

import com.datapirates.touristguideapp.dto.requestDto.BookingReqDto;
import com.datapirates.touristguideapp.dto.requestDto.TimeReqDto;
import com.datapirates.touristguideapp.entity.Vehicle;
import com.datapirates.touristguideapp.entity.bookings.*;
import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.hotel.RoomCategory;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.datapirates.touristguideapp.entity.users.Guide;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import com.datapirates.touristguideapp.entity.users.Tourist;
import com.datapirates.touristguideapp.repository.exception.ResourceNotFoundException;
import com.datapirates.touristguideapp.repository.*;
import com.datapirates.touristguideapp.service.interfaces.bookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class bookingServiceImpl implements bookingService {

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

    @Autowired
    private hotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private guideRepository guideRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired hotelOwnerRepository hotelOwnerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private roomRepository roomRepository;
    @Autowired
    private touristRepository touristRepository;
    @Autowired
    private JavaMailSender javaMailSender;


    private void sendMails(String toEmail, String subject, String body) {
        SimpleMailMessage massage = new SimpleMailMessage();
        massage.setFrom("travelmateapp2022@gmail.com");
        massage.setTo(toEmail);
        massage.setSubject(subject);
        massage.setText(body);

        javaMailSender.send(massage);
    }

    @Override
    public List<Booking> getBookingByTourist(Long id) {
        Tourist tourist = touristRepository.getById(id);
        return tourist.getBookings();
    }

    @Override
    public Optional<Booking> getBookingByTemporary(Long id) {
        return bookingRepository.findByRelativeTemporaryId(id);
    }

    @Override
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

    private double calculateTotalAmount(Long hotelId,Long guideId,Long vehicleId,int dayCount,String categoryType,int roomCount){
        double total=0.0;
        if (guideId!=null){
            Optional<Guide> guide = guideRepository.findById(guideId);
            if (guide.isPresent()){
                Guide guide1 = guide.get();
                total+=guide1.getPriceRange();
            }
        }
        if (vehicleId!=null){
            Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
            if (vehicle.isPresent()){
                Vehicle vehicle1 = vehicle.get();
                total+=(vehicle1.getPriceForDay()*dayCount);
            }
        }
        if (hotelId!=null){
            Hotel hotel = hotelRepository.getById(hotelId);
            Set<HotelRoom> hotelRooms = hotel.getHotelRooms();
            for (HotelRoom hotelRoom : hotelRooms){
                RoomCategory roomCategory = hotelRoom.getRoomCategory();
                if(roomCategory.getCategoryType().equals(categoryType)){
                    total+=(hotelRoom.getPrice()*dayCount*roomCount);
                }
            }

        }
        return total;
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


    @Override
    public String saveBooking(BookingReqDto bookingReqDto) {
        Hotel hotel = hotelRepository.getById(bookingReqDto.getHotel());
            Set<HotelRoom> hotelRooms = hotel.getHotelRooms();
            if (hotelRooms.isEmpty()){
                //hotels.remove(hotel);
                return "No Rooms";
            }

            int count=0;
            for (HotelRoom hotelRoom : hotelRooms){
                RoomCategory roomCategory = hotelRoom.getRoomCategory();
                if(roomCategory.getCategoryType().equals(bookingReqDto.getCategoryType())){
                    count++;
                }
            }

            if (count<bookingReqDto.getRoomCount()){
                //hotels.remove(hotel);
                return "No enough rooms";
            }

        if (bookingReqDto.getGuide()!=null){
            Guide guide = guideRepository.getById(bookingReqDto.getGuide());
            if (guide.getAvailability().equals("no")){
                return "guide already booked";
            }
        }
        if (bookingReqDto.getDriver()!=null){
            Driver driver = driverRepository.getById(bookingReqDto.getDriver());
            if (driver.getAvailability().equals("no")){
                return "driver already booked";
            }
        }
        convertDtoToEntity(bookingReqDto);
        return "successfully booked";
    }
    private Booking convertDtoToEntity(BookingReqDto bookingReqDto) {
        Booking booking = bookingReqDto.getBooking();
        //booking.setTemporaryBookings(bookingReqDto.getTemporaryBookings())
        booking.setHotelId(bookingReqDto.getHotel());
        booking.setDriverId(bookingReqDto.getDriver());
        booking.setGuideId(bookingReqDto.getGuide());
        booking.setRoomCount(bookingReqDto.getRoomCount());
        booking.setCategoryType(bookingReqDto.getCategoryType());
        if(bookingReqDto.getGuide()!=null){
            sendMails(userRepository.getEmail(bookingReqDto.getGuide()),"Booking","you have a new booking");
        }
        Hotel hotel = hotelRepository.getById(bookingReqDto.getHotel());
        Set<HotelRoom> hotelRooms = hotel.getHotelRooms();
        int count=1;
        for (HotelRoom hotelRoom : hotelRooms){
            if (hotelRoom.getRoomAvailability().equals("no")){
                continue;
            }
            RoomCategory roomCategory = hotelRoom.getRoomCategory();
            if(roomCategory.getCategoryType().equals(bookingReqDto.getCategoryType())){
                roomRepository.setAvailability(hotelRoom.getRoomId(),"no");
                count++;
            }
            if (count>bookingReqDto.getRoomCount()){
                break;
            }
        }

        Tourist existingTourist = touristRepository.findById(bookingReqDto.getUser()).orElseThrow(() ->
                new ResourceNotFoundException("Location", "Id", booking.getBookingId()));
        booking.setTourist(existingTourist);


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
    public Long getTemporaryId(Long id) {
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

    @Override
    public List<Booking> getBookingByTouristAndState(Long id,String status) {
        Tourist tourist = touristRepository.getById(id);
        List<Booking> retBooking = new ArrayList<>();
        List<Booking> booking = tourist.getBookings();
        Booking booking1 = new Booking();

        for (Booking value : booking) {
            booking1 = value;
            if (booking1.getBookingStatus() == null) {
                // booking.remove(i);
                continue;
            }
            if (booking1.getBookingStatus().equals(status)) {
                // booking.remove(i);
                retBooking.add(booking1);
            }
        }
        return retBooking;
    }

    @Override
    public String cancelFullBooking(Long id) {
        Optional<Booking> checking = bookingRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        String bookingState = bookingRepository.getStateById(id);
        if(bookingState.toLowerCase().equals("pending")){
            Long tempID = bookingRepository.findTempId(id);
            String hotelState = temporaryBookingRepository.getHotelStatus(tempID);
            if(!(hotelState.toLowerCase().equals("souldselect")||hotelState.toLowerCase().equals("notselect"))){
                Long hotelId = temporaryBookingRepository.getPendingHotel(tempID);
                Long ownerId = hotelRepository.getOwnerId(hotelId);
                String email = userRepository.getEmail(ownerId);
                String subject="Booking cancel";
                String body="Your Booking has canceled by tourist";
                sendMails(email,subject,body);
            }
            String driverState = temporaryBookingRepository.getDriverStatus(tempID);
            if(!(driverState.toLowerCase().equals("souldselect")||driverState.toLowerCase().equals("notselect"))){
                Long driverId = temporaryBookingRepository.getPendingDriver(tempID);
                String email = userRepository.getEmail(driverId);
                String subject="Booking cancel";
                String body="Your Booking has canceled by tourist";
                sendMails(email,subject,body);
            }
            String guideState = temporaryBookingRepository.getGuideStatus(tempID);
            if(!(guideState.toLowerCase().equals("souldselect")||guideState.toLowerCase().equals("notselect"))){
                Long guideId = temporaryBookingRepository.getPendingGuide(tempID);
                String email = userRepository.getEmail(guideId);
                String subject="Booking cancel";
                String body="Your Booking has canceled by tourist";
                sendMails(email,subject,body);
            }
            Long touristId = bookingRepository.getTouristId(id);
            bookingRepository.deleteById(id);
            String email = userRepository.getEmail(touristId);
            String subject="Booking cancel";
            String body="Your Booking has successfully canceled";
            sendMails(email,subject,body);
        }
        else {
            if(bookingState.toLowerCase().equals("rated")){
                return "ERROR: FinishedBooking";
            }
            Long hotelId = hotelBookingRepository.findHotelId(id);
            if (hotelId!=null){
                Long ownerId = hotelRepository.getOwnerId(hotelId);
                String email = userRepository.getEmail(ownerId);
                String subject="Booking cancel";
                String body="Your Booking has canceled by tourist";
                sendMails(email,subject,body);
            }
            Long driverId = driverBookingRepository.findDriverId(id);
            if (driverId!=null){
                String email = userRepository.getEmail(driverId);
                String subject="Booking cancel";
                String body="Your Booking has canceled by tourist";
                sendMails(email,subject,body);
            }
            Long guideId = guideBookingRepository.findGuideId(id);
            if (guideId!=null){
                String email = userRepository.getEmail(guideId);
                String subject="Booking cancel";
                String body="Your Booking has canceled by tourist";
                sendMails(email,subject,body);
            }
            Long touristId = bookingRepository.getTouristId(id);
            bookingRepository.deleteById(id);
            String email = userRepository.getEmail(touristId);
            String subject="Booking cancel";
            String body="Your Booking has successfully canceled";
            sendMails(email,subject,body);
        }

        return "successfully canceled";
    }

    @Override
    public String cancelSingleBooking(Long id, String type) {

        String bookingState = bookingRepository.getStateById(id);
        if(bookingState.toLowerCase().equals("pending")){
            Long tempID = bookingRepository.findTempId(id);
            if (type.toLowerCase().equals("hotel")){

                String hotelState = temporaryBookingRepository.getHotelStatus(tempID);
                if(!(hotelState.toLowerCase().equals("souldselect")||hotelState.toLowerCase().equals("notselect"))){
                    Long hotelId = temporaryBookingRepository.getPendingHotel(tempID);
                    Long ownerId = hotelRepository.getOwnerId(hotelId);
                    String email = userRepository.getEmail(ownerId);
                    String subject="Booking cancel";
                    String body="Your Booking has canceled by tourist";
                    sendMails(email,subject,body);
                    temporaryBookingRepository.setHotelState(tempID,"notSelected");
                    hotelBookingRepository.deleteById(id);
                }
                else {
                    return "ERROR : Hadn't Book Hotel";
                }
            }

            if (type.toLowerCase().equals("driver")){

                String driverState = temporaryBookingRepository.getDriverStatus(tempID);
                if(!(driverState.toLowerCase().equals("souldselect")||driverState.toLowerCase().equals("notselect"))){
                    Long driverId = temporaryBookingRepository.getPendingDriver(tempID);
                    String email = userRepository.getEmail(driverId);
                    String subject="Booking cancel";
                    String body="Your Booking has canceled by tourist";
                    sendMails(email,subject,body);
                    temporaryBookingRepository.setDriverState(tempID,"notSelected");
                    driverBookingRepository.deleteById(id);
                }
                else {
                    return "ERROR : Hadn't Book Driver";
                }
            }

            if (type.toLowerCase().equals("guide")){

                String guideState = temporaryBookingRepository.getGuideStatus(tempID);
                if(!(guideState.toLowerCase().equals("souldselect")||guideState.toLowerCase().equals("notselect"))){
                    Long guideId = temporaryBookingRepository.getPendingGuide(tempID);
                    String email = userRepository.getEmail(guideId);
                    String subject="Booking cancel";
                    String body="Your Booking has canceled by tourist";
                    sendMails(email,subject,body);
                    temporaryBookingRepository.setGuideState(tempID,"notSelected");
                    guideBookingRepository.deleteById(id);
                }
                else {
                    return "ERROR : Hadn't Book Guide";
                }
            }

        }
        else {
            if(bookingState.toLowerCase().equals("rated")){
                return "ERROR: FinishedBooking";
            }
            if (type.toLowerCase().equals("hotel")){
                Long hotelId = hotelBookingRepository.findHotelId(id);
                if (hotelId!=null){
                    Long ownerId = hotelRepository.getOwnerId(hotelId);
                    String email = userRepository.getEmail(ownerId);
                    String subject="Booking cancel";
                    String body="Your Booking has canceled by tourist";
                    sendMails(email,subject,body);
                    hotelBookingRepository.deleteById(id);
                }
                else {
                    return "ERROR : Hadn't Book Hotel";
                }
            }
            else {
                if (type.toLowerCase().equals("driver")){
                    Long driverId = driverBookingRepository.findDriverId(id);
                    if (driverId!=null){
                        String email = userRepository.getEmail(driverId);
                        String subject="Booking cancel";
                        String body="Your Booking has canceled by tourist";
                        sendMails(email,subject,body);
                        driverBookingRepository.deleteById(id);
                    }
                    else {
                        return "ERROR : Hadn't Book Driver";
                    }
                }
                else {
                    if (type.toLowerCase().equals("guide")){
                        Long guideId = guideBookingRepository.findGuideId(id);
                        if (guideId!=null){
                            String email = userRepository.getEmail(guideId);
                            String subject="Booking cancel";
                            String body="Your Booking has canceled by tourist";
                            sendMails(email,subject,body);
                            guideBookingRepository.deleteById(id);
                        }
                        else {
                            return "ERROR : Hadn't Book Driver";
                        }
                    }
                    else {
                        return "ERROR : Wrong Type";
                    }

                }
            }
        }
        Long touristId = bookingRepository.getTouristId(id);
        String email = userRepository.getEmail(touristId);
        String subject="Booking cancel";
        String body="Your Booking has successfully canceled";
        sendMails(email,subject,body);

        return "successfully canceled";
    }

    @Override
    public double getTotalAmount(Long hotelId,Long guideId,Long vehicleId,int dayCount,String categoryType,int roomCount) {
        return calculateTotalAmount(hotelId, guideId, vehicleId, dayCount, categoryType, roomCount);
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
    public Long getGuideId(Long id) {
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
    public Long getDriverId(Long id) {
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
    public Long getHotelId(Long id) {
        return hotelBookingRepository.findHotelId(id);
    }

    @Override
    public void updateHotel(Long hotel, Long id) {
        hotelBookingRepository.setHotel(id,hotel);
    }

    @Override
    public void mailSender(String toEmail, String subject, String body) {
        sendMails(toEmail, subject, body);
    }
}
