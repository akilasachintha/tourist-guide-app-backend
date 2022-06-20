package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.admin.adminApprove;
import com.datapirates.touristguideapp.dto.requestDto.GuideRateDTO;
import com.datapirates.touristguideapp.dto.requestDto.LoginReqDTO;
import com.datapirates.touristguideapp.dto.requestDto.VerifyReqDto;
import com.datapirates.touristguideapp.dto.responseDto.AppUserResponseDTO;
import com.datapirates.touristguideapp.dto.responseDto.LoginResDTO;
import com.datapirates.touristguideapp.entity.users.AppUser;
import com.datapirates.touristguideapp.entity.users.Guide;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import com.datapirates.touristguideapp.entity.users.Tourist;
import com.datapirates.touristguideapp.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private adminApprove adminApprove;

    @Autowired
    private com.datapirates.touristguideapp.admin.adminService adminService;

    /**
     * Security
     **/

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResDTO> authUser(@RequestBody LoginReqDTO loginReqDTO) {
        LoginResDTO authUser = userService.authUser(loginReqDTO);
        return ResponseEntity.ok(authUser);
    }

//    @GetMapping("/{userId}")
//    public Optional<AppUser> getUserById(@PathVariable(name = "userId") Long id) {
//        return userService.getUserById(id);
//    }

    @GetMapping("/{appUserId}")
    public AppUserResponseDTO getAppUserById(@PathVariable(name = "appUserId") Long id) {
        return userService.getAppUserById(id);
    }

    @GetMapping("/getKey")
    private String getKey(@RequestBody AppUser user) {
        return adminService.madeUserSecretKey(user.getEmail(), user.getPassword());
    }

    /**
     * tourist
     **/
    @PostMapping("/tourist/add")
    private String addTourist(@RequestBody Tourist tourist) {
        userService.saveTourist(tourist);
        return "Successfully added";
    }

    @PostMapping("/tourist/verify")
    private String addTourist(@RequestBody VerifyReqDto verifyReqDto) {
        return userService.verifyTourist(verifyReqDto.getEmail(), verifyReqDto.getCode());
    }

    @PutMapping("/tourist/update/{id}")
    private String updateTourist(@PathVariable(name = "id") Long id, @RequestBody Tourist tourist) {
        return userService.updateTourist(id, tourist);
    }

    @PostMapping("/hotelOwner/add")
    private String addHotelOwner(@RequestBody HotelOwner hotelOwner) {
        userService.saveHotelOwner(hotelOwner);
        return "Successfully added";
    }

    @PutMapping("/hotelOwner/update")
    private String updateHotelOwner(@RequestParam Long id, @RequestBody HotelOwner hotelOwner) {
        return userService.updateHotelOwner(id, hotelOwner);
    }

    /**
     * guide
     **/

    @PostMapping("/guide/add")
    private String addGuide(@RequestBody Guide guide) {
        userService.saveGuide(guide);
        return "Successfully added";
    }

    @PutMapping("/guide/update/{id}")
    private String updateGuide(@PathVariable(name = "id") Long id, @RequestBody Guide guide) {
        return userService.updateGuide(id, guide);
    }

    @GetMapping("/guide/getByAvailability")
    private List<Guide> getGuideByAvailability() {
        return userService.getGuideByAvailability("yes");
    }

    @GetMapping("/guide/getAll")
    private List<Guide> getAllGuide() {
        return adminApprove.getGuideByAdmin("confirm");
    }

    @PutMapping("/user/rate")
    private String ratingUser(@RequestBody GuideRateDTO guideRateDTO) {
        return userService.userRating(guideRateDTO);
    }

}
