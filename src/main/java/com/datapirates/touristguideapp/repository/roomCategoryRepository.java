package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.datapirates.touristguideapp.entity.hotel.RoomCategory;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface roomCategoryRepository extends JpaRepository<RoomCategory, String> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM RoomCategory h WHERE h.categoryType = :type")
    void deleteCategory(String type);
}
