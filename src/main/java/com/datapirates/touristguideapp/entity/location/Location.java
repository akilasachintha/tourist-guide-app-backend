package com.datapirates.touristguideapp.entity.location;

import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.users.Driver;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @NotNull
    private String locationName;

    private String district;

    private String town;

    @Lob
    private String description;

    private String category;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference(value = "location-locationImages")
    @ToString.Exclude
    private Set<LocationImage> locationImages = new HashSet<>();

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "location-hotels")
    @ToString.Exclude
    private Set<Hotel> hotels = new HashSet<>();

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "location-drivers")
    @ToString.Exclude
    private Set<Driver> drivers = new HashSet<>();

    public void setLocationImages(Set<LocationImage> locationImages) {
        this.locationImages = locationImages;

        for (LocationImage b : locationImages) {
            b.setLocation(this);
        }
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;

        for (Driver b : drivers) {
            b.setLocation(this);
        }
    }

    public void setHotels(Set<Hotel> hotels) {
        this.hotels = hotels;

        for (Hotel b : hotels) {
            b.setLocation(this);
        }
    }

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
