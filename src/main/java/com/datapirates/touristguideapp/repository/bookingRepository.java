package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.bookings.TemporaryBooking;
import com.datapirates.touristguideapp.entity.users.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.datapirates.touristguideapp.entity.bookings.Booking;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@Repository
public interface bookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByTouristId(Long id);
    Optional<Booking> findByRelativeTemporaryId(Long id);
    @Query("select B.relativeTemporaryId from Booking B where B.bookingId=:id")
    Optional<Booking> findTempId(Long id);
    @Query("select B.user_id from Booking B where B.bookingId=:id")
    Long getTouristId(Long id);
    @Query("select B.bookingId from Booking B where B.relativeTemporaryId=:id")
    Long getTBookingId(Long id);
    @Transactional
    @Modifying
    @Query("update Booking B set B.relativeTemporaryId=:id2 where B.bookingId=:id")
    void setTempId(Long id,Long id2);
    @Transactional
    @Modifying
    @Query("update Booking B set B.bookingStatus=:status where B.bookingId=:id")
    void setBookingStatus(Long id,String status);
    @Query("select B.bookingId from Booking B where B.user_id=:id and B.bookingStatus=:status")
    List<Long> getBookingIds(Long id,String status);

}
