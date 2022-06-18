package com.datapirates.touristguideapp.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity,Integer> {
    AdminEntity findByUsername(String username);

    AdminEntity findByEmail(String email);
}
