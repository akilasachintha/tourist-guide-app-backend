package com.datapirates.touristguideapp.model;

import com.datapirates.touristguideapp.model.user.Driver;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Location")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "district")
    private String district;

    @Column(name = "town")
    private String town;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Hotel> hotels;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Driver> drivers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Location location = (Location) o;
        return locationId != null && Objects.equals(locationId, location.locationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
