package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.users.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface roomRepository extends JpaRepository<HotelRoom, Long> {
    @Transactional
    @Modifying
    @Query("update HotelRoom R set R.roomAvailability=:availability where R.roomId=:roomId")
    void setAvailability(Long roomId,String availability);
    @Transactional
    @Modifying
    @Query("update HotelRoom R set R.adminStatus=:status where R.hotel=:id and R.roomNo=:roomNo")
    void approve(Long id,String status,Long roomNo);
    @Transactional
    @Modifying
    @Query("update HotelRoom R set R.roomCategory=:category where R.roomCategory=:type")
    void setCategory(String category,String type);

    List<HotelRoom> findByHotelAndRoomAvailability(Long id, String availability);
    List<HotelRoom> findByAdminStatus(String status);
    Optional<HotelRoom> findByHotelAndRoomNo(Long id, Long roomNo);
    void deleteByHotelAndRoomNo(Long id, Long roomNo);
}
