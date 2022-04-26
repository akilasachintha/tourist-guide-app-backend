package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.dto.requestDto.UserDriverReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.DriverResponseDTO;
import com.datapirates.touristguideapp.entity.location.Location;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.datapirates.touristguideapp.exception.ResourceNotFoundException;
import com.datapirates.touristguideapp.repository.DriverRepository;
import com.datapirates.touristguideapp.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

    @Override
    public Driver saveDriver(UserDriverReqDTO userDriverReqDTO) {
        return convertDtoToEntity(userDriverReqDTO);
    }

    @Override
    public DriverResponseDTO getDriverById(Long userId) {
        DriverResponseDTO driverResponseDTO = new DriverResponseDTO();

        Driver existingDriver = driverRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("Driver", "Id", userId));

        driverResponseDTO.setUserType(existingDriver.getUserType());
        driverResponseDTO.setUserId(existingDriver.getUserId());
        driverResponseDTO.setLicenceNo(existingDriver.getLicenceNo());
        driverResponseDTO.setName(existingDriver.getName());
        driverResponseDTO.setAvailability(existingDriver.getAvailability());
        driverResponseDTO.setRating(existingDriver.getRating());
        driverResponseDTO.setUserPhotoUrl(existingDriver.getUserPhotoUrl());

        return driverResponseDTO;
    }

    private Driver convertDtoToEntity(UserDriverReqDTO userDriverReqDTO) {
        Driver driver = new Driver();

        driver.setName(userDriverReqDTO.getName());
        driver.setEmail(userDriverReqDTO.getEmail());
        driver.setPassword(userDriverReqDTO.getPassword());
        driver.setUserPhotoUrl(userDriverReqDTO.getPhotoUrl());
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
}


