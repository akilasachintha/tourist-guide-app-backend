package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.dto.requestDto.LoginReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.LoginResDTO;
import com.datapirates.touristguideapp.entity.users.Guide;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import com.datapirates.touristguideapp.entity.users.Tourist;
import com.datapirates.touristguideapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.build.RepeatedAnnotationPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResDTO> authUser(@RequestBody LoginReqDTO loginReqDTO){
        LoginResDTO authUser = userService.authUser(loginReqDTO);
        return ResponseEntity.ok(authUser);
    }

    /** tourist **/

    @PostMapping("/tourist/add")
    private String addTourist(@RequestBody Tourist tourist){
        userService.saveTourist(tourist);
        return "Successfully added";
    }

    @PutMapping("/tourist/update")
    private String updateTourist(@RequestParam Long id,@RequestBody Tourist tourist){
        return userService.updateTourist(id,tourist);
    }

    /** hotel owner **/

    @PostMapping("/hotelOwner/add")
    private String addHotelOwner(@RequestBody HotelOwner hotelOwner){
        userService.saveHotelOwner(hotelOwner);
        return "Successfully added";
    }

    @PutMapping("/hotelOwner/update")
    private String updateHotelOwner(@RequestParam Long id, @RequestBody HotelOwner hotelOwner){
        return userService.updateHotelOwner(id,hotelOwner);
    }

    /** guide **/

    @PostMapping("/guide/add")
    private String addGuide(@RequestBody Guide guide){
        userService.saveGuide(guide);
        return "Successfully added";
    }

    @PutMapping("/guide/update")
    private String updateGuide(@RequestParam Long id, @RequestBody Guide guide){
        return userService.updateGuide(id,guide);
    }

    @GetMapping("/guide/getByAvailability")
    private List<Guide> getGuideByAvailability(@RequestParam String availability){
        return userService.getGuideByAvailability(availability);
    }

    @PutMapping("/guide/rate")
    private String ratingGuide(@RequestParam Long id, @RequestParam int starCount){
        return userService.guideRating(id,starCount);
    }

}
