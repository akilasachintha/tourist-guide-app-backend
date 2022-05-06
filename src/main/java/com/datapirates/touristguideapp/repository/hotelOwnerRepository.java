package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.bookings.Booking;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface hotelOwnerRepository extends JpaRepository<HotelOwner, Long> {
}
