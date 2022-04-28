package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.dto.requestDto.LoginReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.LoginResDTO;
import com.datapirates.touristguideapp.entity.users.AppUser;

public interface UserService {
    AppUser saveUser(AppUser appUser);

    LoginResDTO authUser(LoginReqDTO loginReqDTO);
}
