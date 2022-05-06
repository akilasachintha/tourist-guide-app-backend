package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.bookings.DriverBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface driverBookingRepository extends JpaRepository<DriverBooking, Long> {
    @Query("select B.bookingId from DriverBooking B where B.driver=:id")
    Optional<DriverBooking> findIdByDriverId(Long id);
    @Query("select B.driver from DriverBooking B where B.bookingId=:id")
    Optional<DriverBooking> findDriverId(Long id);
    @Transactional
    @Modifying
    @Query("update DriverBooking D set D.driver=:driver where D.driver=:id")
    void setDriver(Long id,Long driver);
}
