package com.datapirates.touristguideapp.entity.location;

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
@IdClass(LocationImageId.class)
@ToString
public class LocationImage {

    @Id
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "location_image_fk1"))
    @JsonBackReference(value = "location-locationImages")
    @ToString.Exclude
    private Location location;
}
