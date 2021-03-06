package com.datapirates.touristguideapp.repository;

import com.datapirates.touristguideapp.entity.users.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByEmail(String email);

    @Query("select u.rateAmount from AppUser u where u.userId=:id")
    Long getRateAmount(Long id);
    @Transactional
    @Modifying
    @Query("update AppUser u set u.rateAmount=:rate where u.userId=:id")
    void setRateAmount(Long id,Long rate);
    @Query("select u.rating from AppUser u where u.userId=:id")
    double getRate(Long id);
    @Query("select u.email from AppUser u where u.userId=:id")
    String getEmail(Long id);
    @Transactional
    @Modifying
    @Query("update AppUser a set a.rating=:rate where a.userId=:id")
    void setRate(Long id,double rate);

//    Optional<AppUser> findByUserId(Long id);
}
