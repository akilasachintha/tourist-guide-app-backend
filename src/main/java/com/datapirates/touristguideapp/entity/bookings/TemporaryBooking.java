package com.datapirates.touristguideapp.entity.bookings;

import com.datapirates.touristguideapp.entity.bookings.Booking;
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
public class TemporaryBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tempBookingId;

    private String driverStatus;

    private String guideStatus;

    private String hotelStatus;

    private String guideEndTime;

    private String hotelEndTime;

    private String vehicleEndTime;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "booking_id", foreignKey = @ForeignKey(name = "temporary_booking_fk1"))
    @JsonBackReference(value = "booking-temporaryBookings")
    private Booking booking;
}
