package com.datapirates.touristguideapp.dto.requestDto;

import lombok.Data;


@Data
public class VerifyReqDto {
    private String email;
    private String code;
}
