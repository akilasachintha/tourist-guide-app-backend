package com.datapirates.touristguideapp.entity;

import com.datapirates.touristguideapp.entity.users.Driver;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

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

    private double priceForDay;

    private String vehicleModal;

    private String vehicleCondition;

    private String vehiclePhotoUrl;

    private String vehicleStatus;

    private String adminStatus = "pending";

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "vehicle_fk1"))
    @JsonBackReference(value = "driver-vehicles")
    @ToString.Exclude
    private Driver driver;


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
