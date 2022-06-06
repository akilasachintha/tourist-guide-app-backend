package com.datapirates.touristguideapp.service.impl;

import com.datapirates.touristguideapp.dto.requestDto.VehicleReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.VehicleResDTO;
import com.datapirates.touristguideapp.entity.Vehicle;
import com.datapirates.touristguideapp.entity.location.Location;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.datapirates.touristguideapp.exception.ResourceNotFoundException;
import com.datapirates.touristguideapp.repository.DriverRepository;
import com.datapirates.touristguideapp.repository.VehicleRepository;
import com.datapirates.touristguideapp.service.interfaces.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;


    @Override
    public Vehicle saveVehicle(VehicleReqDTO vehicleReqDTO) {
        return convertDtoToEntity(vehicleReqDTO);
    }

    @Override
    public List<VehicleResDTO> getVehicles() {
        return vehicleRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<VehicleResDTO> getVehiclesByAppUserId(Long id) {
        List<Vehicle> existingVehicles = vehicleRepository.findAllByUserId(id);
        return existingVehicles.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private VehicleResDTO convertEntityToDto(Vehicle vehicle){
        VehicleResDTO vehicleResDTO = new VehicleResDTO();

        vehicleResDTO.setVehicleId(vehicle.getVehicleId());
        vehicleResDTO.setVehicleNo(vehicle.getVehicleNo());
        vehicleResDTO.setVehicleName(vehicle.getVehicleName());
        vehicleResDTO.setVehicleType(vehicle.getVehicleType());
        vehicleResDTO.setVehicleCondition(vehicle.getVehicleCondition());
        vehicleResDTO.setPriceForKm(vehicle.getPriceForKm());
        vehicleResDTO.setSeats(vehicle.getSeats());
        vehicleResDTO.setVehiclePhotoUrl(vehicle.getVehiclePhotoUrl());

        vehicleResDTO.setUserId(vehicle.getDriver().getUserId());
        vehicleResDTO.setAvailability(vehicle.getDriver().getAvailability());
        vehicleResDTO.setRating(vehicle.getDriver().getRating());
        vehicleResDTO.setUserPhotoUrl(vehicle.getDriver().getUserPhotoUrl());

        return vehicleResDTO;
    }


    private Vehicle convertDtoToEntity(VehicleReqDTO vehicleReqDTO){
        Vehicle vehicle = new Vehicle();

        vehicle.setVehicleNo(vehicleReqDTO.getVehicleNo());
        vehicle.setVehicleName(vehicleReqDTO.getVehicleName());
        vehicle.setVehiclePhotoUrl(vehicleReqDTO.getPhotoUrl());
        vehicle.setVehicleType(vehicleReqDTO.getVehicleType());
        vehicle.setSeats(vehicleReqDTO.getSeats());
        vehicle.setPriceForKm(vehicleReqDTO.getPriceForKm());
        vehicle.setVehicleCondition(vehicleReqDTO.getVehicleCondition());

        Driver existingDriver = driverRepository.findById(vehicleReqDTO.getUserId()).orElseThrow(() ->
                    new ResourceNotFoundException("Driver", "Id", vehicleReqDTO.getUserId()));
        vehicle.setDriver(existingDriver);

        return vehicleRepository.save(vehicle);
    }
}


