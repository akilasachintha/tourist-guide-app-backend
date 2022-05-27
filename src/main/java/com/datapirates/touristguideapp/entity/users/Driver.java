package com.datapirates.touristguideapp.entity.users;

import com.datapirates.touristguideapp.entity.Vehicle;
import com.datapirates.touristguideapp.entity.bookings.DriverBooking;
import com.datapirates.touristguideapp.entity.location.Location;
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
@ToString
@RequiredArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "driver_fk1"))
public class Driver extends AppUser {

    private String userType = "driver";

    private String licenceNo;

    private String availability;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "driver-vehicles")
    @ToString.Exclude
    private Set<Vehicle> vehicles  = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "location", foreignKey = @ForeignKey(name = "driver_fk2"))
    @JsonBackReference(value = "location-drivers")
    @ToString.Exclude
    private Location location;
}
