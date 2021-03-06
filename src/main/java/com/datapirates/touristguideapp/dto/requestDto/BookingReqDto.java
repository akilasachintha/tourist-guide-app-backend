package com.datapirates.touristguideapp.dto.requestDto;


import com.datapirates.touristguideapp.entity.bookings.Booking;
import lombok.Data;

@Data
public class BookingReqDto {

    private Booking booking;

    private Long user;

    private int roomCount;

    private  String categoryType;

    private int dayCount;


}
