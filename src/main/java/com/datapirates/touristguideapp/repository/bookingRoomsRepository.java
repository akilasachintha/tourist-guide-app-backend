package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.bookings.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface bookingRoomsRepository extends JpaRepository<BookingRooms, Long> {
    @Query("select B.roomNo from BookingRooms B where B.hotelBooking=:id")
    List<Long> getRoomNosByBooking(Long id);
}
