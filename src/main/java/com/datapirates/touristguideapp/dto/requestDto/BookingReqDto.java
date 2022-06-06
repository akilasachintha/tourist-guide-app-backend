package com.datapirates.touristguideapp.dto.requestDto;


import com.datapirates.touristguideapp.entity.bookings.TemporaryBooking;
import lombok.Data;

import java.util.Set;

@Data
public class BookingReqDto {

    private Long bookingId;

    private Long relativeTemporaryId;

    private String bookingStatus;

    private String checkInDate;

    private String checkOutDate;

    private double paidAmount;

    private String date;

    private String time;

    private Long userId;

    private Long pendingHotel;

    private Long pendingDriver;

    private Long pendingGuide;

    private String driverStatus;

    private String guideStatus;

    private String hotelStatus;

    private TimeReqDto hotelEndTime;
    private TimeReqDto guideEndTime;
    private TimeReqDto driverEndTime;

}
