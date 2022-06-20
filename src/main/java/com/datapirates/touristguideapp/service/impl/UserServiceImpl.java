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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    private JavaMailSender javaMailSender;


    private void sendMails(String toEmail, String subject, String body) {
        SimpleMailMessage massage = new SimpleMailMessage();
        massage.setFrom("travelmateapp2022@gmail.com");
        massage.setTo(toEmail);
        massage.setSubject(subject);
        massage.setText(body);

        javaMailSender.send(massage);
    }

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
        Guide existingGuide = guideRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Guide", "Id", id));

        existingGuide.setName(guide.getName());
        existingGuide.setAvailability(guide.getAvailability());
        existingGuide.setUserPhotoUrl(guide.getUserPhotoUrl());

        guideRepository.save(existingGuide);
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
        HotelOwner existingHotelOwner = hotelOwnerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("HotelOwner", "Id", id));

        existingHotelOwner.setName(hotelOwner.getName());
        existingHotelOwner.setUserPhotoUrl(hotelOwner.getUserPhotoUrl());
        existingHotelOwner.setPhoneNo(hotelOwner.getPhoneNo());
        hotelOwnerRepository.save(existingHotelOwner);
        return "update success";
    }

    @Override
    public Tourist saveTourist(Tourist tourist) {
        String verifyCode = buildVerify();
        tourist.setVerifyCode(verifyCode);
        sendMails(tourist.getEmail(), "VerifyCode", verifyCode);
        return touristRepository.save(tourist);
    }

    private String buildVerify() {
        StringBuilder code = new StringBuilder();
        String numbers = "1234567890";

        char[] numbArray = numbers.toCharArray();

        for (int i = 0; i < 6; i++) {
            int a = (int) (Math.random() * 10);
            code.append(numbArray[a]);
        }

        return code.toString();
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
    public String verifyTourist(String email, String code) {
        List<Tourist> tourists = touristRepository.findAll();
        for (Tourist tourist : tourists) {
            if (tourist.getEmail().equals(email) && tourist.getVerifyCode().equals(code)) {
                touristRepository.setVerifyStatus(tourist.getUserId(), "confirm");
                return "successfully verified";
            }
        }
        return "Error verify";
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

        switch (appUser.getUserType()) {
            case "driver":
                Driver driver = driverRepository.findById(appUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Driver", "Id", appUser.getUserId()));
                appUserResponseDTO.setAdminStatus(driver.getAdminStatus());
                break;
            case "guide":
                Guide guide = guideRepository.findById(appUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Guide", "Id", appUser.getUserId()));
                appUserResponseDTO.setAdminStatus(guide.getAdminStatus());
                appUserResponseDTO.setAvailability(guide.getAvailability());
                break;
            case "hotelOwner":
                HotelOwner hotelOwner = hotelOwnerRepository.findById(appUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("HotelOwner", "Id", appUser.getUserId()));
                appUserResponseDTO.setAdminStatus(hotelOwner.getAdminStatus());
                break;
            case "tourist":
                Tourist tourist = touristRepository.findById(appUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Tourist", "Id", appUser.getUserId()));
                appUserResponseDTO.setVerifyStatus(tourist.getVerifyStatus());
                break;
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
