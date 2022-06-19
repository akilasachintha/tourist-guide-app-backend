package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.datapirates.touristguideapp.entity.users.Tourist;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface touristRepository extends JpaRepository<Tourist, Long> {

    @Transactional
    @Modifying
    @Query("update Tourist T set T.verifyStatus=:status where T.userId=:id")
    void setVerifyStatus(Long id,String status);
}
