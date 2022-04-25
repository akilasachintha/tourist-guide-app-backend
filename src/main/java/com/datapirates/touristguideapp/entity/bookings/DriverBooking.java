package com.datapirates.touristguideapp.entity.bookings;

import com.datapirates.touristguideapp.entity.users.Driver;
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
public class DriverBooking extends Booking {
    private String participant;

    private double advancedPayment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "driver_id", foreignKey = @ForeignKey(name = "driver_booking_fk1"), insertable = false, updatable = false)
    @JsonBackReference("driver-driverBookings")
    @ToString.Exclude
    private Driver driver;
}
