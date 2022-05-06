package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.dto.requestDto.LoginReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.LoginResDTO;
import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.users.*;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser appUser);

    LoginResDTO authUser(LoginReqDTO loginReqDTO);

    String guideRating(Long id,int starCount);

    List<Guide> getGuideByAvailability(String availability);

    Guide saveGuide(Guide guide);

    String updateGuide(Long id,Guide guide);
/***/

    HotelOwner saveHotelOwner(HotelOwner hotelOwner);

    String updateHotelOwner(Long id, HotelOwner hotelOwner);
/***/

    Tourist saveTourist(Tourist tourist);

    String updateTourist(Long id , Tourist tourist);


}
