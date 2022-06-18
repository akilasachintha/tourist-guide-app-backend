package com.datapirates.touristguideapp.service.impl;

import com.datapirates.touristguideapp.dto.requestDto.UserDriverReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.DriverResponseDTO;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.location.Location;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.datapirates.touristguideapp.repository.exception.ResourceNotFoundException;
import com.datapirates.touristguideapp.repository.DriverRepository;
import com.datapirates.touristguideapp.admin.adminApprove;
import com.datapirates.touristguideapp.repository.LocationRepository;
import com.datapirates.touristguideapp.repository.UserRepository;
import com.datapirates.touristguideapp.service.interfaces.DriverService;
import com.sun.xml.bind.v2.runtime.reflect.Lister;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {

    private UserRepository userRepository;

    private DriverRepository driverRepository;

    private final LocationRepository locationRepository;

    private adminApprove adminApprove;


    @Override
    public Driver saveDriver(UserDriverReqDTO userDriverReqDTO) {
        return convertDtoToEntity(userDriverReqDTO);
    }

    private Driver convertDtoToEntity(UserDriverReqDTO userDriverReqDTO) {
        Driver driver = new Driver();

        driver.setName(userDriverReqDTO.getName());
        driver.setEmail(userDriverReqDTO.getEmail());
        driver.setPassword(userDriverReqDTO.getPassword());
        driver.setUserPhotoUrl(userDriverReqDTO.getUserPhotoUrl());
        driver.setDob(userDriverReqDTO.getDob());
        driver.setPhoneNo(userDriverReqDTO.getPhoneNo());
        driver.setAvailability(userDriverReqDTO.getAvailability());
        driver.setRating(userDriverReqDTO.getRating());
        driver.setLicenceNo(userDriverReqDTO.getLicenceNo());

        Location existingLocation = locationRepository.findById(userDriverReqDTO.getLocationId()).orElseThrow(() ->
                new ResourceNotFoundException("Location", "Id", userDriverReqDTO.getUserId()));
        driver.setLocation(existingLocation);

        return driverRepository.save(driver);
    }

    @Override
    public DriverResponseDTO getDriverById(Long userId) {
        return null;
    }

    @Override
    public String driverRating(Long id, int starCount) {
        Optional<Driver> checking = driverRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        System.out.println(userRepository.getRate(id));
        double currentRate = userRepository.getRate(id);
        Long currentAmount = userRepository.getRateAmount(id);
        double currentStars = currentAmount * currentRate;


        currentStars+=starCount;
        currentAmount+=1;
        currentRate=currentStars/currentAmount;
        driverRepository.setRate(id,currentRate);
        userRepository.setRateAmount(id,currentAmount);
        return "successful rated";
    }

    @Override
    public List<Driver> getDriverByAvailabilityAndLocationId(String availability, Long id) {
        List<Driver> drivers = driverRepository.findByAvailabilityAndLocation(availability,id);
        Driver driver;
        for (int i=0; i<drivers.size(); i++){
            driver = drivers.get(i);
            if (!driver.getAdminStatus().equals("confirm")){
                drivers.remove(i);
            }
        }
        return drivers;
    }

    @Override
    public String setDriverAvailability(Long id, String availability) {
        Optional<Driver> checking = driverRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        driverRepository.setAvailability(id,availability);
        return "successfully updated";
    }

    @Override
    public List<DriverResponseDTO> getAllDrivers() {
        return adminApprove.getDriverByAdmin("confirm").stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private DriverResponseDTO convertEntityToDto(Driver driver){
        DriverResponseDTO driverResponseDTO = new DriverResponseDTO();

        driverResponseDTO.setUserId(driver.getUserId());
        driverResponseDTO.setEmail(driver.getEmail());
        driverResponseDTO.setName(driver.getName());
        driverResponseDTO.setLicenceNo(driver.getLicenceNo());
        driverResponseDTO.setRating(driver.getRating());
        driverResponseDTO.setUserPhotoUrl(driver.getUserPhotoUrl());
        driverResponseDTO.setAvailability(driver.getAvailability());
        driverResponseDTO.setUserType(driver.getUserType());

        return driverResponseDTO;
    }

}


