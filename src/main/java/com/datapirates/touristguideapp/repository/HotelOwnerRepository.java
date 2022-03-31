package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.model.user.HotelOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelOwnerRepository extends JpaRepository<HotelOwner, Long> {
}
