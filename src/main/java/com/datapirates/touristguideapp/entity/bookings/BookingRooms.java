package com.datapirates.touristguideapp.entity.bookings;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BookingRooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roomNo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "hotelBooking", foreignKey = @ForeignKey(name = "rooms_booking_fk1"))
    @JsonBackReference(value = "booking-bookingRooms")
    @ToString.Exclude
    private HotelBooking hotelBooking;

}
