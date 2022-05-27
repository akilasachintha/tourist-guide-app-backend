package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.datapirates.touristguideapp.entity.users.Tourist;

@Repository
public interface touristRepository extends JpaRepository<Tourist, Long> {
}