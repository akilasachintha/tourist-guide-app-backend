package com.datapirates.touristguideapp.service.impl;

import com.datapirates.touristguideapp.dto.requestDto.VehicleReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.VehicleResDTO;
import com.datapirates.touristguideapp.entity.Vehicle;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.datapirates.touristguideapp.repository.DriverRepository;
import com.datapirates.touristguideapp.repository.VehicleRepository;
import com.datapirates.touristguideapp.repository.exception.ResourceNotFoundException;
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
        return vehicleRepository.findByAdminStatus("confirm").stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<VehicleResDTO> getVehiclesByAppUserId(Long id) {
        List<Vehicle> existingVehicles = vehicleRepository.findAllByUserId(id);

//        Vehicle vehicle;
//        for (int i = 0; i < existingVehicles.size(); i++) {
//            vehicle = existingVehicles.get(i);
//            if (!vehicle.getAdminStatus().equals("confirm")) {
//                existingVehicles.remove(i);
//            }
//        }

        return existingVehicles.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Vehicle updateVehicleStatus(Long id, Vehicle vehicle) {
        Vehicle existingVehicle = vehicleRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Vehicle", "Id", id));

        existingVehicle.setVehicleStatus(vehicle.getVehicleStatus());

        vehicleRepository.save(existingVehicle);
        return existingVehicle;
    }

    @Override
    public String deleteVehicleById(Long id) {
        vehicleRepository.deleteByVehicleId(id);
        return "Vehicle Id " + id + " Deleted Successfully";
    }

    @Override
    public Vehicle updateVehicleDetails(Long id, Vehicle vehicle) {
        Vehicle existingVehicle = vehicleRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Vehicle", "Id", id));

        existingVehicle.setAdminStatus("pending");
        existingVehicle.setVehicleNo(vehicle.getVehicleNo());
        existingVehicle.setVehicleModal(vehicle.getVehicleModal());
        existingVehicle.setVehicleType(vehicle.getVehicleType());
        existingVehicle.setVehicleName(vehicle.getVehicleName());
        existingVehicle.setVehiclePhotoUrl(vehicle.getVehiclePhotoUrl());
        existingVehicle.setVehicleCondition(vehicle.getVehicleCondition());
        existingVehicle.setSeats(vehicle.getSeats());

        vehicleRepository.save(existingVehicle);
        return existingVehicle;
    }

    private VehicleResDTO convertEntityToDto(Vehicle vehicle) {
        VehicleResDTO vehicleResDTO = new VehicleResDTO();

        vehicleResDTO.setVehicleId(vehicle.getVehicleId());
        vehicleResDTO.setVehicleNo(vehicle.getVehicleNo());
        vehicleResDTO.setVehicleName(vehicle.getVehicleName());
        vehicleResDTO.setVehicleType(vehicle.getVehicleType());
        vehicleResDTO.setVehicleCondition(vehicle.getVehicleCondition());
        vehicleResDTO.setPriceForKm(vehicle.getPriceForDay());
        vehicleResDTO.setSeats(vehicle.getSeats());
        vehicleResDTO.setVehiclePhotoUrl(vehicle.getVehiclePhotoUrl());
        vehicleResDTO.setVehicleModal(vehicle.getVehicleModal());
        vehicleResDTO.setVehicleStatus(vehicle.getVehicleStatus());
        vehicleResDTO.setAdminStatus(vehicle.getAdminStatus());

        vehicleResDTO.setUserId(vehicle.getDriver().getUserId());
        vehicleResDTO.setAvailability(vehicle.getDriver().getAvailability());
        vehicleResDTO.setRating(vehicle.getDriver().getRating());
        vehicleResDTO.setUserPhotoUrl(vehicle.getDriver().getUserPhotoUrl());

        return vehicleResDTO;
    }

    private Vehicle convertDtoToEntity(VehicleReqDTO vehicleReqDTO) {
        Vehicle vehicle = new Vehicle();

        vehicle.setVehicleNo(vehicleReqDTO.getVehicleNo());
        vehicle.setVehicleName(vehicleReqDTO.getVehicleName());
        vehicle.setVehicleType(vehicleReqDTO.getVehicleType());
        vehicle.setSeats(vehicleReqDTO.getSeats());
        vehicle.setPriceForDay(vehicleReqDTO.getPriceForKm());
        vehicle.setVehicleCondition(vehicleReqDTO.getVehicleCondition());
        vehicle.setVehiclePhotoUrl(vehicleReqDTO.getVehiclePhotoUrl());

        Driver existingDriver = driverRepository.findById(vehicleReqDTO.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException("Driver", "Id", vehicleReqDTO.getUserId()));
        vehicle.setDriver(existingDriver);

        return vehicleRepository.save(vehicle);
    }
}


