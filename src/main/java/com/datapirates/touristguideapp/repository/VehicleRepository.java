package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query(value = "SELECT v FROM Vehicle v WHERE v.driver.userId = :id")
    List<Vehicle> findAllByUserId(Long id);
}
