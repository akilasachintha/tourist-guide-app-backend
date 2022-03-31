package com.datapirates.touristguideapp.service.interfaces;

import com.datapirates.touristguideapp.model.user.Driver;

import java.util.List;

public interface DriverService {
    Driver saveDriver(Driver driver);

    List<Driver> getDrivers();

    Driver updateDriver(Driver driver);

    String deleteDriver(Long id);
}
