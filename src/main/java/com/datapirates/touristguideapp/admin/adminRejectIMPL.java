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

import java.util.Optional;

@Service
public class adminRejectIMPL implements adminReject {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private guideRepository guideRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private hotelRepository hotelRepository;
    @Autowired
    private hotelOwnerRepository hotelOwnerRepository;
    @Autowired
    private roomRepository roomRepository;

    private void sendMails(String toEmail) {
        SimpleMailMessage massage = new SimpleMailMessage();
        String subject = "Registration Closed";
        String body = "Your Registration with TravelMate has closed by the admin.For more details send a request mail";
        massage.setFrom("subath.abeysekara@gmail.com");
        massage.setTo(toEmail);
        massage.setSubject(subject);
        massage.setText(body);

        javaMailSender.send(massage);
    }

    @Override
    public String rejectGuide(Long id) {
        Optional<Guide> guide = guideRepository.findById(id);
        if (!guide.isPresent()) {
            return "Error id";
        }
        Guide guide1 = guide.get();
        sendMails(guide1.getEmail());
        guideRepository.deleteById(id);
        return "Rejected";
    }

    @Override
    public String rejectDriver(Long id) {
        Optional<Driver> driver = driverRepository.findById(id);
        if (!driver.isPresent()) {
            return "Driver Not Found with Id " + id;
        }
        Driver driver1 = driver.get();
//        sendMails(driver1.getEmail());
        driverRepository.deleteByUserId(id);
        return "Rejected";
    }

    @Override
    public String rejectHotelOwner(Long id) {
        Optional<HotelOwner> hotelOwner = hotelOwnerRepository.findById(id);
        if (!hotelOwner.isPresent()) {
            return "Error id";
        }
        HotelOwner hotelOwner1 = hotelOwner.get();
        sendMails(hotelOwner1.getEmail());
        hotelOwnerRepository.deleteById(id);
        return "Rejected";
    }

    @Override
    public String rejectHotel(Long id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (!hotel.isPresent()) {
            return "Error id";
        }
        Hotel hotel1 = hotel.get();
        HotelOwner hotelOwner = hotel1.getHotelOwner();
        sendMails(hotelOwner.getEmail());
        hotelRepository.deleteById(id);
        return "Rejected";
    }

    @Override
    public String rejectVehicle(Long id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if (!vehicle.isPresent()) {
            return "Error id";
        }
        Vehicle vehicle1 = vehicle.get();
        Driver driver = vehicle1.getDriver();
//        sendMails(driver.getEmail());
        vehicleRepository.deleteByVehicleId(id);
        return "Rejected";
    }

    @Override
    public String rejectHotelRoom(Long id, Long RoomNo) {
        Optional<HotelRoom> room = roomRepository.findByHotelAndRoomNo(id, RoomNo);
        if (!room.isPresent()) {
            return "Error id";
        }
        HotelRoom room1 = room.get();
        Hotel hotel = room1.getHotel();
        HotelOwner hotelOwner = hotel.getHotelOwner();
        sendMails(hotelOwner.getEmail());
        roomRepository.deleteById(id);
        return "Rejected";
    }
}
