package com.datapirates.touristguideapp.service.interfaces;

import com.datapirates.touristguideapp.dto.requestDto.GuideRateDTO;
import com.datapirates.touristguideapp.dto.requestDto.LoginReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.AppUserResponseDTO;
import com.datapirates.touristguideapp.dto.responseDto.LoginResDTO;
import com.datapirates.touristguideapp.entity.users.AppUser;
import com.datapirates.touristguideapp.entity.users.Guide;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import com.datapirates.touristguideapp.entity.users.Tourist;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser appUser);

    String userRating(GuideRateDTO guideRateDTO);

    List<Guide> getGuideByAvailability(String availability);

    Guide saveGuide(Guide guide);

    String updateGuide(Long id,Guide guide);

    String setGuideAvailability(Long id,String availability);
/***/

    HotelOwner saveHotelOwner(HotelOwner hotelOwner);

    String updateHotelOwner(Long id, HotelOwner hotelOwner);
/***/

    Tourist saveTourist(Tourist tourist);

    String updateTourist(Long id , Tourist tourist);

    String verifyTourist(String email , String code);

    LoginResDTO authUser(LoginReqDTO loginReqDTO);

    AppUserResponseDTO getAppUserById(Long id);

//    Optional<AppUser> getUserById(Long id);
    List<Guide> getAll();
}
