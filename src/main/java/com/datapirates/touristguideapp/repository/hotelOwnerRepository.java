package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.bookings.Booking;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface hotelOwnerRepository extends JpaRepository<HotelOwner, Long> {

    @Transactional
    @Modifying
    @Query("update HotelOwner O set O.adminStatus=:status where O.userId=:id")
    void approve(Long id,String status);
    List<HotelOwner> findByAdminStatus(String status);

}
