package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
