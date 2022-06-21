package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.users.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.datapirates.touristguideapp.entity.bookings.TemporaryBooking;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface temporary_bookingRepository extends JpaRepository<TemporaryBooking, Long> {

    @Transactional
    @Modifying
    @Query("update TemporaryBooking t set t.guideStatus=:state where t.tempBookingId=:id")
    void setGuideState(Long id,String state);
    @Transactional
    @Modifying
    @Query("update TemporaryBooking t set t.driverStatus=:state where t.tempBookingId=:id")
    void setDriverState(Long id,String state);
    @Transactional
    @Modifying
    @Query("update TemporaryBooking t set t.hotelStatus=:state where t.tempBookingId=:id")
    void setHotelState(Long id,String state);
    @Transactional
    @Modifying
    @Query("update TemporaryBooking t set t.guideEndTime=:endTime where t.tempBookingId=:id")
    void setGuideEndTime(Long id,String endTime);
    @Transactional
    @Modifying
    @Query("update TemporaryBooking t set t.driverEndTime=:endTime where t.tempBookingId=:id")
    void setDriverEndTime(Long id,String endTime);
    @Transactional
    @Modifying
    @Query("update TemporaryBooking t set t.hotelEndTime=:endTime where t.tempBookingId=:id")
    void setHotelEndTime(Long id,String endTime);
    @Transactional
    @Modifying
    @Query("update TemporaryBooking t set t.pendingGuide=:id2 where t.tempBookingId=:id")
    void setPendingGuide(Long id,Long id2);
    @Transactional
    @Modifying
    @Query("update TemporaryBooking t set t.pendingDriver=:id2 where t.tempBookingId=:id")
    void setPendingDriver(Long id,Long id2);
    @Transactional
    @Modifying
    @Query("update TemporaryBooking t set t.pendingHotel=:id2 where t.tempBookingId=:id")
    void setPendingHotel(Long id,Long id2);
    @Query("select t.tempBookingId from TemporaryBooking t where t.pendingGuide=:id")
    Optional<TemporaryBooking> checkPendingGuide(Long id);
    @Query("select t.tempBookingId from TemporaryBooking t where t.pendingDriver=:id")
    Optional<TemporaryBooking> checkPendingDriver(Long id);
    @Query("select t.pendingDriver from TemporaryBooking t where t.tempBookingId=:id")
    Long getPendingDriver(Long id);
    @Query("select t.driverEndTime from TemporaryBooking t where t.tempBookingId=:id")
    String getEndTimeDriver(Long id);
    @Query("select t.guideEndTime from TemporaryBooking t where t.tempBookingId=:id")
    String getEndTimeGuide(Long id);
    @Query("select t.hotelEndTime from TemporaryBooking t where t.tempBookingId=:id")
    String getEndTimeHotel(Long id);
    @Query("select t.pendingGuide from TemporaryBooking t where t.tempBookingId=:id")
    Long getPendingGuide(Long id);
    @Query("select t.pendingHotel from TemporaryBooking t where t.tempBookingId=:id")
    Long getPendingHotel(Long id);
    @Query("select t.tempBookingId from TemporaryBooking t where t.pendingHotel=:id")
    List<TemporaryBooking> checkPendingHotel(Long id);
    @Query("select t.driverStatus from TemporaryBooking t where t.tempBookingId=:id")
    String getDriverStatus(Long id);
    @Query("select t.guideStatus from TemporaryBooking t where t.tempBookingId=:id")
    String getGuideStatus(Long id);
    @Query("select t.hotelStatus from TemporaryBooking t where t.tempBookingId=:id")
    String getHotelStatus(Long id);
    @Query("select t.tempBookingId from TemporaryBooking t where t.hotelStatus=:status")
    List<Long> getIdsByHotel(String status);
    @Query("SELECT t.tempBookingId FROM TemporaryBooking t")
    List<Long> getAllTempIds();
    @Query("select t.tempBookingId from TemporaryBooking t where t.driverStatus=:status")
    List<Long> getIdsByDriver(String status);
    @Query("select t.tempBookingId from TemporaryBooking t where t.guideStatus=:status")
    List<Long> getIdsByGuide(String status);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TemporaryBooking t WHERE t.tempBookingId = :id")
    void deleteTemBooking(Long id);

    Optional<TemporaryBooking> findByPendingGuide(Long id);
    Optional<TemporaryBooking> findByPendingDriver(Long id);
    Optional<TemporaryBooking> findByPendingHotel(Long id);
    Optional<TemporaryBooking> findByBookingid(Long id);


}
