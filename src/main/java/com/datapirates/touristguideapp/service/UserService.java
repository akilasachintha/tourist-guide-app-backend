package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.dto.requestDto.LoginReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.LoginResDTO;
import com.datapirates.touristguideapp.entity.users.User;

public interface UserService {
    User saveUser(User user);

    LoginResDTO authUser(LoginReqDTO loginReqDTO);
}
