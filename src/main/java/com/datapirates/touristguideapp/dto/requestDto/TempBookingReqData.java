package com.datapirates.touristguideapp.dto.requestDto;

import lombok.Data;

@Data
public class TempBookingReqData {

    private Long pendingHotel;

    private Long pendingDriver;

    private Long pendingGuide;

    private String driverStatus;

    private String guideStatus;

    private String hotelStatus;

    private String guideEndTime;

    private String hotelEndTime;

    private String driverEndTime;

}
