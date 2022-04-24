package com.datapirates.touristguideapp.model;

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
    @JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "images_fk1"))
    @JsonBackReference
    @ToString.Exclude
    private Location location;
}
