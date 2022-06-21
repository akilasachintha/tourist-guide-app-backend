package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.hotel.HotelImage;
import com.datapirates.touristguideapp.entity.hotel.HotelImageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface HotelImageRepository extends JpaRepository<HotelImage , String> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM  HotelImage h WHERE h.url = :url")
    void deleteHotelImage(String url);
}
