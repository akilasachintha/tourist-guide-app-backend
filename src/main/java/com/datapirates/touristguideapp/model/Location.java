package com.datapirates.touristguideapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
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
    @JsonManagedReference
    @ToString.Exclude
    private Set<LocationImage> locationImages = new HashSet<>();


    public Long getLocationId() {
        return locationId;
    }

    public void setLocationImages(Set<LocationImage> locationImages) {
        this.locationImages = locationImages;

        for (LocationImage b : locationImages) {
            b.setLocation(this);
        }
    }
}
