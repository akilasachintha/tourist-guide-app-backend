package com.datapirates.touristguideapp.entity.bookings;

import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.users.Guide;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "hotel_booking_fk1"))
public class HotelBooking extends Booking{

    private int noOfMembers;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "hotelid", referencedColumnName = "hotelId")
    private Hotel hotelid;

    @OneToMany(mappedBy = "hotelBooking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("booking-bookingRooms")
    @ToString.Exclude
    private Set<bookingRooms> bookingRooms;
}