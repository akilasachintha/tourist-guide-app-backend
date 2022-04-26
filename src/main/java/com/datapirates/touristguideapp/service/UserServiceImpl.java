package com.datapirates.touristguideapp.service;

import com.datapirates.touristguideapp.dto.requestDto.LoginReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.LoginResDTO;
import com.datapirates.touristguideapp.entity.users.User;
import com.datapirates.touristguideapp.exception.ResourceNotFoundException;
import com.datapirates.touristguideapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public LoginResDTO authUser(LoginReqDTO loginReqDTO) {
        LoginResDTO loginResDTO = new LoginResDTO();

        User existingUser = userRepository.findByEmail(loginReqDTO.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("User", "Email", loginReqDTO.getEmail())
        );

        loginResDTO.setUserId(existingUser.getUserId());
        loginResDTO.setUserType(existingUser.getUserType());
        loginResDTO.setStatus(true);
        return loginResDTO;
    }
}
