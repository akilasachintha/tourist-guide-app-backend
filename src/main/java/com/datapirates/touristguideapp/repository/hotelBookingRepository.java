package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.bookings.Booking;
import com.datapirates.touristguideapp.entity.bookings.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface hotelBookingRepository extends JpaRepository<HotelBooking, Long> {
    @Query("select B.bookingId from HotelBooking B where B.hotel_id=:id")
    Optional<HotelBooking> findIdByHotelId(Long id);
    @Query("select B.hotel_id from HotelBooking B where B.bookingId=:id")
    List<HotelBooking> findHotelId(Long id);
}
