package com.datapirates.touristguideapp.service.interfaces;

import com.datapirates.touristguideapp.dto.requestDto.VehicleReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.VehicleResDTO;
import com.datapirates.touristguideapp.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle saveVehicle(VehicleReqDTO vehicleReqDTO);

    List<VehicleResDTO> getVehicles();

    List<VehicleResDTO> getVehiclesByAppUserId(Long id);

    Vehicle updateVehicleStatus(Long id, Vehicle vehicle);

    String deleteVehicleById(Long id);
}
