package com.datapirates.touristguideapp.service.impl;

import com.datapirates.touristguideapp.admin.AdminEntity;
import com.datapirates.touristguideapp.admin.AdminRepository;
import com.datapirates.touristguideapp.admin.adminService;
import com.datapirates.touristguideapp.dto.requestDto.LoginReqDTO;
import com.datapirates.touristguideapp.dto.responseDto.AppUserResponseDTO;
import com.datapirates.touristguideapp.dto.responseDto.LoginResDTO;
import com.datapirates.touristguideapp.entity.users.*;
import com.datapirates.touristguideapp.repository.*;
import com.datapirates.touristguideapp.repository.exception.ResourceNotFoundException;
import com.datapirates.touristguideapp.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private adminService adminService;
    @Autowired
    private guideRepository guideRepository;

    @Autowired
    private hotelOwnerRepository hotelOwnerRepository;

    @Autowired
    private touristRepository touristRepository;


    private final AdminRepository adminRepository;

    private final DriverRepository driverRepository;


    @Override
    public AppUser saveUser(AppUser appUser) {
        return userRepository.save(appUser);
    }


    @Override
    public String guideRating(Long id, int starCount) {
        Optional<Guide> checking = guideRepository.findById(id);
        if (!checking.isPresent()) {
            return "not available Id";
        }
        System.out.println("Check 01");

        double currentRate = userRepository.getRate(id);
        Long currentAmount = userRepository.getRateAmount(id);
        double currentStars = currentAmount * currentRate;
        System.out.println(currentRate);

        currentStars += starCount;
        currentAmount += 1;
        currentRate = currentStars / currentAmount;
        guideRepository.setRate(id, currentRate);
        userRepository.setRateAmount(id, currentAmount);
        return "successful rated";
    }

    @Override
    public List<Guide> getGuideByAvailability(String availability) {
        List<Guide> guides = guideRepository.findByAvailability(availability);
        Guide guide;
        for (int i = 0; i < guides.size(); i++) {
            guide = guides.get(i);
            if (!guide.getAdminStatus().equals("confirm")) {
                guides.remove(i);
            }
        }
        return guides;
    }

    @Override
    public Guide saveGuide(Guide guide) {
        return guideRepository.save(guide);
    }

    @Override
    public String updateGuide(Long id, Guide guide) {
        Optional<Guide> checking = guideRepository.findById(id);
        if (!checking.isPresent()) {
            return "not available Id";
        }
        guideRepository.save(guide);
        return "update success";
    }

    @Override
    public String setGuideAvailability(Long id, String availability) {
        Optional<Guide> checking = guideRepository.findById(id);
        if (!checking.isPresent()) {
            return "not available Id";
        }
        guideRepository.setAvailability(id, availability);
        return "successfully updated";
    }

    @Override
    public HotelOwner saveHotelOwner(HotelOwner hotelOwner) {
        return hotelOwnerRepository.save(hotelOwner);
    }

    @Override
    public String updateHotelOwner(Long id, HotelOwner hotelOwner) {
        Optional<HotelOwner> checking = hotelOwnerRepository.findById(id);
        if (!checking.isPresent()) {
            return "not available Id";
        }
        hotelOwnerRepository.save(hotelOwner);
        return "update success";
    }

    @Override
    public Tourist saveTourist(Tourist tourist) {
        return touristRepository.save(tourist);
    }

    @Override
    public String updateTourist(Long id, Tourist tourist) {
        Optional<Tourist> checking = touristRepository.findById(id);
        if (!checking.isPresent()) {
            return "not available Id";
        }
        touristRepository.save(tourist);
        return "update success";
    }

    @Override
    public LoginResDTO authUser(LoginReqDTO loginReqDTO) {
        LoginResDTO loginResDTO = new LoginResDTO();

        AppUser existingAppUser = userRepository.findByEmail(loginReqDTO.getEmail());
        AdminEntity existingAdminUser = adminRepository.findByEmail(loginReqDTO.getEmail());

        if (existingAdminUser == null) {
            if (existingAppUser == null) {
                loginResDTO.setStatus(false);
            } else if (!existingAppUser.getPassword().equals(loginReqDTO.getPassword())) {
                loginResDTO.setStatus(false);
            } else {
                loginResDTO.setUserId(existingAppUser.getUserId());
                loginResDTO.setUserType(existingAppUser.getUserType());
                loginResDTO.setStatus(true);
                loginResDTO.setName(existingAppUser.getName());

                String token = adminService.madeUserSecretKey(loginReqDTO.getEmail(), loginReqDTO.getPassword());
                loginResDTO.setToken(token);
            }
        } else {
            if (!existingAdminUser.getPassword().equals(loginReqDTO.getPassword())) {
                loginResDTO.setStatus(false);
            } else {
                loginResDTO.setUserId(existingAdminUser.getId());
                loginResDTO.setUserType("admin");
                loginResDTO.setStatus(true);
                loginResDTO.setName(existingAdminUser.getUsername());

                String token = adminService.madeUserSecretKey(loginReqDTO.getEmail(), loginReqDTO.getPassword());
                loginResDTO.setToken(token);
            }
        }
        return loginResDTO;
    }

    @Override
    public AppUserResponseDTO getAppUserById(Long id) {
        AppUser existingAppUser = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("AppUser", "Id", id));
        return convertEntityToDto(existingAppUser);
    }

    @Override
    public List<Guide> getAll() {
        return guideRepository.findAll();
    }

    private AppUserResponseDTO convertEntityToDto(AppUser appUser) {
        AppUserResponseDTO appUserResponseDTO = new AppUserResponseDTO();

        if (appUser.getUserType().equals("driver")) {
            Driver driver = driverRepository.findById(appUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Driver", "Id", appUser.getUserId()));
            appUserResponseDTO.setAdminStatus(driver.getAdminStatus());
        } else if (appUser.getUserType().equals("guide")) {
            Guide guide = guideRepository.findById(appUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Guide", "Id", appUser.getUserId()));
            appUserResponseDTO.setAdminStatus(guide.getAdminStatus());
        }

        appUserResponseDTO.setUserId(appUser.getUserId());
        appUserResponseDTO.setName(appUser.getName());
        appUserResponseDTO.setEmail(appUser.getEmail());
        appUserResponseDTO.setUserPhotoUrl(appUser.getUserPhotoUrl());
        appUserResponseDTO.setUserType(appUser.getUserType());
        appUserResponseDTO.setDob(appUser.getDob());
        appUserResponseDTO.setPhoneNo(appUser.getPhoneNo());
        appUserResponseDTO.setRating(appUser.getRating());

        return appUserResponseDTO;
    }
}
