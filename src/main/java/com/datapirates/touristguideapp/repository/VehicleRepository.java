package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query(value = "SELECT v FROM Vehicle v WHERE v.driver.userId = :id")
    List<Vehicle> findAllByUserId(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Vehicle v WHERE v.vehicleId = :id")
    void deleteByVehicleId(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Vehicle v SET v.adminStatus=:status WHERE v.vehicleId=:id")
    void approve(Long id, String status);

    List<Vehicle> findByAdminStatus(String status);
}
