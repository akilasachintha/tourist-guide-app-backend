package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.dto.requestDto.LoginReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.LoginResDTO;
import com.datapirates.touristguideapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResDTO> authUser(@RequestBody LoginReqDTO loginReqDTO){
        LoginResDTO authUser = userService.authUser(loginReqDTO);
        return ResponseEntity.ok(authUser);
    }
}
