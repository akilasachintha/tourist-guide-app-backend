package com.datapirates.touristguideapp.dto.responseDto;

import lombok.Data;

@Data
public class LoginResDTO {

    private Long userId;
    private String userType;
    private boolean status;
}
