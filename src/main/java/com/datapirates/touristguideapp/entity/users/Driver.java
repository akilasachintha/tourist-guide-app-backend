package com.datapirates.touristguideapp.entity.users;

import com.datapirates.touristguideapp.entity.location.Location;
import com.datapirates.touristguideapp.entity.Vehicle;
import com.datapirates.touristguideapp.entity.bookings.DriverBooking;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Driver extends User {
    private String licenceNo;

    private String availability;

    private double rating;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "driver-vehicles")
    @ToString.Exclude
    private Set<Vehicle> vehicles  = new HashSet<>();

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "driver-driverBookings")
    @ToString.Exclude
    private Set<DriverBooking> driverBookings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "driver_fk1"))
    @JsonBackReference(value = "location-drivers")
    @ToString.Exclude
    private Location location;
}
