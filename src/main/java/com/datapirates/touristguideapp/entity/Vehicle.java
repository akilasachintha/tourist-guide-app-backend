package com.datapirates.touristguideapp.entity;

import com.datapirates.touristguideapp.entity.users.Driver;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
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

    private double priceForKm;

    private String vehicleCondition;

    private String vehiclePhotoUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "vehicle_fk1"))
    @JsonBackReference(value = "driver-vehicles")
    @ToString.Exclude
    private Driver driver;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        Vehicle vehicle = (Vehicle) o;
//        return vehicleNo != null && Objects.equals(vehicleNo, vehicle.vehicleNo);
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}
