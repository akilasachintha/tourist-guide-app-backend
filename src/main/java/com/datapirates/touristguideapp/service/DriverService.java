package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.dto.requestDto.UserDriverReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.DriverResponseDTO;
import com.datapirates.touristguideapp.entity.users.Driver;

import java.util.List;

public interface DriverService {

    Driver saveDriver(UserDriverReqDTO userDriverReqDTO);

    DriverResponseDTO getDriverById(Long userId);

    String driverRating(Long id,int starCount);

    List<Driver> getDriverByAvailabilityAndLocationId(String availability,Long id);
}
