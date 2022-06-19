package com.datapirates.touristguideapp.dto.requestDto;

import lombok.Data;


@Data
public class verifyReqDto {
    private String email;
    private String code;
}
