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
@ToString
@RequiredArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    private String vehicleName;

    private String vehicleNo;

    private String vehicleType;

    private int seats;

    private double priceForKm;

    private String vehicleCondition;

    private String vehiclePhotoUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "vehicle_fk1"))
    @JsonBackReference(value = "driver-vehicles")
    @ToString.Exclude
    private Driver driver;

}
