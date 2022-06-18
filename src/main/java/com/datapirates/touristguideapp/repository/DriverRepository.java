package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.users.AppUser;
import com.datapirates.touristguideapp.entity.users.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    @Transactional
    @Modifying
    @Query("update Driver D set D.rating=:rate where D.userId=:id")
    void setRate(Long id,double rate);
    @Transactional
    @Modifying
    @Query("update Driver D set D.adminStatus=:status where D.userId=:id")
    void approve(Long id,String status);
    @Transactional
    @Modifying
    @Query("update Driver D set D.availability=:availability where D.userId=:id")
    void setAvailability(Long id,String availability);
    List<Driver> findByAvailabilityAndLocation(String availability, Long id);
    List<Driver> findByAdminStatus(String status);

}
