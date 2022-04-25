package com.datapirates.touristguideapp.entity;

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
public class Vehicle {
    @Id
    private String vehicleNo;

    private String vehicleType;

    private int seats;

    private double priceForKm;

    private String vehicleCondition;

    private String photoUrl;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "driver_id", foreignKey = @ForeignKey(name = "vehicle_fk1"))
    @JsonBackReference(value = "driver-vehicles")
    private Driver driver;
}
