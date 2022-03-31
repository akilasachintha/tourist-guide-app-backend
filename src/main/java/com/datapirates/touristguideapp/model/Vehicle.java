package com.datapirates.touristguideapp.model;

import com.datapirates.touristguideapp.model.user.Driver;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Vehicle")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @Column(name = "vehicle_no", nullable = false)
    private String vehicleNo;

    @Column(name = "type")
    private String type;

    @Column(name = "seats")
    private int seats;

    @Column(name = "price_for_km")
    private double priceForKm;

    @Column(name = "vehicle_condition")
    private String vehicleCondition;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_id", foreignKey = @ForeignKey(name = "vehicle_id_fk1"), nullable = false)
    @ToString.Exclude
    private Driver driver;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vehicle vehicle = (Vehicle) o;
        return vehicleNo != null && Objects.equals(vehicleNo, vehicle.vehicleNo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
