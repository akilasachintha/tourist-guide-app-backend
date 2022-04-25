package com.datapirates.touristguideapp.entity.bookings;

import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class HotelBooking extends Booking {

    private int noOfMembers;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "hotel_id", foreignKey = @ForeignKey(name = "hotel_booking_fk1"))
    @JsonBackReference(value = "hotel-hotelBookings")
    @ToString.Exclude
    private Hotel hotel;
}
