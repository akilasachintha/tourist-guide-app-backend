package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.bookings.GuideBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface guideBookingRepository extends JpaRepository<GuideBooking, Long> {
    @Query("select B.bookingId from GuideBooking B where B.guide=:id")
    Optional<GuideBooking> findIdByGuideId(Long id);
    @Query("select B.guide from GuideBooking B where B.bookingId=:id")
    Long findGuideId(Long id);
    @Transactional
    @Modifying
    @Query("update GuideBooking G set G.guide=:guide where G.guide=:id")
    void setGuide(Long id,Long guide);
}
