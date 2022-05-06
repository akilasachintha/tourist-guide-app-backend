package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.location.Location;
import com.datapirates.touristguideapp.entity.users.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.datapirates.touristguideapp.entity.users.Guide;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface guideRepository extends JpaRepository<Guide, Long> {
    @Transactional
    @Modifying
    @Query("update Guide g set g.rating=:rate where g.user_id=:id")
    void setRate(Long id,double rate);
    @Transactional
    @Modifying
    @Query("update Guide G set G.availability=:availability where G.user_id=:id")
    void setAvailability(Long id,String availability);
    List<Guide> findByAvailability(String availability);
}
