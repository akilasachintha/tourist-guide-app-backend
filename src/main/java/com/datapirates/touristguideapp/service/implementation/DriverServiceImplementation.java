package com.datapirates.touristguideapp.service.implementation;

import com.datapirates.touristguideapp.model.user.Driver;
import com.datapirates.touristguideapp.repository.DriverRepository;
import com.datapirates.touristguideapp.service.interfaces.DriverService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImplementation implements DriverService {

    private final DriverRepository driverRepository;

    public DriverServiceImplementation(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public List<Driver> getDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public Driver updateDriver(Driver driver) {
        Driver existingDriver = driverRepository.findById(driver.getUserId()).orElse(null);
        assert existingDriver != null;
        existingDriver.setName(driver.getName());
        existingDriver.setEmail(driver.getEmail());
        existingDriver.setDob(driver.getDob());
        existingDriver.setPassword(driver.getPassword());
        existingDriver.setAvailability(driver.getAvailability());
        existingDriver.setLicenceNo(driver.getLicenceNo());
        existingDriver.setPhotoUrl(driver.getPhotoUrl());
        existingDriver.setRating(driver.getRating());

        return driverRepository.save(existingDriver);
    }

    @Override
    public String deleteDriver(Long id) {
        driverRepository.deleteById(id);
        return "Successfully Deleted Driver " + id;
    }
}
