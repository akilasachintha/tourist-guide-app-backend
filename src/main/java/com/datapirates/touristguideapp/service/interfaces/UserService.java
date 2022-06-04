package com.datapirates.touristguideapp.service.interfaces;

import com.datapirates.touristguideapp.dto.requestDto.LoginReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.LoginResDTO;
import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.users.*;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser appUser);

    String guideRating(Long id,int starCount);

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


    List<AppUser> getUsers();

    LoginResDTO authUser(LoginReqDTO loginReqDTO);
}
