package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.dto.requestDto.LoginReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.LoginResDTO;
import com.datapirates.touristguideapp.entity.users.*;
import com.datapirates.touristguideapp.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private guideRepository guideRepository;

    @Autowired
    private hotelOwnerRepository hotelOwnerRepository;

    @Autowired
    private touristRepository touristRepository;

    @Override
    public AppUser saveUser(AppUser appUser) {
        return userRepository.save(appUser);
    }


    @Override
    public String guideRating(Long id, int starCount) {
        Optional<Guide> checking = guideRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        System.out.println("Check 01");

        double currentRate = userRepository.getRate(id);
        Long currentAmount = userRepository.getRateAmount(id);
        double currentStars = currentAmount * currentRate;
        System.out.println(currentRate);

        /*** after updating ***/

        currentStars+=starCount;
        currentAmount+=1;
        currentRate=currentStars/currentAmount;
        guideRepository.setRate(id,currentRate);
        userRepository.setRateAmount(id,currentAmount);
        return "successful rated";
    }

    @Override
    public List<Guide> getGuideByAvailability(String availability) {
        return guideRepository.findByAvailability(availability);
    }

    @Override
    public Guide saveGuide(Guide guide) {
        return guideRepository.save(guide);
    }

    @Override
    public String updateGuide(Long id , Guide guide) {
        Optional<Guide> checking = guideRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        guideRepository.save(guide);
        return "update success";
    }

    @Override
    public String setGuideAvailability(Long id, String availability) {
        Optional<Guide> checking = guideRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        guideRepository.setAvailability(id,availability);
        return "successfully updated";
    }

    @Override
    public HotelOwner saveHotelOwner(HotelOwner hotelOwner) {
        return hotelOwnerRepository.save(hotelOwner);
    }

    @Override
    public String updateHotelOwner(Long id , HotelOwner hotelOwner) {
        Optional<HotelOwner> checking = hotelOwnerRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        hotelOwnerRepository.save(hotelOwner);
        return "update success";
    }

    @Override
    public Tourist saveTourist(Tourist tourist) {
        return touristRepository.save(tourist);
    }

    @Override
    public String updateTourist(Long id , Tourist tourist) {
        Optional<Tourist> checking = touristRepository.findById(id);
        if (!checking.isPresent()){
            return "not available Id";
        }
        touristRepository.save(tourist);
        return "update success";
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }
}
