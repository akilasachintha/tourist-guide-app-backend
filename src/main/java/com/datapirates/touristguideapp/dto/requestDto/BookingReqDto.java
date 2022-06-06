package com.datapirates.touristguideapp.dto.requestDto;


import com.datapirates.touristguideapp.entity.bookings.Booking;
import com.datapirates.touristguideapp.entity.bookings.TemporaryBooking;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class BookingReqDto {

    private Booking booking;

    private Long user;

    private Long guide;

    private Long driver;

    private Long hotel;

}
