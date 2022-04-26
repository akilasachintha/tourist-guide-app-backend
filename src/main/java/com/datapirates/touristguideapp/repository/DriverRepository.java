package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.users.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

}
