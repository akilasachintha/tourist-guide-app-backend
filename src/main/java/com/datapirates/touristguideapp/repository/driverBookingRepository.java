package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.bookings.DriverBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface driverBookingRepository extends JpaRepository<DriverBooking, Long> {
    @Query("select B.bookingId from DriverBooking B where B.user_id=:id")
    Optional<DriverBooking> findIdByDriverId(Long id);
    @Query("select B.user_id from DriverBooking B where B.bookingId=:id")
    Optional<DriverBooking> findDriverId(Long id);
}
