package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.bookings.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface hotelBookingRepository extends JpaRepository<HotelBooking, Long> {
    @Query("select B.bookingId from HotelBooking B where B.hotelid=:id")
    Optional<HotelBooking> findIdByHotelId(Long id);
    @Query("select B.hotelid from HotelBooking B where B.bookingId=:id")
    Long findHotelId(Long id);
    @Transactional
    @Modifying
    @Query("update HotelBooking B set B.hotelid=:hotel where B.hotelid=:id")
    void setHotel(Long id,Long hotel);
}
