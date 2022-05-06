package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.bookings.GuideBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface guideBookingRepository extends JpaRepository<GuideBooking, Long> {
    @Query("select B.bookingId from GuideBooking B where B.user_id=:id")
    Optional<GuideBooking> findIdByGuideId(Long id);
    @Query("select B.user_id from GuideBooking B where B.bookingId=:id")
    Optional<GuideBooking> findGuideId(Long id);
}
