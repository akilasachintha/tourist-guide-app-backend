package com.datapirates.touristguideapp.model.user;
import com.datapirates.touristguideapp.model.Location;
import com.datapirates.touristguideapp.model.Vehicle;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Driver")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Driver extends User {
    @Column(name = "rating")
    private double rating;

    @Column(name = "licence_no")
    private String licenceNo;

    @Column(name = "availability")
    private String availability;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Vehicle> vehicles;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id",  foreignKey = @ForeignKey(name = "driver_fk1"))
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Location location;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Driver driver = (Driver) o;
        return getUserId() != null && Objects.equals(getUserId(), driver.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}