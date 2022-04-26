package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.dto.requestDto.UserDriverReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.DriverResponseDTO;
import com.datapirates.touristguideapp.entity.users.Driver;

public interface DriverService {

    Driver saveDriver(UserDriverReqDTO userDriverReqDTO);

    DriverResponseDTO getDriverById(Long userId);
}
