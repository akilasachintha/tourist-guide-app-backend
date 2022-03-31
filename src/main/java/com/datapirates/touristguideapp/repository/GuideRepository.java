package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.model.user.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
}
