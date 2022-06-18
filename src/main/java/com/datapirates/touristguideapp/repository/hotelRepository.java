package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.datapirates.touristguideapp.entity.hotel.Hotel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface hotelRepository extends JpaRepository<Hotel, Long> {
    @Transactional
    @Modifying
    @Query("update Hotel H set H.adminStatus=:status where H.hotelId=:id")
    void approve(Long id, String status);

    @Transactional
    @Modifying
    @Query("update Hotel H set H.rating=:rate where H.hotelId=:id")
    void setRate(Long id, double rate);

    @Transactional
    @Modifying
    @Query("update Hotel H set H.rateAmount=:rateAmount where H.hotelId=:id")
    void setRateAmount(Long id, Long rateAmount);

    @Transactional
    @Modifying
    @Query("update Hotel H set H.hotelOwner=:owner where H.hotelOwner=:id")
    void setOwner(Long id, Long owner);

    @Query("select H.hotelOwner from Hotel H where H.hotelId=:id")
    Long getOwnerId(Long id);

    @Query("select H.rating from Hotel H where H.hotelId=:id")
    Double getRate(Long id);

    @Query("select H.rateAmount from Hotel H where H.hotelId=:id")
    Long getRateAmount(Long id);

    List<Hotel> findByAdminStatus(String status);


    @Modifying
    @Query(value = "SELECT h FROM Hotel h WHERE h.hotelOwner.userId=:userId")
    List<Hotel> findAllByUserId(Long userId);
}
