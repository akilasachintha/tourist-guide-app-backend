package com.datapirates.touristguideapp.admin;

import com.datapirates.touristguideapp.entity.Vehicle;
import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.datapirates.touristguideapp.entity.users.Guide;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import com.datapirates.touristguideapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class adminApproveIMPL implements adminApprove{

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private hotelRepository hotelRepository;

    @Autowired
    private guideRepository guideRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private hotelOwnerRepository hotelOwnerRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private roomRepository roomRepository;

    private void sendMails(String toEmail) {
        SimpleMailMessage massage = new SimpleMailMessage();
        String subject = "Registration Confirmed";
        String body = "Your Registration with TravelMate has confirmed by the admin";
        massage.setFrom("subath.abeysekara@gmail.com");
        massage.setTo(toEmail);
        massage.setSubject(subject);
        massage.setText(body);

        javaMailSender.send(massage);
    }


    @Override
    public List<HotelRoom> getRoomByAdmin(String status) {
        return roomRepository.findByAdminStatus(status);
    }

    @Override
    public List<Guide> getGuideByAdmin(String status) {
        return guideRepository.findByAdminStatus(status);
    }

    @Override
    public List<Hotel> getHotelByAdmin(String status) {
        return hotelRepository.findAll();
    }

    @Override
    public List<Driver> getDriverByAdmin(String status) {
        return driverRepository.findByAdminStatus(status);
    }

    @Override
    public List<HotelOwner> getHotelOwnerByAdmin(String status) {
        return hotelOwnerRepository.findByAdminStatus(status);
    }

    @Override
    public List<Vehicle> getVehicleByAdmin(String status) {
        return vehicleRepository.findByAdminStatus(status);
    }

    @Override
    public String approveGuide(Long id) {
        Optional<Guide> guide = guideRepository.findById(id);
        if (!guide.isPresent()){
            return "Error id";
        }
        //Guide guide1 = guide.get();
        //sendMails(guide1.getEmail());
        guideRepository.approve(id,"confirm");
        return "Confirmed";
    }

    @Override
    public String approveDriver(Long id) {
        Optional<Driver> driver = driverRepository.findById(id);
        if (!driver.isPresent()){
            return "Error id";
        }
        Driver driver1 = driver.get();
//        sendMails(driver1.getEmail());
        driverRepository.approve(id,"confirm");
        return "Confirmed";
    }

    @Override
    public String approveHotelOwner(Long id) {
        Optional<HotelOwner> hotelOwner = hotelOwnerRepository.findById(id);
        if (!hotelOwner.isPresent()){
            return "Error id";
        }
        HotelOwner hotelOwner1 = hotelOwner.get();
        sendMails(hotelOwner1.getEmail());
        hotelOwnerRepository.approve(id,"confirm");
        return "Confirmed";
    }

    @Override
    public String approveHotel(Long id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (!hotel.isPresent()){
            return "Error id";
        }
        Hotel hotel1 = hotel.get();
        HotelOwner hotelOwner = hotel1.getHotelOwner();
        sendMails(hotelOwner.getEmail());
        hotelRepository.approve(id,"confirm");
        return "Confirmed";
    }

    @Override
    public String approveVehicle(Long id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if (!vehicle.isPresent()){
            return "Error id";
        }
        Vehicle vehicle1 = vehicle.get();
        Driver driver = vehicle1.getDriver();
//        sendMails(driver.getEmail());
        vehicleRepository.approve(id,"confirm");
        return "Confirmed";
    }

    @Override
    public String approveHotelRoom(Long id, Long RoomNo) {
        Optional<HotelRoom> room = roomRepository.findByHotelAndRoomNo(id,RoomNo);
        if (!room.isPresent()){
            return "Error id";
        }
        HotelRoom room1 = room.get();
        Hotel hotel = room1.getHotel();
        HotelOwner hotelOwner = hotel.getHotelOwner();
        sendMails(hotelOwner.getEmail());
        roomRepository.approve(id,"confirm",RoomNo);
        return "Confirmed";
    }
}
