package com.datapirates.touristguideapp.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface adminRepository extends JpaRepository<adminEntity,Integer> {
    adminEntity findByUsername(String username);
}
