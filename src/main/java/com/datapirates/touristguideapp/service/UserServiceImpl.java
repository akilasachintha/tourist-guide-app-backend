package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.dto.requestDto.LoginReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.LoginResDTO;
import com.datapirates.touristguideapp.entity.users.AppUser;
import com.datapirates.touristguideapp.exception.ResourceNotFoundException;
import com.datapirates.touristguideapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public AppUser saveUser(AppUser appUser) {
        return userRepository.save(appUser);
    }

    @Override
    public LoginResDTO authUser(LoginReqDTO loginReqDTO) {
        LoginResDTO loginResDTO = new LoginResDTO();

        AppUser existingAppUser = userRepository.findByEmail(loginReqDTO.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("User", "Email", loginReqDTO.getEmail())
        );

        loginResDTO.setUserId(existingAppUser.getUserId());
        loginResDTO.setUserType(existingAppUser.getUserType());
        loginResDTO.setStatus(true);
        return loginResDTO;
    }
}
